package com.surpass.vision.server;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.surpass.vision.server.ServerPoint;

import sun.misc.Unsafe;

@Component
@Configuration
@EnableScheduling // 此注解必加
public class PointList {

	class Ind { // extends Semaphore{
//		public Ind(int permits) {
//			super(permits);
//			// TODO Auto-generated constructor stub
//		}
		public int getInd() {
			return ind;
		}

		public void setInd(int ind) {
			this.ind = ind;
		}

		public _Data getD() {
			return d;
		}

		public void setD(_Data d) {
			this.d = d;
		}

		int ind;
		_Data d;

		public Double getValue() {
			return d.values[ind];
		}
	}


	/**
	 * 单例模式
	 */
	private PointList() {
//		// applicationContext =
//		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
//		//  第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
//		service.scheduleAtFixedRate(doUpdateValue, 1, 1, TimeUnit.SECONDS);
	}

	public static PointList instance;

	public static PointList getInstance() {
		// 先判断实例是否存在，若不存在再对类对象进行加锁处理
		if (instance == null) {
			synchronized (PointList.class) {
				if (instance == null) {
					instance = new PointList();
				}
			}
		}
		return instance;
	}

	/**
	 * 数据区,每一个_Data，5000个点位； 每个数据块一个线程负责更新。 ---------------------------------------
	 * 
	 * @author 刘强 2019年8月26日 下午2:00:24
	 */
	private static List<_Data> datas = new ArrayList<_Data>();

	/**
	 * 
	 * ---------------------------------------
	 * 
	 * @author 刘强 2019年8月26日 下午4:04:24
	 */
	private static Hashtable<Double, DataViewer> viewers = new Hashtable<Double, DataViewer>();

