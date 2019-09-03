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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.surpass.vision.server.Point;
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
public class PointGroup extends PointGroupData implements Serializable{
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

	List<User> sharedUsers; // 共享者 

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
		for(int i=0;i<sharedUsers.size();i++) {
			User u = sharedUsers.get(i);
			if(u.getId() != id2) {
				ret.add(IDTools.toString(u.getId()));
			}
		}
		return ret;
	}

	// 更新数据方法
}
