package com.surpass.vision.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Server implements Serializable {
	int id;
	String serverName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName.toUpperCase();
	}
	
	List<Device> devices;
	public List<Device> getDevices() {
		return devices;
	}
	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}
	public Hashtable<String, Point> getPoints() {
		return points;
	}
	public void setPoints(Hashtable<String, Point> points) {
		this.points = points;
	}

	Hashtable<String,Point> points;
	
	public void addDevice(Device device) {
		if(devices == null)
			devices = new ArrayList<Device>();
		devices.add(device);
		
	}
	public void addPoint(Point p) {
		if(points == null)
			points = new Hashtable<String,Point>();
		points.put(p.tagName.toUpperCase(), p);
	}
}
