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
import java.util.List;

import com.surpass.vision.server.Point;

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
public class PointGroup extends PointGroupData implements Serializable{
	private static final long serialVersionUID = -4373082997833397493L;
	String type;
	Integer id;
	String name;
	
	// 权限信息
	User owner; // 拥有者，默认为创建者
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getOwnerUser() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public User getCreaterUser() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

	public List<User> getSharedUser() {
		return shared;
	}

	public void setShared(List<User> shared) {
		this.shared = shared;
	}

	public List<Point> getServerPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	User creater; // 创建者
	
	List<User> shared; // 共享者 
	
	// 点位信息
	List<Point> points;
	
	// 更新数据方法
}
