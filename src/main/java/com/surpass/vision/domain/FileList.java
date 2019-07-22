package com.surpass.vision.domain;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class FileList {
	boolean isFile;
	String name;
	
	public boolean isFile() {
		return isFile;
	}

	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWholePath() {
		return "";
	}

	Hashtable<String,FileList> children;

	int changed = 0;
	
	Hashtable<String,FileList> change;
	
	public Hashtable<String,FileList> getChange() {
		Hashtable<String,FileList> ret = change;
		change = null;
		return ret;
	}
	
	public JSONObject toJSONObject() {
		JSONObject ret = new JSONObject();
		ret.put("name", name);
		ret.put("isFile", this.isFile);
		ret.put("changed", changed);
		JSONArray jChildren = new JSONArray();
				
		return null;
	}
	
	public synchronized void addChildren(Hashtable<String,FileList> _children) {
		if(children == null) children = new Hashtable<String,FileList>();
		if(change == null) change = new Hashtable<String,FileList>();
		Hashtable<String,FileList> deleteChildren = new Hashtable<String, FileList>(this.children);
		Enumeration e = _children.keys();
		while(e.hasMoreElements()) {
			String k = (String) e.nextElement();
			if(!this.children.containsKey(k)) {
				FileList child = _children.get(k);
				child.changed = 1; // 添加
				change.put(child.getName(), child);
				children.put(child.getName(), child);
			} 
			deleteChildren.remove(k);
		}
		Enumeration ed = deleteChildren.keys();
		while(ed.hasMoreElements()) {
			String k = (String) ed.nextElement();
			FileList child = _children.get(k);
			child.changed = -1; // 删除
			change.put(child.getName(), child);
		}		
	}
	// @Deplicate
	public synchronized boolean addChild(FileList child) {
		
		if(children == null) children = new Hashtable<String,FileList>();
		if(children.containsKey(child.getName())) {
			return false;
		} else {
			if(change == null) change = new Hashtable<String,FileList>();
			change.put(child.getName(), child);
			children.put(child.getName(), child);
		}
		
		return true;

	}
	
	public boolean hasChild(FileList child) {
		if(this.children.containsKey(child.getName()))
			return true;
		else 
			return false;
	}
	
}
