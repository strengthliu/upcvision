package com.surpass.vision.appCfg;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.surpass.vision.server.AuthHandshakeInterceptor;

@Configuration
public class ServerConfig {

	@Value("${upc.graphPhysicalPath}")
	private String graphPhysicalPath;

	// 图形的URL目录
	@Value("${upc.graphPath}")
	private String graphPath;
	
	// 图形的缩略图目录
	@Value("${upc.graphServerPath}")
	private String graphServerPath;

	// 默认缩略图
	@Value("${upc.graphDefaultImg}")
	private String graphDefaultImg;

	
	// 图形的URL目录
	@Value("${upc.graphDirDefaultImg}")
	private String graphDirDefaultImg;

//    @Autowired
//    private AuthHandshakeInterceptor authHandshakeInterceptor;

	private static ServerConfig instance;
	{
		if(instance == null) instance = this;
		if(graphPath==null)
			graphPath = "SVG";
		if(graphPhysicalPath==null)
			graphPhysicalPath = graphPath;
		
	}
	public static ServerConfig getInstance(){
		if(instance == null) instance = new ServerConfig();
		return instance;
	}
	
	public static void main(String[] argc) {
		System.out.println(ServerConfig.getServerBase());
		System.out.println(ServerConfig.getInstance().getPhysicalGraphPath("C:\\dev\\upcvision\\svg"));
		System.out.println(ServerConfig.getInstance().getPhysicalGraphPath(ServerConfig.instance.graphServerPath));

	}
	
	/**
	 * 获取UPC应用的根目录。
	 * 如C:\\dev\\upcvision
	 * @return
	 */
	public static String getServerBase() {
		String pathName1 = null;
		try {
			pathName1 = new File(".").getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(pathName1);

		return pathName1;
	}

	/**
	 * 根据完整路径生成服务器上可访问的对应的URL
	 * 
	 * @param wholePath
	 * @return
	 */
	public String getURLFromPath(String wholePath) {
		//System.out.println("wholePath = "+wholePath);
		ServerConfig sc = ServerConfig.getInstance();
		String graphbase = sc.getPhysicalGraphPath(graphPath);
		String ret = "";
		// 图形的缩略图目录
//		@Value("${upc.graphServerPath}")
//		private String graphServerPath;

		if(wholePath.startsWith(graphbase)) { // 是
			ret = wholePath.substring(graphbase.length());
		}else if(wholePath.startsWith(graphPath)){
			ret = wholePath.substring(graphPath.length());
		}else {
			ret = wholePath;
//			throw new IllegalStateException("getURLFromPath "+wholePath+"不是图形目录。");
		}

		// 如果是根，就用.代替
		// if(path==null) path=".";
		// 开头结尾加上/
		// System.out.println(path);
		String seperator = "/";
String path = ret;
		path = path.replaceAll("\\\\", "/");
		path = path.replaceAll("\\\\\\\\", "/");
		path = path.replaceAll("//", "/");
		ret = path;
		return ret;
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	public String getPhysicalGraphPath(String path) {
		String base = ServerConfig.getServerBase();
		if(path.contentEquals("."))
			return base;
		String ret = base;
		if(path.startsWith(base)) { // path是物理路径
			return path;
		} else {
			if(ret.trim().endsWith(File.separator)) {
				ret = ret.substring(0,ret.length()-File.separator.length());
			} 
			 if(path.startsWith(File.separator)) {
				 path = path.substring(File.separator.length()-1);
			 }
			 ret = ret + File.separator + path;
		}
		return ret;
	}

//	public CharSequence getPhysicalGraphPath() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public String getDefaultGraphImg() {
		// TODO Auto-generated method stub
		return getURLFromPath(graphDefaultImg);
	}

	public String getDefaultGraphDirImg() {
		return getURLFromPath(graphDirDefaultImg);
	}

//	public

//    @Bean
//    UPCServerContext getUPCServerContext() {
//    	return new UPCServerContext();
//    }

//	public static String getURLFromPath(String physicalGraphPath, String name) {
//		// TODO Auto-generated method stub
//		String path = fl.getPath();
//		ret.setPath(path);
//		if (path != null && path.contains(graphPath)) {
//			path = path.substring(graphPath.length());
//		}
//		// 如果是根，就用.代替
//		// if(path==null) path=".";
//		// 开头结尾加上/
//		// System.out.println(path);
//		String seperator = "/";
//		if (!graphServerPath.startsWith(seperator))
//			graphServerPath = seperator + graphServerPath;
//		if (!graphServerPath.endsWith(seperator))
//			graphServerPath = graphServerPath + seperator;
//		if (path == null)
//			path = graphServerPath;
//		else
//			path = graphServerPath + path;
//		path = path.replaceAll("\\\\", "/");
//		path = path.replaceAll("\\\\\\\\", "/");
//		path = path.replaceAll("//", "/");
//		ret.setUrlPath(path + "/" + ret.getFileName());
//		return null;
//	}

}
