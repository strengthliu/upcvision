package com.surpass.vision.server;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Server {
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
		this.serverName = serverName;
	}
	
	List<Device> devices;
	Hashtable<String,Point> points;
	
	public void addDevice(Device device) {
		if(devices == null)
			devices = new ArrayList<Device>();
		devices.add(device);
		
	}
	public void addPoint(Point p) {
		if(points == null)
			points = new Hashtable<String,Point>();
		points.put(p.tagName, p);
	}
}
