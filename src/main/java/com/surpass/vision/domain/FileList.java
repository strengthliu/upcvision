package com.surpass.vision.domain;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.jsoup.helper.StringUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.surpass.vision.appCfg.ServerConfig;
import com.surpass.vision.graph.GraphManager;

public class FileList extends PointGroup implements Serializable,Cloneable {
	/**
	 * 0：没有变化 1：该文件为新增文件 -1：该文件为删除文件
	 */
	int changed = 0;
//	Hashtable<String, FileList> children;
	Hashtable children;
	String img;
	boolean isFile;
	boolean isSVG;
	String name;
	String path;
	String nickName;
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public FileList() {
		super();
		this.children = new Hashtable<String, FileList>();
	}

//	public FileList(FileList repo) {
//		super();
//		this.isFile = repo.isFile;
//		this.name = repo.getName();
//		this.path = repo.getPath();
//		this.changed = repo.changed;
//		this.children = new Hashtable<String, FileList>(repo.children);
//	}

	/**
	 * 用ID索引的FileList。
	 * 所有的子都共用这一个。
	 * ---------------------------------------
	 * @author 刘强 2019年10月6日 下午1:04:06 
	 */
	private static Hashtable<Double, FileList> inds = new Hashtable<Double, FileList>();//GraphManager.getInds();
	/**
	 * 按全路径建立的索引
	 */
	private static Hashtable<String, FileList> indpath = new Hashtable<String, FileList>();

	/**
	 * 
	 * @param child
	 * @return
	 */
	public boolean addChild(FileList child) {
		if (children == null)
			children = new Hashtable<String, FileList>();
		if (children.containsKey(child.getName())) {
			return false;
		} else {
			FileList fl = getLatestParent(this, child);
			String path = child.path.substring(fl.path.length());
			// 如果还有路径，就按路径加上子
			if (!StringUtil.isBlank(path)) {
				String splitChar = "";
				if (File.separator.contentEquals("\\"))
					splitChar = "\\\\";
				else
					splitChar = File.separator;
				String[] folds = path.split(splitChar);
				String lastPath = fl.getPath();
				//
				for (int ifold = 0; ifold < folds.length; ifold++) {
					lastPath = lastPath + folds[ifold];
					// 这里要用clone，原因是child可能是graph，如果new一个FileList，后面类型转换时就会出错
					// 这里要用new Graph()，原因是child可能是graph，如果new一个FileList，后面类型转换时就会出错
					FileList chi = (FileList) new Graph();
					chi.setPath(lastPath);
					chi.setFile(false);
					chi.setImg("images/logo.png");
					fl.getChildren().put(chi.getWholePath(), chi);
					fl = chi;
				}
				fl.getChildren().put(child.getWholePath(), child);
				inds.put(child.getId(), child);
				indpath.put(child.getWholePath(), child);
			}else {
				this.getChildren().put(child.getWholePath(), child);
				inds.put(child.getId(), child);
				indpath.put(child.getWholePath(), child);
				System.out.println("22");
			}
		}

		return true;

	}

	/**
	 * 找到最匹配的那个目录，如 child是c:\a\b\c，this是c:\a和c:\b，就返回c:\a,就是说，返包含最多的那一个
	 * 
	 * @param al
	 * @param par
	 * @param child
	 * @return
	 */
	private FileList getLatestParent(FileList par, FileList child) {
		if (par == null)
			par = this;
		// 如par匹配，就查孩子
		if (child.getPath().contains(par.getPath())) {
			boolean hasMore = false;
			Enumeration<FileList> e2 = par.children.elements();
			while (e2.hasMoreElements()) {
				FileList fl = e2.nextElement();
				// 如果有一个孩子是目录、是匹配的，就递归这个孩子
				if (!fl.isFile && child.getPath().contains(fl.getPath())) {
					hasMore = true;
					return getLatestParent(fl, child);
				}
			}
			// 如果一个孩子也不匹配，就返回自己。
			return par;
		} else // 如果par不匹配，就返回par
			return par;
	}

