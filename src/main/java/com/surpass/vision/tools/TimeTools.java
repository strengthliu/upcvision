package com.surpass.vision.tools;

import java.util.Date;

public class TimeTools {
	public static long parseSecond(long t) {
		if(t>9999999999.0) {
			return Math.round(t/1000);
		} else
			return Math.round(t);
	}
	
	public static String parseStr(long t) {
		String t1 = IDTools.toString(t);
		Long l = Long.valueOf(t1+"000");
		Date dt = new Date(l);
		System.out.println(dt.toLocaleString());
		return dt.toLocaleString();
	}
	
	public static void main(String[]args) {
		Date dt = new Date(1578275040);
		long l = System.currentTimeMillis();
		System.out.println(l);
		l = parseSecond(l);
		parseStr(l);
//		l = Long.valueOf("1578275040000");
//		dt = new Date(l);
		
		System.out.println(l);
//		parseStr(l);
	}
	
}
