package com.surpass.vision.server;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.objenesis.instantiator.util.UnsafeUtils;

import sun.misc.Unsafe;

public class PointList {
	
	class Ind { //extends Semaphore{
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
		public Long getValue() {
			return d.values[ind];
		}
	}

	class _Data {

		public static final int maxCapacity = 5000;
		public static final int rebuildThrethold = 1000;

		public AtomicInteger _0relation = new AtomicInteger(0); 
		// 获取Unsafe对象，Unsafe的作用是提供CAS操作
//		private static final Unsafe unsafe = Unsafe.getUnsafe();
//		private static long valueOffset;
//		static {
//			try {
//				valueOffset = unsafe.objectFieldOffset(AtomicReference.class.getDeclaredField("value"));
//			} catch (Exception ex) {
//				throw new Error(ex);
//			}
//		}

		/**
		 * 点位名 ---------------------------------------
		 * 
		 * @author 刘强 2019年8月26日 下午4:29:08
		 */
		volatile CopyOnWriteArrayList<String> tagNames = new CopyOnWriteArrayList<String>();

		/**
		 * 点位值。这是更新最快的地方。 ---------------------------------------
		 * 
		 * @author 刘强 2019年8月26日 下午4:29:26
		 */
		volatile Long[] values = new Long[maxCapacity];

		/**
		 * 当前各点位被使用的数字，如果为0，则可以删除。 删除时执行lazy模式。 ---------------------------------------
		 * 
		 * @author 刘强 2019年8月26日 下午4:28:11
		 */
		volatile CopyOnWriteArrayList<AtomicInteger> useCount2 = new CopyOnWriteArrayList<AtomicInteger>();
		
		volatile CopyOnWriteArrayList<Ind> useCount1 = new CopyOnWriteArrayList<Ind>();
//		final Semaphore sp = new Semaphore(3);// 创建一个Semaphore信号量，并设置最大并发数为3

		/**
		 * 如果pointIds不变，就是没有修改。 ---------------------------------------
		 * 
		 * @author 刘强 2019年8月26日 下午3:41:56
		 */
		volatile CopyOnWriteArrayList<Long> pointIds = new CopyOnWriteArrayList<Long>();
//		private volatile AtomicLongArray pointIds;

		/**
		 * TODO: 自己实现相应更新方法，以确保并发安全。
		 * 
		 * @param expect
		 * @param update
		 * @return
		 */
//		public final boolean compareAndSet(int expect, int update) {
////			new ArrayList().toArray()
////			Arrays.asList(a)
//			return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
//		}

		/**
		 * 数据更新标识量 ---------------------------------------
		 * 
		 * @author 刘强 2019年8月26日 下午7:07:45
		 */
		public volatile AtomicBoolean updating = new AtomicBoolean(false);

		/**
		 * 重建维护操作标识量 ---------------------------------------
		 * 
		 * @author 刘强 2019年8月27日 上午10:44:29
		 */
		public volatile AtomicBoolean rebuilding = new AtomicBoolean(false);

	}

