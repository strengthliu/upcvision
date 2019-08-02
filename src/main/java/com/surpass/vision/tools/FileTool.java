package com.surpass.vision.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.domain.FileList;
import com.surpass.vision.graph.GraphManager;
import com.surpass.vision.schedule.UpdateGraphDirctory;
import com.surpass.vision.server.ServerManager;
import com.surpass.vision.service.RedisService;

public class FileTool {
	private static final Logger LOGGER =  LoggerFactory.getLogger(UpdateGraphDirctory.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	static ServerManager sm = ServerManager.getInstance();

	RedisService rs;
	
	public RedisService getRs() {
		return rs;
	}

	public void setRs(RedisService rs) {
		this.rs = rs;
	}

	public void setSm(ServerManager sm) {
		this.sm = sm;
	}

	// 从1开始
	private static final int _depth = 1;

	public static void find(String pathName) throws IOException {
		find(pathName, _depth);
	}

	public static String readHtml(String fileName) {
		//LOGGER.info("检测文件："+fileName);
		//System.out.println("fileName="+fileName);
		//String pathname = "D:\\twitter\\13_9_6\\dataset\\en\\input.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
		File filename = new File(fileName); // 要读取以上路径的input。txt文件
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		boolean isCompatible = false;
		if(suffix.contentEquals("txt") || suffix.contentEquals("html") 
				|| suffix.contentEquals("htm") ||suffix.contentEquals("svg") 
				|| suffix.contentEquals("text"))
			isCompatible = true;
		// 大于1m或不是指定文件格式，就退出。
		if((filename.length()/1024/1024)>1 || !isCompatible ) return "";
		//System.out.println(fileName);
		InputStreamReader reader = null;
		String ret = "";
		BufferedReader br = null;
		try {
			reader = new InputStreamReader(
					new FileInputStream(filename));
			br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			String line = "";
			line = br.readLine();
			while (line != null) {
				line = br.readLine(); // 一次读入一行数据
				ret = ret + line;
			}
			//br.close();
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} // 建立一个输入流对象reader
		catch(IOException e3) {
			e3.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
//		// 按字节流读文件
//		FileInputStream fis = null;
//		StringBuffer sb = new StringBuffer();
//		try {
//			fis = new FileInputStream(fileName);
//			byte[] bytes = new byte[1024];
//			while (-1 != fis.read(bytes)) {
//				sb.append(new String(bytes));
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				fis.close();
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//		}
//		System.gc();
//		return sb.toString();
	}

	private static void find(String pathName, int depth) throws IOException {

		// 获取pathName的File对象
		File dirFile = new File(pathName);
		// 这句必须加上，解决不同操作系统文件名大小写区分问题。
		pathName = dirFile.getCanonicalPath();
		// 判断该文件或目录是否存在，不存在时在控制台输出提醒
		if (!dirFile.exists()) {
			return;
		}
		// 判断如果不是一个目录，就判断是不是一个文件，时文件则输出文件路径
		if (!dirFile.isDirectory()) {
			return;
		}
		LOGGER.info("开始刷新目录："+pathName);
		// 获取此目录下的所有文件名与目录名
		String[] fileList = dirFile.list();
		Hashtable<String, FileList> children = new Hashtable<String, FileList>();
		ArrayList<FileList> dirs = new ArrayList<FileList>();
		for (int i = 0; i < fileList.length; i++) {
			// 遍历文件目录
			String string = fileList[i];
			// File("documentName","fileName")是File的另一个构造器
			File file = new File(dirFile.getPath(), string);
			String name = file.getName();
			String path = file.getCanonicalPath();
			FileList fl = new FileList();
			String fkey = path;// + File.separator + name;
			fl.setName(name);
			if (file.isDirectory()) {
				fl.setPath(path);
				fl.setFile(false);
				dirs.add(fl);
			} else {
				path = path.substring(0, path.length()-name.length()-1);
				fl.setPath(path);
				fl.setFile(true);
				// 解析文件
				//System.out.println("1 readHtml="+fl.getName());

				Document doc = Jsoup.parse(readHtml(fl.getWholePath()));
				// 判断文件是否是图形文件
				ArrayList<String> pointIDs = new ArrayList<String>();
				if (doc.select(GlobalConsts.GraphElement) != null) {
					Elements docs = doc.select(GlobalConsts.PointTag);
					for (int idocs = 0; idocs < docs.size(); idocs++) {
						Element e = docs.get(idocs);
						String tag = e.attr(GlobalConsts.PointID);
						if (!StringUtil.isBlank(tag)) {
							if(sm.getPointByID(tag)!=null) {
								pointIDs.add(tag);
								// LOGGER.info("检查点位："+tag+" => "+sm.getPointByID(tag));
							}
						}
					}
					fl.setSVG(true);
					fl.setPointIDs(pointIDs);
				} else { // 不是svg文件
					fl.setSVG(false);
				}
			}
			children.put(fkey, fl);
		}
		GraphManager.getInstance().addChildren(pathName, children);
		for (int i = 0; i < dirs.size(); i++) {
			FileList fl = dirs.get(i);
			String fkey = pathName + File.separator + fl.getName();
			find(fkey, depth + 1);
		}
		if (depth == 1)
			GraphManager.endUpdate(Thread.currentThread().getName());
	}

}
