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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
//	Hashtable<String,Graph> children;
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
			Enumeration e=children.elements();
			while(e.hasMoreElements()){
				Graph _g =(Graph)e.nextElement();
				_g.getIDList(ret);
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

//	public Double getId() {
//		return id;
//	}

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

//	public void setId(Double id) {
//		this.id = id;
//	}

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
					Graph child = (Graph) children.get(k);
					if(!child.isFile) {
						if(path.contains(child.getPath()))
							return child.getParentByPath(path);
					}
				}
			}
		}
		return this;
	}

	/**
	 * 合并两个图（将rtd合并到this），结构以path为准，如果是文件图形重复，以rtd为准，更新this的图。
	 * 
	 * 添加或修改一个子。按目录结构，如果不是直接的子，就把中间的孩子们都补齐。
	 * @param rtd
	 */
	public void addOrUpdateChild(Graph rtd) {
		if(this.isFile) return; // 不能对一个文件添加子
		if(this.children==null) this.children = new Hashtable<String,Graph>();
		if(rtd==null) return;
		if(rtd.getId()==null) { // 删除指令，一种特殊处理情况。
			System.out.println("rtd.getId()="+rtd.getId()+" ret.getName()="+rtd.getName());
			if(StringUtil.isBlank(rtd.getPath()))
					return;
			else
				this.children.remove(rtd.getPath());
			return;
		}
//******************************************************************************************
		boolean hasYangChild = false;
		Enumeration e = this.children.keys();
		Graph yangChild;
		// 先找到我最接近rtd的孩子，叫yangChild，是个目录
		// 如果this跟rtd是同一级别，执行所有的相对应的子合并
		if(this.getPath().contentEquals(rtd.getPath())) {
			// 如果rtd是文件，就加入（替换），
			if(rtd.isFile) this.children.put(rtd.getWholePath(), rtd);
			else {//否则，遍历双方的子
				if(rtd.getChildren()==null||rtd.getChildren().size()==0) {//如果rtd是空目录，就填满
					rtd.children = new Hashtable<String,Graph>();
				}
				//执行所有的相对应的子合并。遍历rtd的子
				Hashtable<String,Graph> hc = rtd.getChildren();
				Enumeration ertd = hc.keys();
				while(ertd.hasMoreElements()) {
					Graph rtdChild = hc.get(ertd.nextElement());
					if(!this.getChildren().containsKey(rtdChild.getWholePath())) {// 如果rtd有，而this没有，直接this加入
						this.getChildren().put(rtdChild.getWholePath(), rtdChild);//这里直接加进去了，不用判断rtdChild是否是空目录，前面已经处理过了。
					}else { // 如果this有，rtd也有，
						Graph thisChild = (Graph) this.getChildren().get(rtdChild.getWholePath());
						// 		如果这个子是文件，就替换
						if(thisChild.isFile && rtdChild.isFile) this.getChildren().put(rtdChild.getWholePath(), rtdChild);
						if(thisChild.isFile && !rtdChild.isFile) throw new IllegalStateException("同一级有重名的文件和目录。");
						// 		如果这个子是目录，就递归
						if(!thisChild.isFile && !rtdChild.isFile) thisChild.addOrUpdateChild(rtdChild);
					}
				}
			}
		} else { // 否则找到this的子里这个path的那个目录，然后递归
			if(!rtd.getPath().contains(this.getPath())) {
				if(!this.getPath().contains(rtd.getPath()))return; // 如果这两个不是同一目录下的，就错了，啥也不做返回。
				// rtd比this级别高，将自己被完整
				rtd.addOrUpdateChild(rtd);// rtd里加上自己,
				copyFromGraph(rtd);//自己的子换成rtd的子，自己的名字内容等都换成rtd
			}
			// 遍历this的子，找到子里面rtd目录
			if(children.containsKey(rtd.getWholePath())) { // 如果子里面有rtd，这个子递归
				Graph gChild = (Graph) children.get(rtd.getWholePath());
				gChild.addOrUpdateChild(rtd);
			}else{// 补足空目录结构到rtd，再递归
				Graph graphTree = GraphManager.getGraphTree();// 取到graph里这个
				Graph thisWholePath = graphTree.getChildByPath(this.getPath());
				Graph gDir = thisWholePath.cutForChild(rtd);
				this.getChildren().put(gDir.getWholePath(), gDir);// 把中间目录补完整
				gDir.addOrUpdateChild(rtd);// 递归
			}
		}
//		
//		// 否则找子
//		while(e.hasMoreElements()) {
//			Graph child = (Graph) this.children.get(e.nextElement());
//			if(child.isFile) {}
//			else {
//				if(rtd.getPath().contains(child.getPath())) {
//					hasYangChild = true;
//					child.addOrUpdateChild(rtd);
//					// 只这个子执行，不再执行后面的了。
//					return;
//				}
//			}
//		}
//		// 已经到了最接近层，this就是yangChild
//		
//		// 修剪这个图形，使之成为从根开始的图形。
//		if(!hasYangChild) {
//			Graph graphTree = GraphManager.getGraphTree(); // 取出整个图形树，没有文件的，只有目录
//			// 从树中找到yangChild的孩子中，rtd这一支的树，叫yangPar
//			Graph thisTreeNode = (Graph) graphTree.getChildByPath(this.getPath());// 取出当前图形的父亲或自己（如果是目录），是个目录
//			if(thisTreeNode == null) {
//				System.out.println("path="+this.getPath());
//			}
//			
//			Graph _g = thisTreeNode.cutForChild(rtd); // 把这个目录修剪成只有rtd这一支
//			// 在yangPar中加入rtd这个孩子
//			if(_g.getPath().contentEquals(rtd.getPath())) { // 如果修剪后的目录与rtd目录一致，就加进去
//				if(rtd.isFile)
//					_g.children.put(rtd.getWholePath(), rtd);
//				else { // 如果是目录，就所子加进去
//					Enumeration ertd = rtd.getChildren().keys();
//					while(ertd.hasMoreElements()) {
//						String key = (String) ertd.nextElement();
//						_g.children.put(key, rtd.getChildren().get(key));
//					}
//					// 如果没有子？？
//				}
//			}
//			else {
//				System.out.println("_g不是rtd是亲爹。");
//				throw new IllegalStateException("_g不是rtd是亲爹。");
//			}
//			
//			Graph _gChild = null ;
//			if(thisTreeNode.children.size()==1) {
//				Collection<?> c = thisTreeNode.children.values();
//				_gChild = (Graph) c.toArray()[0];
//				this.children.put(_gChild.getWholePath(), _gChild);
//			} else {
//				System.out.println(_g.children.size());
//				// 如果thisTreeNode没有子，就是前面加入的是一个相同的空目录，什么也不用做
////						throw new IllegalStateException("初始化时没有注册过"+_g.getPath()+"这个目录。");
//			}
//		}
	}

	private void copyFromGraph(Graph rtd) { // 自己的子换成rtd的子，自己的名字内容等都换成rtd
		this.setChanged(rtd.getChanged());
		this.setChildren(rtd.getChildren());
		this.setCreater(rtd.getCreater());
		this.setCreaterUser(rtd.getCreaterUser());
		this.setDesc(rtd.getDesc());
		this.setFile(rtd.isFile);
		this.setFileName(rtd.getFileName());
		this.setId(rtd.getId());
		this.setImg(rtd.getImg());
		this.setName(rtd.getName());
		this.setNickName(rtd.getNickName());
		this.setOtherrule1(rtd.getOtherrule1());
		this.setOtherrule2(rtd.getOtherrule2());
		this.setOtherrule3(rtd.getOtherrule3());
		this.setOtherrule4(rtd.getOtherrule4());
		this.setOtherrule5(rtd.getOtherrule5());
		this.setOwner(rtd.getOwner());
		this.setOwnerUser(rtd.getOwnerUser());
		this.setPath(rtd.getPath());
		this.setPointList(rtd.getPointList());
		this.setPoints(rtd.getPoints());
		this.setPointTextIDs(rtd.getPointTextIDs());
		this.setShared(rtd.getShared());
		this.setShareddepart(rtd.getShareddepart());
		this.setSharedDepartment(rtd.getSharedDepartment());
		this.setSharedUsers(rtd.getSharedUsers());
		this.setSVG(rtd.isSVG);
		this.setType(rtd.getType());
		this.setUrlPath(rtd.getUrlPath());
	}

	/**
	 * 返回指定pathName的目录孩子。
	 * 
	 * TODO: 可能有BUG
	 * 
	 * @param pathName
	 * @return
	 */
