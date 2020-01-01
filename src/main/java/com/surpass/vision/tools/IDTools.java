package com.surpass.vision.tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.jsoup.helper.StringUtil;

import com.surpass.vision.appCfg.GlobalConsts;

public class IDTools {

	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		String [] ss = {"|"};
//		String [] r = IDTools.splitID("!aa,b|b");
//		for(int i=0;i<r.length;i++)
//			System.out.println(r[i]);
//		getUserList
//		Double d = 15660299628049.0;
//		System.out.println("默认输出:" + d);
//		System.out.println("格式输出:" + toString(d));
//		
//		System.out.println("格式输出:" + toString(Double.valueOf(2)));
		
//		long t = System.currentTimeMillis();
//		System.out.println(new Date(t)+"  "+ t);
//		int ti = (int)t;
//		System.out.println(new Date(ti)+"  "+ ti);
		
		ArrayList t1 = new ArrayList();
		ArrayList t2 = new ArrayList();
		t1.add("a");
		t1.add("b");
		t1.add("c");
		t2.add("a");
		t2.add("c");
		t2.add("d");
		t2.add("e");
		List td = IDTools.getAggrandizement(t1, t2);
		
		for(int i=0;i<td.size();i++) {
			System.out.println(td.get(i)+" | ");
		}
		System.out.println("--------");
		td = IDTools.getDecreament(t1, t2);
		for(int i=0;i<td.size();i++) {
			System.out.println(td.get(i)+" | ");
		}
		

	}

	public static int SERVER_ID  = 1; // 默认为1
	public static long ID_BEGIN_TIME = 1309449600000L;
	public static int DB_COUNT = 1;
	/**
	 * 获取主键ID
	 * 
	 * @return
	 * @throws Exception
	 */
	public static synchronized long getUniqueID() throws Exception {
		if (SERVER_ID <= 0) {
			throw new Exception("server id error, please check config file!");
		}

		long destID = System.currentTimeMillis() - ID_BEGIN_TIME;
		destID = (destID << 8) | SERVER_ID;
		Thread.sleep(1);
		return destID;
	}

	/**
	 * 生成唯一ID,该ID的dbIndex与sourceID一至 注：最大支持库 ：512个 最大支持时间：4240-01-01
	 * 
	 * @param sourceID
	 * @return
	 * @throws Exception
	 */
	public static synchronized long getUniqueID(long sourceID) throws Exception {
		if (SERVER_ID <= 0) {
			throw new Exception("server id error, please check config file!");
		}

		int sourceIndex = getDBIndex(sourceID);
		long destID = System.currentTimeMillis() - ID_BEGIN_TIME;
		destID = (destID << 9) | sourceIndex;
		destID = (destID << 8) | SERVER_ID;
		Thread.sleep(1);
		return destID;
	}

	/**
	 * 获取ID所对应该的数据库编号
	 * 
	 * @param ID
	 * @return 数据库
	 */
	public static int getDBIndex(long id) {
		return (int) ((id >> 8) & (DB_COUNT - 1));
	}
	
	public static synchronized Double newID() {
		Integer i = new Random().nextInt(10);
		Long l = System.currentTimeMillis();
		String r = l.toString()+i.toString();
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Double.parseDouble(r);
	}
	
	public static String [] splitID(String str,String... splitChar) {
		if(StringUtil.isBlank(str)) return new String[0];
		String sc = GlobalConsts.Key_splitChar;
		if(splitChar.length>1) {
			sc = "";
			for(int i =0;i<splitChar.length;i++){
				if(splitChar[i]!=null && splitChar[i]!="" && splitChar[i]!="|" && splitChar[i]!=".")
					sc+= splitChar[i]+"|";
				else if(splitChar[i]=="|" || splitChar[i]==".") {
					String sct = "\\"+splitChar[i];
					sc+= sct+"|";
				
				}
			}
		}else if(splitChar.length==1) {
			String sct = splitChar[0];
			if(splitChar[0]=="|" || splitChar[0]==".") {
				sct = "\\"+sct;
			}
			sc = sct+"|";
		}else if(splitChar.length==0)
			sc += "|";
		
		sc = sc.substring(0, sc.length()-1);
//		System.out.println("sc = "+sc);
		
		return str.split(sc);
	}

	public static String merge(Object[] aggrandizement) {
		if(aggrandizement.length<=0) return "";
		
		String ret = "";
		Class<?> clazz = aggrandizement.getClass();
		Method method = null;
		try {
			method = clazz.getMethod("getId");
		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
		} catch (SecurityException e) {
//			e.printStackTrace();
		}

		for(int i=0;i<aggrandizement.length;i++) {
			Object ot = null;
			if(method!=null) {
				try {
					ot = method.invoke(clazz.newInstance());
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
						| InstantiationException e) {
					e.printStackTrace();
					ot = aggrandizement[i];
				}
			}else {
				ot = aggrandizement[i];
			}
			
			if(ot instanceof java.lang.Double) {
				ret += IDTools.toString((Double)ot);
			} else if(ot instanceof java.lang.Long) {
				ret += IDTools.toString((Long)ot);
			} else if(ot instanceof java.lang.Float) {
				ret += IDTools.toString((Float)ot);
			} else if(ot instanceof java.lang.Integer) {
				ret += IDTools.toString((Integer)ot);
			} else if(ot instanceof java.math.BigDecimal) {
				ret += IDTools.toString((BigDecimal)ot);
			} else if(ot instanceof java.math.BigInteger) {
				ret += IDTools.toString((BigInteger)ot);
			} else {
				ret += ot.toString();
			}
			ret += GlobalConsts.Key_splitChar;
		}
		if(ret.endsWith(GlobalConsts.Key_splitChar))
			ret = ret.substring(0,ret.length() - GlobalConsts.Key_splitChar.length());
		return ret;
	}

	public static String toString(Double doubleId) {
		DecimalFormat df1 = new DecimalFormat("0");
		String ret = df1.format(doubleId);
		return ret;
	}
	public static String toString(BigInteger doubleId) {
		DecimalFormat df1 = new DecimalFormat("0");
		String ret = df1.format(doubleId);
		return ret;
	}
	public static String toString(BigDecimal doubleId) {
		DecimalFormat df1 = new DecimalFormat("0");
		String ret = df1.format(doubleId);
		return ret;
	}
	
	public static String toString(Float doubleId) {
		DecimalFormat df1 = new DecimalFormat("0");
		String ret = df1.format(doubleId);
		return ret;
	}

	public static String toString(Long id) {
		DecimalFormat df1 = new DecimalFormat("0");
		String ret = df1.format(id);
		return ret;
	}

	public static String toString(Integer uid) {
		DecimalFormat df1 = new DecimalFormat("0");
		String ret = df1.format(uid);
		return ret;
	}

	public static String toString(String userId) {
		
		return userId;
	}
	
	public static <T> List<T> getAggrandizement(List<T> s,List<T> t) {
		ArrayList ret = new ArrayList();
		if(s==null)return ret;
		for(int i=0;i<s.size();i++) {
			T _s = s.get(i);
			if(t==null||!t.contains(_s)) {
				ret.add(_s);
			}
		}
		return ret;
	}
	public static <T> List<T> getDecreament(List<T> s,List<T> t) {
		ArrayList ret = new ArrayList();
		if(t==null) return ret;
		for(int i=0;i<t.size();i++) {
			T _s = t.get(i);
			if(s==null||!s.contains(_s)) {
				ret.add(_s);
			}
		}
		return ret;
	}
	
//	public static List<String> getAggrandizement(List<String> s,List<String> t) {
//		
//		return null;
//	}
//	
//	decreament
}
