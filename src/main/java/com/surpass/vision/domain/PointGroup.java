/*******************************************************************************
 * Copyright (c) 2019 Qiang.liu.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Copyright Law 
 * of the People's Republic of China (2010 Amendment)
 * which accompanies this distribution, and is available at
 * http://www.law-lib.com/law/law_view.asp?id=6938
 *
 * Contributors:
 *     Qiang.liu 
 *******************************************************************************/
package com.surpass.vision.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.surpass.vision.server.Point;
import com.surpass.vision.server.pb.PointDisplayRule;
import com.surpass.vision.tools.IDTools;

/**---------------------------------------
 * @Title: PointGroup.java
 * @Package:com.surpass.vision.domain
 * ---------------------------------------
 * @Description:
 *     
 * ---------------------------------------
 * @author 刘强
 * 2019年7月27日 下午6:23:46
 * @version: v1.0  
 * ---------------------------------------
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class PointGroup extends PointGroupData implements Serializable,Cloneable{
	private static final long serialVersionUID = -4373082997833397493L;

	User createrUser; // 创建者

	String desc;
	Double id;
	String img = "/images/samples/300x300/1.jpg";
	String name;
	// 权限信息
	User ownerUser; // 拥有者，默认为创建者
	// 点位信息
	List<Point> pointList;
	
	// 图形上的显示格式。
	List<PointDisplayRule> pointDisplayRuleList;
	
	public List<PointDisplayRule> getPointDisplayRuleList() {
		return pointDisplayRuleList;
	}

	public void setPointDisplayRuleList(List<PointDisplayRule> pointDisplayRuleList) {
		this.pointDisplayRuleList = pointDisplayRuleList;
	}

	/**
	 * 在图形上，点位所显示的那个text的ID
	 * ---------------------------------------
	 * @author 刘强 2019年10月17日 上午7:23:47 
	 */
	private ArrayList<String> pointTextIDs;

	public ArrayList<String> getPointTextIDs() {
		return pointTextIDs;
	}

	public void setPointTextIDs(ArrayList<String> pointTextIDs) {
		this.pointTextIDs = pointTextIDs;
	}

	List<User> sharedUsers; // 共享者 
	List<DepartmentInfo> sharedDepartment;
	
	public List<DepartmentInfo> getSharedDepartment() {
		return sharedDepartment;
	}

	public void setSharedDepartment(List<DepartmentInfo> sharedDepartment) {
		this.sharedDepartment = sharedDepartment;
	}

	String type;
	public User getCreaterUser() {
		return createrUser;
	}

	public String getDesc() {
		return desc;
	}

	public Double getId() {
		return id;
	}

	public String getImg() {
		return img;
	}
	public String getName() {
		return name;
	}
	public User getOwnerUser() {
		return ownerUser;
	}

	public List<Point> getPointList() {
		return pointList;
	}

	public UserRight getRight(Double id) {
		UserRight ur = new UserRight();
		
		return ur;
	}

	public List<User> getSharedUsers() {
		return sharedUsers;
	}

	public String getType() {
		return type;
	}

	public void setCreaterUser(User createrUser) {
		this.createrUser = createrUser;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setId(Double id) {
		this.id = id;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwnerUser(User ownerUser) {
		this.ownerUser = ownerUser;
	}

	public void setPointList(List<Point> pointList) {
		this.pointList = pointList;
	}

	public void setSharedUsers(List<User> sharedUsers) {
		this.sharedUsers = sharedUsers;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> removeUser(Double id2) {
		ArrayList<String> ret = new ArrayList<String>();
		ArrayList<User> us = new ArrayList<User>();
		for(int i=0;i<sharedUsers.size();i++) {
			User u = sharedUsers.get(i);
			if(u.getId() != id2) {
				ret.add(IDTools.toString(u.getId()));
				us.add(u);
			}
		}
		this.setShared(IDTools.merge(ret.toArray()));
		this.setSharedUsers(us);
		return ret;
	}
	
	public List<String> removeDepart(Double id2) {
		ArrayList<String> ret = new ArrayList<String>();
		ArrayList<DepartmentInfo> us = new ArrayList<DepartmentInfo>();
		for(int i=0;i<this.sharedDepartment.size();i++) {
			DepartmentInfo u = sharedDepartment.get(i);
			if(u.getId().doubleValue() != id2) {
				ret.add(IDTools.toString(u.getId()));
				us.add(u);
			}
		}
		this.setShareddepart(IDTools.merge(ret.toArray()));
		this.setSharedDepartment(us);
		return ret;
	}

	// 更新数据方法
	
	@Override
	public PointGroup clone() throws CloneNotSupportedException {
		PointGroup ret = null;
		ret = (PointGroup) super.clone();
		if(this.pointList!=null) {
			List<Point> pointList = new ArrayList<Point>();
			for(int indpl=0;indpl<this.pointList.size();indpl++) {
				if(this.pointList.get(indpl)==null) {
					System.out.println("got null point....");
				}else
				pointList.add(this.pointList.get(indpl).clone());
			}
			ret.setPointList(pointList);
		}
		if(this.pointTextIDs!=null) {
			ArrayList<String> pointTextIDs= new ArrayList<String>();
			for(int indpt=0;indpt<this.pointTextIDs.size();indpt++)
				pointTextIDs.add(this.pointTextIDs.get(indpt));
			ret.setPointTextIDs(pointTextIDs);
		}
		if(this.createrUser!=null) {
			User createrUser = this.createrUser.clone(); // 创建者
			ret.setCreaterUser(createrUser);
		}
		if(this.ownerUser!=null) {
			User ownerUser = this.ownerUser.clone(); // 创建者
			ret.setOwnerUser(ownerUser);
		}
		if(this.sharedUsers!=null) {
			List<User> sharedUsers = new ArrayList<User>();
			for(int indsu=0;indsu<this.sharedUsers.size();indsu++)
				sharedUsers.add(this.sharedUsers.get(indsu).clone());
			ret.setSharedUsers(sharedUsers);
		}
		if(this.sharedDepartment!=null) {
			List<DepartmentInfo> sharedDepartment = new ArrayList<DepartmentInfo>();
			for(int indsu=0;indsu<this.sharedDepartment.size();indsu++)
				sharedDepartment.add(this.sharedDepartment.get(indsu).clone());
			ret.setSharedUsers(sharedUsers);
		}
		return ret;
	}
}