private Graph getChildByPath(String pathName) {
		if(StringUtil.isBlank(pathName))
			return null;
		if (this.name.equals(pathName)) // name和pathName相等的，是目录，所以只会返回目录。
			return this;
		if (pathName.contains(this.name)) {
			if (this.children == null) {
				return null;
			}
			Enumeration<String> e = this.children.keys();
			while(e.hasMoreElements()) {
				String key = (String) e.nextElement();
				Graph f = (Graph) this.children.get(key);
				if(pathName.contains(f.getPath())) {
					return f.getChildByPath(pathName);
				}
			}
		}
		return null;
	}

//	private void copyGraph(Graph g) {
//		this.setChildren(g.getChildren());
//		this.setCreater(g.getCreater());
//		this.setCreaterUser(g.getCreaterUser());
//		this.setDesc(g.getDesc());
//		this.set
//	}
	
	/**
	 * 为了孩子rtd减去自己的所有分支，只保留rtd这一系。返回最近rtd的那个孩子。
	 * @param rtd
	 * @return
	 */
	private Graph cutForChild(Graph rtd) {
		if(this.getPath().contentEquals(rtd.getPath())) {
			this.children = new Hashtable<String,Graph>();
			return this;
		}
		else {
			if(this.children!=null) {
				Enumeration<String> e = this.children.keys();
				Graph _g = null;
				while(e.hasMoreElements()) {
					String key = (String) e.nextElement();
					Graph g = (Graph) this.children.get(key);
					if(rtd.getPath().contains(g.getPath())) {
						_g = g.cutForChild(rtd);
					} else {
						this.children.remove(key);
					}
				}
				if(_g!=null)
					return _g;
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
			this.children.remove(rtd.getWholePath());
	}

	public Graph clearLeaf() {
		if(this.isFile)
			throw new IllegalStateException("图形文件不能再去除页子了。");
		if(this.children!=null) {
			Enumeration<String> e = this.children.keys();
			while(e.hasMoreElements()) {
				String key = (String) e.nextElement();
				Graph _g = (Graph) children.get(key);
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
		Graph ret = (Graph) super.clone();
		Hashtable<String,Graph> children = new Hashtable<String,Graph>();
		if(this.children!=null) {
			Enumeration<String> e = this.children.keys();
			while(e.hasMoreElements()) {
				String key = (String) e.nextElement();
				children.put(key, ((Graph) this.children.get(key)).clone());
			}
		}
		ret.setChildren(children);
		return ret;
	}
}
