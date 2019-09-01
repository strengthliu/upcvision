package com.surpass.vision.server;

import java.io.Serializable;
import java.util.Hashtable;

public class Point implements Serializable {
	Long id;
	String tagName;
	String desc; // FN_TAGNOTE
	String deviceName;
	String serverName;
	String enunit; // 数据单位 ，FN_ENUNITS
	String tagType; // 位号类型，FN_TAGTYPE
	
	public Long getId() {
		return id;
	}
	public String getEnunit() {
		return enunit;
	}
	public void setEnunit(String enunit) {
		this.enunit = enunit;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getTagType() {
		return tagType;
	}
	public void setTagType(String tagType) {
		this.tagType = tagType;
	}
	public Hashtable<String, Float> getValues() {
		return values;
	}
	public void setValues(Hashtable<String, Float> values) {
		this.values = values;
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

	Hashtable<String,Float> values;
	
}