	public String getWholePath() {
		//if(true) return this.getOtherrule1();
		if (!StringUtil.isBlank(this.path)) {
			if (this.isFile) {
				if (!StringUtil.isBlank(this.name))
					if(this.isSVG) {
						if(this.name.endsWith(".svg"))
							return this.path + File.separator + this.name;
						else
							return this.path + File.separator + this.name+".svg";
					}
					else
						return this.path + File.separator + this.name;
				else
					return null;
			} else {
				return this.path;
			}
		} else
			return name;
	}

	/***
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
				inds.put(child.getId(), child);
				indpath.put(child.getWholePath(), child);
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
			child.setChanged(-1); // 删除
			//
			System.out.println("当前待变化：" + GraphManager.getCurrentUpdate().size());
			System.out.println("添加删除元素");
			GraphManager.addChange(child);
			this.children.remove(k);
			System.out.println("当前待变化：" + GraphManager.getCurrentUpdate().size());
		}
		if (GraphManager.beginUpdate(Thread.currentThread().getName()))
			return;
	}

	public int getChanged() {
		return changed;
	}

//	/**
//	 * 返回指pathName的孩子。
//	 * 
//	 * TODO: 可能有BUG
//	 * 
//	 * @param pathName
//	 * @return
//	 */
//	public FileList getChild(String pathName) {
//		if(StringUtil.isBlank(pathName))
//			return null;
//		if (this.name.equals(pathName))
//			return this;
//		if (pathName.contains(this.name)) {
//			if (this.children == null) {
//				return null;
//			}
////			String[] names = this.name.split("\\" + File.separator);
////			String[] pathNames = pathName.split("\\" + File.separator);
////			String nextChildName = "";
////			for (int i = 0; i < pathNames.length; i++) {
////				if (nextChildName.length() == 0)
////					nextChildName = pathNames[i];
////				else
////					nextChildName = nextChildName + File.separator + pathNames[i];
////				if (i > names.length)
////					break;
////			}
//			Enumeration e = this.children.keys();
//			while(e.hasMoreElements()) {
//				String key = (String) e.nextElement();
//				FileList f = this.children.get(key);
//				if(pathName.contains(f.getPath())) {
//					return f.getChild(pathName);
//				}
//			}
////			
////			if (this.children.containsKey(nextChildName)) {
////				FileList fl = this.children.get(nextChildName);
////				if (fl != null && !fl.isFile) {
////					if (nextChildName.contentEquals(pathName))
////						return fl;
////					else
////						return fl.getChild(pathName);
////				}
////			} else {
////				return null;
////			}
//		}
//		return null;
//	}

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

	public void setName(String name) {
		this.name = name;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setSVG(boolean isSVG) {
		this.isSVG = isSVG;
	}

	public void addChildren(ArrayList<FileList> children1) {
		for (int i = 0; i < children1.size(); i++) {
			FileList child = children1.get(i);

			this.children.put(child.getWholePath(), child);
			/**
			 * 修改：增加目录分享功能，所以目录也要保存起来
			 */
//			if (child.isFile)
				inds.put(child.getId(), child);
				indpath.put(child.getWholePath(), child);

		}
	}
	
	public FileList getChild(Double id) {
		return inds.get(id);
	}

	public FileList getChild(String wholepath) {
		return indpath.get(wholepath);
	}

	@Override
	public FileList clone() {
		FileList ret = null;
		try {
			ret = (FileList)super.clone();
			if(this.children!=null) {
				Hashtable<String,FileList> children = new Hashtable<String,FileList>();
//				Hashtable<Double, FileList> inds = new Hashtable<Double, FileList>();
				Enumeration<String> e = this.children.keys();
				while(e.hasMoreElements()) {
					String key = (String) e.nextElement();
					children.put(key, ((FileList) this.children.get(key)).clone());
				}
				ret.setChildren(children);
//				ret.buildInds(inds);
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	private void buildInds(Hashtable<Double, FileList> inds,Hashtable<String, FileList> indpath) {
		if(inds == null) inds = new Hashtable<Double, FileList>();
		if(indpath == null) indpath = new Hashtable<String, FileList>();
		inds.put(this.getId(), this);
		indpath.put(this.getWholePath(), this);
		
		this.inds = inds;
		this.indpath = indpath;
		if(this.children!=null) {
			Enumeration<String> e = this.children.keys();
			while(e.hasMoreElements()) {
				String key = e.nextElement();
				FileList fl = (FileList) this.children.get(key);
				fl.buildInds(inds,indpath);
			}
		}
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
