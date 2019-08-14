package com.surpass.vision.server;


import java.io.Serializable;
import java.util.Hashtable;


public class Device implements Serializable {
	String deviceNote;
	public String getDeviceNote() {
		return deviceNote;
	}
	public void setDeviceNote(String deviceNote) {
		this.deviceNote = deviceNote;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Hashtable<String, Point> getPoints() {
		return points;
	}
	public void setPoints(Hashtable<String, Point> points) {
		this.points = points;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	Long id;
	String deviceName;
	Hashtable<String,Point> points;
	public void addPoint(Point point) {
		if(points == null)
			points = new Hashtable<String,Point>();
		points.put(point.tagName, point);
	}

}
