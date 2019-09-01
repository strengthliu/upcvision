package com.surpass.vision.domain;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class UserRight implements Serializable {
	
	boolean isCreater;
	boolean isOwnner;
	boolean isShared;
	public boolean isCreater() {
		return isCreater;
	}
	public void setCreater(boolean isCreater) {
		this.isCreater = isCreater;
	}
	public boolean isOwnner() {
		return isOwnner;
	}
	public void setOwnner(boolean isOwnner) {
		this.isOwnner = isOwnner;
	}
	public boolean isShared() {
		return isShared;
	}
	public void setShared(boolean isShared) {
		this.isShared = isShared;
	}
	public boolean canCreate(Integer role) {
		if(role<3)
			return true;
		else 
			return false;
	}
	public boolean canUpdate(Integer role) {
		if(role <=1)
			return true;
		if(this.isOwnner || this.isCreater)
			return true;
		return false;
	}
	
//	
//	Integer id;
//	List rs;
//	String group;
//	String typeName;
//	JSONObject j;
//	
//	// 流程图
//	List<PointGroup> ja;
//	
//	// 实时数据
//	
//	// 报警查询
//	
//	// 历史曲线
//	
//	// 直线报警
//	
//	// XY图
	
	
	
}