	/**
	 * 单例模式
	 */
	private PointList() {
		// applicationContext =
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
	private static _Data[] datas = new _Data[10] ;

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
	public void addPoints(DataViewer dv) {
		Point[] p = dv.getPoints();
		int[] ind = new int[p.length];
		Ind[] iind = new Ind[p.length];
		for (int indp = 0; indp < p.length; indp++) {
			// 判断是在哪个数据区里。
			int dataIndex = -1;
			for (int t_dataIndex = 0; t_dataIndex < datas.length; t_dataIndex++) {
				if (datas[t_dataIndex].pointIds.contains(p[indp])) {
					dataIndex = t_dataIndex;
					break;
				}
			}
			if (dataIndex == -1) {
				//TODO:  选择一个数据区
				dataIndex = 0;
				for(int i_dataIndex=0;i_dataIndex<datas.length;i_dataIndex++) {
					int freePc1 = datas[i_dataIndex].pointIds.size();
					if(freePc1>p.length) {
						dataIndex = i_dataIndex;
						break;
					}
					int _0 = datas[i_dataIndex]._0relation.get();
					if(_0>p.length) {
						dataIndex = i_dataIndex;						
						break;
					}
				}
				
				// 如果当前正在更新数据，可以填加点在数组的尾部
				// 如果当前在重构，则需要一直等待。
				while (datas[dataIndex].rebuilding.get()) {
					Thread.yield();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				   
				// 加入这个点
				datas[dataIndex].pointIds.add(p[indp].getId());
				ind[indp] = datas[dataIndex].pointIds.indexOf(p[indp]);
//				ind[indp][1] = dataIndex;
				
				datas[dataIndex].tagNames.set(indp, p[indp].tagName); // 设置名字
				datas[dataIndex].useCount2.get(indp).getAndIncrement(); // 使用加1
				Ind _ind = new Ind();
				_ind.setD(datas[dataIndex]);
				_ind.setInd(indp);
				datas[dataIndex].useCount1.set(indp, _ind);
				// 返回引用Ind
				iind[indp] = _ind;

			} else {
				// 如果当前正在更新数据，可以填加点在数组的尾部
				// 如果当前在重构，则需要一直等待。
				while (datas[dataIndex].rebuilding.get()) {
					Thread.yield();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// 如果已经有这个点了
				if (datas[dataIndex].pointIds.contains(p[indp])) {
					int indContain = datas[0].pointIds.indexOf(p[indp]);
					// 使用数加1，返回以前已经使用的数。
					int oldUser = datas[dataIndex].useCount2.get(indContain).getAndIncrement();
					iind[indp] = datas[dataIndex].useCount1.get(indContain);

					switch (oldUser) {
					case 0: // 第一次使用
						// TODO
						break;
					case _Data.maxCapacity - 1: // 已经最大了
						// TODO
						break;
					}
				}
			}
		}
		// 构建
		dv.ind = iind;
		this.viewers.put(dv.pointGroupID, dv);
		
//		boolean ret;
//		AtomicReference<_Data> stacks = new AtomicReference<_Data>(datas[0]);
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
			for (int t_dataIndex = 0; t_dataIndex < datas.length; t_dataIndex++) {
				if (datas[t_dataIndex].pointIds.contains(p[indp])) {
					dataIndex = t_dataIndex;
					break;
				}
			}
			if (dataIndex == -1) {
				// 不存在这个点，应该是已经被删除了。

			} else {
				// 如果重构中，则待。
				while (datas[dataIndex].rebuilding.get()) {
					Thread.yield();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				// 如果还有这个点（没有被清理掉）
				if (datas[dataIndex].pointIds.contains(p[indp])) {
					int indContain = datas[0].pointIds.indexOf(p[indp]);
					// 使用数加1，返回以前已经使用的数。
					int oldUser = datas[dataIndex].useCount2.get(indContain).getAndIncrement();
					
					switch (oldUser) {
					case 1: // 最后一次使用，当前已经为0了。
						// TODO: 判断是否需要维护。
						int v = datas[dataIndex]._0relation.incrementAndGet();
						if(v>_Data.rebuildThrethold) rebuild(datas[dataIndex]);
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
		while(_data.rebuilding.compareAndSet(false, true));

		int i = 0;
		int newInd = 0;
		for(AtomicInteger a : _data.useCount2) {
			int ind = a.get();
			if(ind<=0) {
				_data.useCount2.remove(i);
				_data.pointIds.remove(i);
				_data.tagNames.remove(i);
				_data.useCount1.remove(i);
			}else {
				_data.useCount1.get(i).setInd(newInd);
				newInd ++;
			}
			i ++;
		}
		
		// 恢复标识
		if(!_data.rebuilding.compareAndSet(true, false)) {
			throw new IllegalStateException("数据_Data被修改了更新标识");
		}
		
	}

	/**
	 * 更新所有点的数据
	 * 
	 * @param tagName
	 */
	public void updateValue(int indData) {
		boolean ret;
		do {
			AtomicReference<_Data> stacks = new AtomicReference<_Data>(datas[indData]);
			_Data oldData = stacks.get();
			_Data newData = new _Data();
			newData.pointIds = oldData.pointIds;

			ret = stacks.compareAndSet(oldData, newData);
		} while (!ret);

		boolean canUpdate = false;
		// 如果有线程在更新，等待直到可以更新或等了一秒
		int waitTimes = 0;
		while (datas[indData].rebuilding.get() || datas[indData].updating.get())
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
			AtomicBoolean ab = datas[indData].updating;
			boolean oldT = ab.get();
			canUpdate = ab.compareAndSet(oldT, true);
		} while (canUpdate);

		// 开始更新
		List<Long> _values = ServerManager.getInstance().getPointValue(datas[indData].pointIds);
		datas[indData].values = (Long[]) _values.toArray();
		// 释放更新标识
		datas[indData].updating.compareAndSet(true, false);

		// 更新完发通知
		publish(Long.valueOf(10), "开始更新");
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
