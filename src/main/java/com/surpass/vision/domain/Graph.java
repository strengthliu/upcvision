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
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.jsoup.helper.StringUtil;

import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.graph.GraphDataManager;
import com.surpass.vision.graph.GraphManager;
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
public class Graph extends FileList implements Serializable,Cloneable {
	int changed;
	Hashtable<String,Graph> children;
	String fileName;
	String urlPath;

	public Graph() {
		super();
		this.setType(GlobalConsts.Type_graph_);
	}

	public int getChanged() {
		return changed;
	}

	public List<Double> getIDList(ArrayList<Double> ret){
		if(ret == null)
			ret = new ArrayList<Double>();
		ret.add(this.id);
		if(this.children!=null) {
			for(Map.Entry<String, Graph> entry: children.entrySet()){
				entry.getValue().getIDList(ret);
				}
		}
		return ret;
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

	/**
	 * 获取最接近path的子
	 * 
	 * @param path
	 * @return
	 */
	public Graph getParentByPath(String path) {
		if(this.isFile)
			return null;
		if(StringUtil.isBlank(path))
			return null;
		if(StringUtil.isBlank(this.getPath()))
			return this;
		// 如果是亲子，就直接返回。
		if(this.getPath().contentEquals(path))
			return this;
		else { // 否则继续查孙子
			if(this.children!=null) {
				Enumeration<String> e = this.children.keys();
				while(e.hasMoreElements()) {
					String k = (String) e.nextElement();
					Graph child = this.children.get(k);
					if(!child.isFile) {
						if(path.contains(child.getPath()))
							return child.getParentByPath(path);
					}
				}
			}
		}
		return this;
	}

	public void addOrUpdateChild(Graph rtd) {
		if(this.children==null) {
			this.children = new Hashtable<String,Graph>();
		}
		if(rtd.getId()==null) {
			System.out.println("rtd.getId()="+rtd.getId());
			this.children.remove(rtd.getPath());
		}
		else {
			// 如果rtd的目录不是this的目录，就说明，级别不对
			if(!StringUtil.isBlank(this.getPath()) && rtd.getPath().contentEquals(this.getPath()))		
				this.children.put(rtd.getWholePath(), rtd);		
			else {
				boolean hasYangChild = false;
				if(this.children==null) {
					this.children = new Hashtable<String,Graph>();
				}
				Enumeration e = this.children.keys();
				Graph yangChild;
				// 先找到我最接近rtd的孩子，叫yangChild，是个目录
				while(e.hasMoreElements()) {
					Graph child = this.children.get(e.nextElement());
					if(child.isFile) {}
					else {
						if(rtd.getPath().contains(child.getPath())) {
							hasYangChild = true;
							child.addOrUpdateChild(rtd);
							// 只这个子执行，不再执行后面的了。
							return;
						}
					}
				}
				// 已经到了最接近层，this就是yangChild
				if(!hasYangChild) {
					Graph g = GraphManager.getGraphTree();
					// 从树中找到yangChild的孩子中，rtd这一支的树，叫yangPar
					Graph _g = (Graph) g.getChild(this.getPath());
					Graph _g_ = _g.cutForChild(rtd);
					Graph _gChild = null ;
					if(_g.children.size()==1) {
						Collection<?> c = _g.children.values();
						_gChild = (Graph) c.toArray()[0];
					} else {
						throw new IllegalStateException("初始化时没有注册过"+_g.getPath()+"这个目录。");
					}
					if(_gChild==null) {
						// 处理异常
					} else {
						_g = _gChild;
					}
					// 在yangPar中加入rtd这个孩子
					_g_.children.put(rtd.getWholePath(), rtd);
					// yangChild中加入yangPar这个孩子
					this.children.put(_g.getPath(), _g);
				}
			}
		}
	}

	
	
	private Graph cutForChild(Graph rtd) {
		if(this.getPath().contentEquals(rtd.getPath())) {
			this.children = new Hashtable<String,Graph>();
			return this;
		}
		else {
			if(this.children!=null) {
				Enumeration<String> e = this.children.keys();
				while(e.hasMoreElements()) {
					String key = (String) e.nextElement();
					Graph g = this.children.get(key);
					if(rtd.getPath().contains(g.getPath())) {
						g.cutForChild(rtd);
					} else {
						this.children.remove(key);
					}
				}
				return this;
			}else
				return this;
		}
	}

	public void deleteChild(Graph rtd) {
		if(this.children==null) {
			this.children = new Hashtable<String,Graph>();
			return;
		}
		if(rtd!=null && !StringUtil.isBlank(rtd.getWholePath()))
			this.children.remove(rtd.getPath());
	}

	public Graph clearLeaf() {
		if(this.isFile)
			throw new IllegalStateException("图形文件不能再去除页子了。");
		if(this.children!=null) {
			Enumeration<String> e = this.children.keys();
			while(e.hasMoreElements()) {
				String key = (String) e.nextElement();
				Graph _g = children.get(key);
				if(_g.isFile)
					children.remove(key);
				else {
					_g.clearLeaf();
				}
			}
		}else
			return this;
		
		return this;
	}

	public Graph copyRootNode() {
		Graph g = new Graph();
		g.setChanged(this.getChanged());
		g.setCreater(this.getCreater());
		g.setCreaterUser(this.getCreaterUser());
		g.setDesc(this.getDesc());
		g.setFile(this.isFile());
		g.setId(this.getId());
		g.setImg(this.getImg());
		g.setName(this.getName());
		g.setOtherrule1(this.getOtherrule1());
		g.setOtherrule2(this.getOtherrule2());
		g.setOwner(this.getOwner());
		g.setOwnerUser(this.getOwnerUser());
		g.setPath(this.getPath());
		g.setPointList(this.getPointList());
		g.setPoints(this.getPoints());
		g.setShared(this.getShared());
		g.setSharedUsers(this.getSharedUsers());
		g.setSVG(this.isSVG());
		g.setType(this.getType());
		g.setUrlPath(this.getUrlPath());
		return g ;
	}

	@Override
	public Graph clone() {
		try {
			Graph ret = (Graph) super.clone();
			Hashtable<String,Graph> children = new Hashtable<String,Graph>();
			if(this.children!=null) {
				Enumeration<String> e = this.children.keys();
				while(e.hasMoreElements()) {
					String key = (String) e.nextElement();
					children.put(key, this.children.get(key).clone());
				}
			}
			return ret;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
