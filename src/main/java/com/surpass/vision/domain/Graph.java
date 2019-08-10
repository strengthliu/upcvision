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
import java.util.Hashtable;
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
public class Graph implements Serializable {
	int changed;
	Hashtable<String,Graph> children;
	User creater; // 创建者
	Integer id;
	boolean isFile;
	boolean isSVG;
	String fileName;
	String img;
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	String name;

	String otherrule1;

	String otherrule2;

	// 权限信息
	User owner; // 拥有者，默认为创建者

	String path;
	
	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	String urlPath;

	//ArrayList<String >pointIDs;
	//ret.setChildren(fl.getChildren());

	// 点位信息
	List<Point> points;

	List<User> shared; // 共享者 

	public int getChanged() {
		return changed;
	}
	
	public Hashtable<String,Graph> getChildren() {
		return children;
	}

	public User getCreater() {
		return creater;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getOtherrule1() {
		return otherrule1;
	}

	public String getOtherrule2() {
		return otherrule2;
	}

	public User getOwner() {
		return owner;
	}

	public String getPath() {
		return path;
	}
	public List<Point> getPoints() {
		return points;
	}

//	public ArrayList<String> getPointIDs() {
//		return pointIDs;
//	}

	public List<User> getShared() {
		return shared;
	}
	public int isChanged() {
		return changed;
	}
	
	public boolean isFile() {
		return isFile;
	}
	public boolean isSVG() {
		return isSVG;
	}

	public void setChanged(int changed) {
		this.changed = changed;
	}

	public void setChildren(Hashtable<String,Graph> children) {
		this.children = children;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOtherrule1(String otherrule1) {
		this.otherrule1 = otherrule1;
	}

	public void setOtherrule2(String otherrule2) {
		this.otherrule2 = otherrule2;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public void setPath(String path) {
		this.path = path;
	}

//	public void setPointIDs(ArrayList<String> pointIDs) {
//		this.pointIDs = pointIDs;
//	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public void setShared(List<User> shared) {
		this.shared = shared;
	}
	
	public void setSVG(boolean isSVG) {
		this.isSVG = isSVG;
	}
	
	// 更新数据方法
	
}
