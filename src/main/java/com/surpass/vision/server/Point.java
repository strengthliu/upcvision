package com.surpass.vision.server;

import java.io.Serializable;
import java.util.Hashtable;

import com.surpass.vision.appCfg.GlobalConsts;

public class Point implements Serializable,Cloneable {
	Long id;
	String tagName;
	String desc; // FN_TAGNOTE
	String deviceName;
	String serverName;
	String enunit; // 数据单位 ，FN_ENUNITS
	String tagType; // 位号类型，FN_TAGTYPE
	
//	double hihiLimit;
//	double hiLimit;
//	double loLimit;
//	double loloLimit;	
	Double hihiLimit;
	Double hiLimit;
	Double loloLimit;
	Double loLimit;
	
	public double getHihiLimit() {
		return hihiLimit;
	}
	public void setHihiLimit(double hihiLimit) {
		this.hihiLimit = hihiLimit;
	}
	public double getHiLimit() {
		return hiLimit;
	}
	public void setHiLimit(double hiLimit) {
		this.hiLimit = hiLimit;
	}
	public double getLoLimit() {
		return loLimit;
	}
	public void setLoLimit(double loLimit) {
		this.loLimit = loLimit;
	}
	public double getLoloLimit() {
		return loloLimit;
	}
	public void setLoloLimit(double loloLimit) {
		this.loloLimit = loloLimit;
	}
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
		this.tagName = tagName.toUpperCase();
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName.toUpperCase();
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName.toUpperCase();
	}

	Hashtable<String,Float> values;
	
	public String wholeName() {
		return GlobalConsts.Key_ServerNamePre+this.serverName + GlobalConsts.Key_splitCharServerPoint + this.tagName;
	}
	
	@Override
	public Point clone() throws CloneNotSupportedException {
		return (Point) super.clone();
	}
}
