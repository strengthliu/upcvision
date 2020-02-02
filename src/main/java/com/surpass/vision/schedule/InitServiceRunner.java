package com.surpass.vision.schedule;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.surpass.vision.appCfg.ServerConfig;
import com.surpass.vision.graph.GraphDataManager;
import com.surpass.vision.graph.GraphManager;
import com.surpass.vision.realTimeData.RealTimeDataManager;
import com.surpass.vision.server.ServerManager;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.tools.FileTool;
import com.surpass.vision.user.UserManager;

@Component
public class InitServiceRunner implements CommandLineRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileTool.class);

	@Value("${upc.graphPath}")
	private String graphPath;
	
	@Value("${gc.library}")
	private String gcLibrary;

	@Autowired
	GraphDataManager graphDataManager;
	
	@Autowired
	ServerManager serverManager;
	
	@Autowired
	RedisService rs;

//	@Autowired
//	UpdateGraphDirctory ug;

	@Autowired
	UserManager userManager;

	@Autowired
	RealTimeDataManager realTimeDataManager;

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("服务器根路径："+ServerConfig.getServerBase());

		LOGGER.info(new Date().toLocaleString() + "===========================>> started.");
		// TODO Auto-generated method stub

		// 初始化用户缓存
		Date dt1 = new Date();
		long start = System.currentTimeMillis();
		LOGGER.info(dt1.toLocaleString() + "====>> 开始初始化用户缓存...");
		userManager.init();
		// 初始化点缓存
		Date dt2 = new Date();
		long end = System.currentTimeMillis();
		LOGGER.info(dt2.toLocaleString() + "====>> 初始化用户缓存结束，用时" + (end - start) / 1000 + "秒");
		LOGGER.info(new Date().toLocaleString() + "====>> 开始初始化实时数据库服务器环境信息...");
		start = System.currentTimeMillis();
		ServerManager.init();
		// 初始化实时数据组缓存
		end = System.currentTimeMillis();
		LOGGER.info(new Date().toLocaleString() + "====>> 初始化实时数据库服务器环境信息结束，用时" + (end - start) / 1000 + "秒");
		LOGGER.info(new Date().toLocaleString() + "====>> 开始初始化实时数据组缓存...");
		start = System.currentTimeMillis();
		realTimeDataManager.getAdminRealTimeDataHashtable();

		end = System.currentTimeMillis();
		LOGGER.info(new Date().toLocaleString() + "====>> 初始化实时数据组缓存结束，用时" + (end - start) / 1000 + "秒");
		// 初始化图形缓存
		// 初始化目录
		LOGGER.info(new Date().toLocaleString() + "====>> 开始初始化图形目录数据...");
		start = System.currentTimeMillis();
		GraphManager.getInstance().reloadFileList(graphPath);
//		System.out.println("初始化图形目录树结束。");

		LOGGER.info(new Date().toLocaleString() + "====>> 开始初始化图形缓存...");
//		try {
//			GraphManager.getInstance().updateGraphs();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		ug.updateGraphDirctory();
		// 初始化服务器、装置
		end = System.currentTimeMillis();
		LOGGER.info(new Date().toLocaleString() + "====>> 初始化图形目录数据结束，用时" + (end - start) / 1000 + "秒");

		// rs.set("test", "aaaa");
		// System.out.println(rs.get("test"));

		ServerManager s = ServerManager.getInstance();
		// System.out.println(s.name);
		LOGGER.info(" ============================================");
		LOGGER.info(" ============》 服务器初始化完成 《=============");
		LOGGER.info(" ============================================");
	}

}
