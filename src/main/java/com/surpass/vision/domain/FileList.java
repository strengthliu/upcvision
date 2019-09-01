package com.surpass.vision.domain;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.surpass.vision.graph.GraphManager;

public class FileList extends PointGroup implements Serializable {
	/**
	 * 0：没有变化
	 * 1：该文件为新增文件
	 * -1：该文件为删除文件
	 */
	int changed = 0;

	Hashtable<String, FileList> children;

	String img;

	boolean isFile;
	boolean isSVG;
	String name;


	String path;

	ArrayList<String> pointIDs;
	public FileList() {
		super();
		this.children = new Hashtable<String, FileList>();
	}
	public FileList(FileList repo) {
		super();
		this.isFile = repo.isFile;
		this.name = repo.getName();
		this.path = repo.getPath();
		this.changed = repo.changed;
		this.children = new Hashtable<String, FileList>(repo.children);
	}
	/**
	 * 
	 * @param child
	 * @return
	 */
	@Deprecated
	public synchronized boolean addChild(FileList child) {
		if (children == null)
			children = new Hashtable<String, FileList>();
		if (children.containsKey(child.getName())) {
			return false;
		} else {
			children.put(child.getName(), child);
		}

		return true;

	}
	

	/***
	 * TODO: 改写这个方法 这个写的不好，这个方法应该放到外面才对，这样跟GraphManager耦合太大了。
	 * 
	 * @param _children
	 */
	public synchronized void addChildren(Hashtable<String, FileList> _children) {
//		System.out.println("当前待变化："+GraphManager.getCurrentUpdate().size());
		if (children == null)
			children = new Hashtable<String, FileList>();
		// TODO: 检查一下，是否克隆正确
		Hashtable<String, FileList> deleteChildren = new Hashtable<String, FileList>(this.children);
		Enumeration<String> e = _children.keys();
		while (e.hasMoreElements()) {
			String k = (String) e.nextElement();
			// 如果当前孩子里没有指定的这个，就更新
			if (!this.children.containsKey(k)) {
				// 如果当前不是我在更新，就退出不更新了。
				if (!GraphManager.beginUpdate(Thread.currentThread().getName()))
					return;
				FileList child = _children.get(k);
				child.changed = 1; // 添加
				// 如果当前不是在更新中，说明没有更新过，更新版本
				if (!GraphManager.isUpdating())
					GraphManager.updateVersion(Thread.currentThread().getName());
				// 添加一个增加变化
				GraphManager.addChange(child);
				// 添加一个孩子
				children.put(k, child);	
			} else
				// 否则就是当前孩子里已经包含，就在待删除列表中去掉
				deleteChildren.remove(k);
		}
		
		// 处理删除的条目
		Enumeration ed = deleteChildren.keys();
		while (ed.hasMoreElements()) {
			//
			if (!GraphManager.beginUpdate(Thread.currentThread().getName()))
				return;
			// 如果当前不是在更新中，说明没有更新过，更新版本
			if (!GraphManager.isUpdating())
				GraphManager.updateVersion(Thread.currentThread().getName());
			
			String k = (String) ed.nextElement();
			FileList child = deleteChildren.get(k);
			child.setChanged(-1);  // 删除
			//
			System.out.println("当前待变化："+GraphManager.getCurrentUpdate().size());
			System.out.println("添加删除元素");
			GraphManager.addChange(child);
			this.children.remove(k);
			System.out.println("当前待变化："+GraphManager.getCurrentUpdate().size());
		}
		if (GraphManager.beginUpdate(Thread.currentThread().getName()))
			return;
	}

	public int getChanged() {
		return changed;
	}

	public FileList getChild(String pathName) {
		if (this.name.equals(pathName))
			return this;
		if (pathName.contains(this.name)) {
			if (this.children == null) {
				return null;
			}
			String[] names = this.name.split("\\"+File.separator);
			String[] pathNames = pathName.split("\\"+File.separator);
			String nextChildName = "";
			for(int i=0;i<pathNames.length;i++) {
				if(nextChildName.length()==0) 
					nextChildName = pathNames[i];
				else
					nextChildName = nextChildName + File.separator + pathNames[i];
				if(i>names.length) break;
			}
			if(this.children.containsKey(nextChildName)) {
				FileList fl = this.children.get(nextChildName);
				if(fl != null && !fl.isFile) {
					if(nextChildName.contentEquals(pathName))
						return fl;
					else
						return fl.getChild(pathName);
				}
			}else {
				return null;
			}
		}
		return null;
	}

	public Hashtable<String, FileList> getChildren() {
		return children;
	}

	public String getImg() {
		return img;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public ArrayList<String> getPointIDs() {
		return pointIDs;
	}

	public String getWholePath() {
		return this.path+File.separator+this.name;
	}

	public boolean hasChild(FileList child) {
		if (this.children.containsKey(child.getName()))
			return true;
		else
			return false;
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

	public void setChildren(Hashtable<String, FileList> children) {
		this.children = children;
	}

	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}

	public void setImg(String img) {
		this.img = img;
	}

//	Hashtable<String, FileList> change;

//	public Hashtable<String, FileList> getChange() {
//		if (change != null) {
//			Hashtable<String, FileList> ret = new Hashtable<String, FileList>(change);
//			change = null;
//			return ret;
//		} else
//			return null;
//	}

	public void setName(String name) {
		this.name = name;
	}

//	public JSONObject getJSONChange() {
//		JSONObject ret = new JSONObject();
//		ret.put("name", name);
//		ret.put("path", path);
//		ret.put("isFile", isFile);
//		ret.put("changed", changed);
//		if (changed != -1) {
//			JSONArray jChildren = new JSONArray();
//			Enumeration<String> e = this.children.keys();
//			while (e.hasMoreElements()) {
//				String key = (String) e.nextElement();
//				FileList fl = children.get(key);
//				JSONObject jo = fl.toJSONObject();
//				jChildren.add(jo);
//			}
//			ret.put("children", jChildren);
//		}
//		return ret;
//	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setPointIDs(ArrayList<String> pointIDs) {
		this.pointIDs = pointIDs;
	}

	public void setSVG(boolean isSVG) {
		this.isSVG = isSVG;
	}

	
//	public JSONObject toJSONObject() {
//		JSONObject ret = new JSONObject();
//		ret.put("name", name);
//		ret.put("path", path);
//		ret.put("isFile", isFile);
//		ret.put("changed", changed);
//		{
//			JSONArray jChildren = new JSONArray();
//			Enumeration<String> e = this.children.keys();
//			while (e.hasMoreElements()) {
//				String key = (String) e.nextElement();
//				FileList fl = children.get(key);
//				JSONObject jo = fl.toJSONObject();
//				jChildren.add(jo);
//			}
//			ret.put("children", jChildren);
//		}
//		return ret;
//	}

}
