package com.surpass.vision.server;

import java.util.Hashtable;

public class Point {
	Long id;
	String tagName;
	String desc;
	String deviceName;
	long tagType;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	String serverName;
	Hashtable<String,Float> values;
	
}
