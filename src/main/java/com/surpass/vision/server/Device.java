package com.surpass.vision.server;


import java.util.Hashtable;


public class Device {
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	int id;
	String deviceName;
	Hashtable<String,Point> points;
	public void addPoint(Point point) {
		if(points == null)
			points = new Hashtable<String,Point>();
		points.put(point.tagName, point);
	}

}
