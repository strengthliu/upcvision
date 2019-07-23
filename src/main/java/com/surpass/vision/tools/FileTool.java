package com.surpass.vision.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import com.surpass.vision.domain.FileList;
import com.surpass.vision.graph.GraphManager;

public class FileTool {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	// 从1开始
	private static final int _depth=1;
	
	public static void find(String pathName) throws IOException{
		find(pathName,_depth);
	}
	
	private static void find(String pathName,int depth) throws IOException{
		
		//获取pathName的File对象
		File dirFile = new File(pathName);
		// 这句必须加上，解决不同操作系统文件名大小写区分问题。
		pathName = dirFile.getCanonicalPath();
		//判断该文件或目录是否存在，不存在时在控制台输出提醒
		if (!dirFile.exists()) {
			return ;
		}
		//判断如果不是一个目录，就判断是不是一个文件，时文件则输出文件路径
		if (!dirFile.isDirectory()) {
			return ;
		}
		//获取此目录下的所有文件名与目录名
		String[] fileList = dirFile.list();
		Hashtable<String,FileList> children = new Hashtable<String,FileList>();
		ArrayList<FileList> dirs = new ArrayList<FileList>();
		for (int i = 0; i < fileList.length; i++) {
			//遍历文件目录
			String string = fileList[i];
			//File("documentName","fileName")是File的另一个构造器
			File file = new File(dirFile.getPath(),string);
			String name = file.getName();
			String path = file.getCanonicalPath();
			FileList fl = new FileList();
			String fkey = path;// + File.separator + name;
			fl.setName(name);
			fl.setPath(path);
			if (file.isDirectory()) {
				fl.setFile(false);
				dirs.add(fl);
			}else fl.setFile(true);
			children.put(fkey, fl);
		} 
		GraphManager.getInstance().addChildren(pathName,children);
		for(int i=0;i<dirs.size();i++) {
			FileList fl = dirs.get(i);
			String fkey = pathName + File.separator + fl.getName();
			find(fkey,depth+1);
		}
		if(depth == 1) GraphManager.endUpdate(Thread.currentThread().getName());
	}


}
