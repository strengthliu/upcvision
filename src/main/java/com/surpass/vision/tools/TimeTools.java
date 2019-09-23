package com.surpass.vision.tools;

public class TimeTools {
	public static long parseSecond(long t) {
		if(t>9999999999.0) {
			return Math.round(t/1000);
		} else
			return Math.round(t);
	}
}