	/**
	 * 添加一个点
	 * 
	 * @param p
	 */
	public DataViewer addPoints(DataViewer dvs) {
		if (dvs.pointGroupID == null || dvs.pointGroupID == 0)
			throw new IllegalStateException("addPoints参数不正确PointID不能为空");
		if (dvs.getSps() == null || dvs.getSps().length <= 0)
			throw new IllegalStateException("addPoints参数不正确,IDs不能为空");
//		if(dv.getIds()==null || dv.getIds().length<=0)
//			throw new IllegalStateException("addPoints参数不正确,IDs不能为空");
		int _ind_c = 0; // 总共有多少个点。
//		int _iind_c = 0;
		for (int inddv = 0; inddv < dvs.sps.length; inddv++) {
			_ind_c += dvs.sps[inddv].ids.length;
		}
		// int[] ind = new int[_ind_c];
		Ind[] iind = new Ind[_ind_c];// 数据点引用
		Long[] idind = new Long[_ind_c];// 数据点引用
		String[] tagnameind = new String[_ind_c];
		int a_index = -1;
		// 一个个ServerPoint遍历
		for (int inddv = 0; inddv < dvs.sps.length; inddv++) {
			ServerPoint dv = dvs.sps[inddv];
			Point[] p = dv.getPoints(); // 取出点
			// 一个个点处理
			for (int indp = 0; indp < p.length; indp++) {
				a_index++;
//				Ind _ind = addPoint(p[indp],iind);

				// 判断选择这个点应该放在哪个数据区里。
				int dataIndex = -1;
				boolean userDirtayData = false;
				for (int t_dataIndex = 0; t_dataIndex < datas.size(); t_dataIndex++) {
					// 如果这个点的服务器跟数据缓冲区里的服务器是同一个
					if (datas.get(t_dataIndex).serverName.contentEquals(p[indp].getServerName())) {
						// 如果这个数据区里存在这个点
						if (datas.get(t_dataIndex).pointIds.contains(p[indp])) {
							dataIndex = t_dataIndex;
							break;
						} else { // 如果不存在这个点
							int freePc1 = datas.get(t_dataIndex).pointIds.size();
							// 这个区里的空间还有没有
							if ((_Data.maxCapacity - freePc1) > p.length) {
								// 如果有，就用这个区
								dataIndex = t_dataIndex;
								break;
							}
							// 如果没有，再看看脏点有多少
							int _0 = datas.get(t_dataIndex)._0relation.get();
							// 如果脏点够用，就用脏点
							if (_0 > p.length) {
								dataIndex = t_dataIndex;
								userDirtayData = true;
								break;
							}
						}
					}
				}
				if (dataIndex == -1) { // 没有这个服务器的数据
					// 新建一个缓冲区
					_Data d = new_Data(p[indp].getServerName());
					dataIndex = datas.size() - 1;

					// 如果当前正在更新数据，可以填加点在数组的尾部
					// 如果当前在重构，则需要一直等待。
					while (datas.get(dataIndex).rebuilding.get()) {
						Thread.yield();
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					// 加入这个点
					Ind _ind = addNewPoint(datas.get(dataIndex), p[indp]);
					// 把引用Ind加入到返回数组中
					// 这里错了，如果有两个服务器，indp索引就不对了。
//					iind[indp] = _ind;
//					idind[indp] = p[indp].id;
//					tagnameind[indp] = p[indp].tagName;
					iind[a_index] = _ind;
					idind[a_index] = p[indp].id;
					tagnameind[a_index] = p[indp].tagName;

				} else {
					// 如果当前正在更新数据，可以填加点在数组的尾部
					// 如果当前在重构，则需要一直等待。
					while (datas.get(dataIndex).rebuilding.get()) {
						Thread.yield();
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					// 如果已经有这个点了
					if (datas.get(dataIndex).pointIds.contains(p[indp])) {
						int indContain = datas.get(dataIndex).pointIds.indexOf(p[indp]);
						// 使用数加1，返回以前已经使用的数。
						int oldUser = datas.get(dataIndex).useCount2.get(indContain).getAndIncrement();
						iind[a_index] = datas.get(dataIndex).useCount1.get(indContain);

						switch (oldUser) {
						case 0: // 第一次使用
							// TODO
							break;
						case _Data.maxCapacity - 1: // 已经最大了

							break;
						}
					} else { // TODO: 如果没有这个点
						if (userDirtayData) { // TODO: 如果使用脏数据
							CopyOnWriteArrayList<AtomicInteger> useCount2 = datas.get(dataIndex).useCount2;
							int induseCount2 = 0;
							for (; induseCount2 < useCount2.size(); induseCount2++) {
								// 找到第一个脏数据
								AtomicInteger ailock = useCount2.get(induseCount2);
								// 锁定
								boolean lock = ailock.compareAndSet(0, 1);
								if (lock) {
									// TODO: 更新脏数据
									// induseCount2
									_Data _data = datas.get(dataIndex);
									_data.pointIds.set(induseCount2, p[indp].getId());
									_data.tagNames.set(induseCount2, p[indp].tagName); // 设置名字
									AtomicInteger ait_ = new AtomicInteger(0);
									int _att_ = ait_.getAndIncrement();
									Ind _ind = new Ind();
									_ind.setD(_data);
									int serind = _data.tagNames.indexOf(p[indp].tagName);
									_ind.setInd(serind); // 设置序号
									_data.useCount1.add(_ind);

									break; // 更新完成退出
								}
							}
							// 如果这时候缓冲区中脏数据都被使用了，就新建一个区
							if (induseCount2 == useCount2.size()) {
								// 新建一个缓冲区
								_Data d = new_Data(p[indp].getServerName());
								dataIndex = datas.size() - 1;

								// 如果当前正在更新数据，可以填加点在数组的尾部
								// 如果当前在重构，则需要一直等待。
								while (datas.get(dataIndex).rebuilding.get()) {
									Thread.yield();
									try {
										Thread.sleep(100);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}

								// 加入这个点
								Ind _ind = addNewPoint(datas.get(dataIndex), p[indp]);
								// 把引用Ind加入到返回数组中
//								iind[indp] = _ind;
//								idind[indp] = p[indp].id;
//								tagnameind[indp] = p[indp].tagName;
								iind[a_index] = _ind;
								idind[a_index] = p[indp].id;
								tagnameind[a_index] = p[indp].tagName;

							}
						} else { // 否则就追加一个点进去
							// 选择缓冲区
							_Data d = datas.get(dataIndex);

							// 如果当前正在更新数据，可以填加点在数组的尾部
							// 如果当前在重构，则需要一直等待。
							while (datas.get(dataIndex).rebuilding.get()) {
								Thread.yield();
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

							// 加入这个点
							Ind _ind = addNewPoint(datas.get(dataIndex), p[indp]);
							// 把引用Ind加入到返回数组中
//							iind[indp] = _ind;
//							idind[indp] = p[indp].id;
//							tagnameind[indp] = p[indp].tagName;
							iind[a_index] = _ind;
							idind[a_index] = p[indp].id;
							tagnameind[a_index] = p[indp].tagName;
						}
					}
				}
			}
		}
		// 构建
		dvs.ind = iind;
		dvs.ids = idind;
		dvs.tagNames = tagnameind;
		dvs.rebuild();

		viewers.put(dvs.pointGroupID, dvs);
		return dvs;
//		boolean ret;
//		AtomicReference<_Data> stacks = new AtomicReference<_Data>(datas.get(dataIndex));
//		do {
//			// AtomicLongArray
//			_Data oldData = stacks.get();
//			_Data newData = new _Data();
//			newData.pointIds = oldData.pointIds;
//			// 可能有问题，不确定比较器的机制。因为如果按==比较，那就有两个问题，一是内存交换太大，二是因为比较的是地址，所以总是相等。
//			ret = stacks.compareAndSet(oldData, newData);
//		} while (!ret);
//		ExecutorService service = Executors.newCachedThreadPool();// 使用并发库，创建缓存的线程池
//		final Semaphore sp = new Semaphore(3);// 创建一个Semaphore信号量，并设置最大并发数为3
	}

	private Ind addNewPoint(_Data _data, Point point) {
		_data.pointIds.add(point.getId());
		_data.tagNames.add(point.tagName); // 设置名字
		AtomicInteger ait_ = new AtomicInteger(0);
		int _att_ = ait_.getAndIncrement();
		_data.useCount2.add(ait_); // 使用加1
		Ind _ind = new Ind();
		_ind.setD(_data);
		int serind = _data.tagNames.indexOf(point.tagName);
		_ind.setInd(serind); // 设置序号
		_data.useCount1.add(_ind);
		return _ind;
	}

	private _Data new_Data(String serverName) {
		// TODO Auto-generated method stub
		_Data d = new _Data();
		d.serverName = serverName;
		// 启动一个线程池任务
		datas.add(d);
		// dataIndex = datas.size() - 1;
		return d;
	}

	/**
	 * 删除一个点
	 * 
	 * @param p
	 */

	final Semaphore sp = new Semaphore(_Data.rebuildThrethold);

	public void delPoints(Point[] p) {
		// 如果当前正在更新数据，不可以立刻删除点
		// 如果当前在重构，则需要一直等待。
		// 不立刻删除，只是把引用标记减1
		// 当引用为0时，也不立刻删除，而是等所有0点达到20%时，再维护换一次
		int[] ind = new int[p.length];
		for (int indp = 0; indp < p.length; indp++) {
			// 判断是在哪个数据区里。
			int dataIndex = -1;
			for (int t_dataIndex = 0; t_dataIndex < datas.size(); t_dataIndex++) {
				if (datas.get(t_dataIndex).pointIds.contains(p[indp])) {
					dataIndex = t_dataIndex;
					break;
				}
			}
			if (dataIndex == -1) {
				// 不存在这个点，应该是已经被删除了。

			} else {
				// 如果重构中，则待。
				while (datas.get(dataIndex).rebuilding.get()) {
					Thread.yield();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				// 如果还有这个点（没有被清理掉）
				if (datas.get(dataIndex).pointIds.contains(p[indp])) {
					int indContain = datas.get(dataIndex).pointIds.indexOf(p[indp]);
					// 使用数加1，返回以前已经使用的数。
					int oldUser = datas.get(dataIndex).useCount2.get(indContain).getAndIncrement();

					switch (oldUser) {
					case 1: // 最后一次使用，当前已经为0了。
						// TODO: 判断是否需要维护。
						int v = datas.get(dataIndex)._0relation.incrementAndGet();
						if (v > _Data.rebuildThrethold)
							rebuild(datas.get(dataIndex));
						break;
					}
				}
				// 否则是出错了
				else {
				}
			}
		}
	}

	private void rebuild(_Data _data) {
		// TODO
		// 设置rebuild标识，阻拦其他操作
		while (_data.rebuilding.compareAndSet(false, true))
			;

		int i = 0;
		int newInd = 0;
		for (AtomicInteger a : _data.useCount2) {
			int ind = a.get();
			if (ind <= 0) {
				_data.useCount2.remove(i);
				_data.pointIds.remove(i);
				_data.tagNames.remove(i);
				_data.useCount1.remove(i);
			} else {
				_data.useCount1.get(i).setInd(newInd);
				newInd++;
			}
			i++;
		}

		// 恢复标识
		if (!_data.rebuilding.compareAndSet(true, false)) {
			throw new IllegalStateException("数据_Data被修改了更新标识");
		}

	}

	@Scheduled(cron = "*/1 * * * * *")
	public void doUpdateValue() {
		for (int i = 0; i < datas.size(); i++) {
//			System.out.println("更新数据缓存区" + i);
			updateValue(i);
		}
	}

	
	/**
	 * 更新所有点的数据
	 * 
	 * @param tagName
	 */
	@Async("pointListUpdateExecutor")
	public void updateValue(int indData) {
//		System.out.println("updateValue in ");
		boolean ret;
		do {
			AtomicReference<_Data> stacks = new AtomicReference<_Data>(datas.get(indData));
			_Data oldData = stacks.get();
			_Data newData = new _Data();
			newData.pointIds = oldData.pointIds;

			ret = stacks.compareAndSet(oldData, newData);
		} while (!ret);

		boolean canUpdate = false;
		// 如果有线程在更新，等待直到可以更新或等了一秒
		int waitTimes = 0;
		while (datas.get(indData).rebuilding.get() || datas.get(indData).updating.get())
			try {
				waitTimes++;
				Thread.sleep(100);
				if (waitTimes > 10)
					return;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		// 可以更新了，就先锁住标识。如果这时有线程进来了，就一直等到可以
		do {
			AtomicBoolean ab = datas.get(indData).updating;
			boolean oldT = ab.get();
			canUpdate = ab.compareAndSet(oldT, true);
		} while (!canUpdate);

		// 开始更新
		List<Double> _values = ServerManager.getInstance().getPointValue(datas.get(indData).serverName,
				datas.get(indData).pointIds, "FN_RTVALUE");
		int count = _values.size();
		for(int i=0;i<count;i++) {
			datas.get(indData).values[i] = _values.get(i);
//			datas.get(indData).updateValue(i,_values.get(i));
//			System.out.println("data_"+i+" = "+_values.get(i));
		}

		
		// 释放更新标识
		datas.get(indData).updating.compareAndSet(true, false);

		// 更新完发通知
//		publish(Long.valueOf(10), "开始更新");
//		return null;

	}

	/**
	 * 取一个点组的值
	 * 
	 * @param pointGroupID
	 * @return
	 */
	public Double[] getDataView(DataViewer viewer) {
		return null;
	}

	/**
	 * 发送消息
	 */
	private ApplicationContext applicationContext;

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Autowired
	public PointList(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void publish(long id, String message) {
		applicationContext.publishEvent(new DataUpdateEvent(this, id, message));
	}

//	AtomicBoolean，AtomicInteger，AtomicLong，AtomicReference
//	AtomicIntegerArray，AtomicLongArray
//	AtomicLongFieldUpdater，AtomicIntegerFieldUpdater，AtomicReferenceFieldUpdater
//	AtomicMarkableReference，AtomicStampedReference，AtomicReferenceArray

	// private
	public static void main(String[] args) {

		// TODO Auto-generated method stub
		Double a = Double.valueOf(100);
		ArrayList al = new ArrayList();
		al.add(a);
		System.out.println(al.get(0));
		a--;
		System.out.println(al.get(0));
		BigDecimal b = new BigDecimal(100);
		al.add(b);
		System.out.println(al.get(1));
		b.add(new BigDecimal(100));
		System.out.println(al.get(1));

	}

}
