package com.surpass.vision.server;

import java.lang.reflect.Field;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.surpass.vision.server.PointList.Ind;


import sun.misc.Unsafe;

class _Data {
	String serverName;

	public _Data() {

	}

	public static final int maxCapacity = 5000;
	public static final int rebuildThrethold = 1000;

	public AtomicInteger _0relation = new AtomicInteger(0);

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
	volatile Double[] values = new Double[maxCapacity];

	/**
	 * 当前各点位被使用的数字，如果为0，则可以删除。 删除时执行lazy模式。 ---------------------------------------
	 * 
	 * @author 刘强 2019年8月26日 下午4:28:11
	 */
	volatile CopyOnWriteArrayList<AtomicInteger> useCount2 = new CopyOnWriteArrayList<AtomicInteger>();

	volatile CopyOnWriteArrayList<Ind> useCount1 = new CopyOnWriteArrayList<Ind>();
//	final Semaphore sp = new Semaphore(3);// 创建一个Semaphore信号量，并设置最大并发数为3

	/**
	 * 如果pointIds不变，就是没有修改。 ---------------------------------------
	 * 
	 * @author 刘强 2019年8月26日 下午3:41:56
	 */
	volatile CopyOnWriteArrayList<Long> pointIds = new CopyOnWriteArrayList<Long>();
//	private volatile AtomicLongArray pointIds;

	/**
	 * TODO: 自己实现相应更新方法，以确保并发安全。
	 * 
	 * @param expect
	 * @param update
	 * @return
	 */
//	public final boolean compareAndSet(int expect, int update) {
////		new ArrayList().toArray()
////		Arrays.asList(a)
//		return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
//	}

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

	
	// 获取Unsafe对象，Unsafe的作用是提供CAS操作
//	private static final Unsafe unsafe = Unsafe.getUnsafe();
//	private static long valueOffset;
//	static {
//		try {
//			valueOffset = unsafe.objectFieldOffset(_Data.class.getDeclaredField("values"));
//		} catch (Exception ex) {
//			throw new Error(ex);
//		}
//	}

	@SuppressWarnings({ "restriction" })
	public void updateValue(int i, Double value) {
		// TODO: 用UnSafe拷贝内存，替换赋值方法。需要测试一下，性能上是不是有较大提升。
		this.values[i] = value;

//		Field f = null;
//		try {
//			f = _Data.class.getDeclaredField("values");
//		} catch (NoSuchFieldException | SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        unsafe.putInt(value, unsafe.objectFieldOffset(f), i*64);
        
//    	long addressOfObject=getAddressOfObject(unsafe, b);
//    	unsafe.copyMemory(b, 16, null, addressOfObject, N); 
	}
	
	public long getAddressOfObject(sun.misc.Unsafe unsafe, Object obj) {
	    Object helperArray[]    = new Object[1];
	    helperArray[0]          = obj;
	    long baseOffset         = unsafe.arrayBaseOffset(Object[].class);
	    long addressOfObject    = unsafe.getLong(helperArray, baseOffset);      
	    return addressOfObject;
	}
}
