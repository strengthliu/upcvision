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

import com.surpass.vision.server.Point;

/**---------------------------------------
 * @Title: Graph.java
 * @Package:com.surpass.vision.domain
 * ---------------------------------------
 * @Description:
 *     
 * ---------------------------------------
 * @author 刘强
 * 2019年7月27日 下午6:02:32
 * @version: v1.0  
 * ---------------------------------------
 */
public class GraphData extends FileList implements Serializable {
	
	Integer id;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	// 权限信息
	User owner; // 拥有者，默认为创建者
	User creater; // 创建者
	
	String otherrule2;
	public String getOtherrule2() {
		return otherrule2;
	}

	public void setOtherrule2(String otherrule2) {
		this.otherrule2 = otherrule2;
	}

	public String getOtherrule1() {
		return otherrule1;
	}

	public void setOtherrule1(String otherrule1) {
		this.otherrule1 = otherrule1;
	}

	String otherrule1;
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

	public List<User> getShared() {
		return shared;
	}

	public void setShared(List<User> shared) {
		this.shared = shared;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	List<User> shared; // 共享者 
	
	// 点位信息
	List<Point> points;
	
	// 更新数据方法
	
}
