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

import com.surpass.vision.appCfg.GlobalConsts;
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
public class Graph extends FileList implements Serializable {
	public Graph() {
		super();
		this.setType(GlobalConsts.Type_graph_);
	}
	int changed;
	Hashtable<String,Graph> children;
	String fileName;

	String urlPath;

	public int getChanged() {
		return changed;
	}

	@Override
	public Hashtable getChildren() {
		return this.children;
	}

	public String getFileName() {
		return fileName;
	}

	public Double getId() {
		return id;
	}

	//ArrayList<String >pointIDs;
	//ret.setChildren(fl.getChildren());

	public String getImg() {
		return img;
	}

	public String getName() {
		return name;
	}


	public String getPath() {
		return path;
	}

	public String getUrlPath() {
		return urlPath;
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
	
	@Override
	public void setChildren(Hashtable children) {
		this.children = children;
	}
	
	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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


	public void setPath(String path) {
		this.path = path;
	}


//	public void setPointIDs(ArrayList<String> pointIDs) {
//		this.pointIDs = pointIDs;
//	}

	public void setSVG(boolean isSVG) {
		this.isSVG = isSVG;
	}
	
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
	
	// 更新数据方法
	
}
