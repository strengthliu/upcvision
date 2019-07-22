package com.surpass.vision.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class History {
	private String id ;
	private List<Double> val ;
	private List<Long> arr;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Double> getVal() {
		return val;
	}
	public void setVal(List<Double> val) {
		List<Double> val1 = new ArrayList<Double>();
		DecimalFormat df = new DecimalFormat("#.00");
		for (int i = 0; i < val.size(); i++) {
			val1.add(Double.parseDouble(df.format(val.get(i))));
		}
		this.val = val1;
	}
	public List<Long> getArr() {
		return arr;
	}
	public void setArr(List<Long> arr) {
		this.arr = arr;
	}
	
	
	
}
