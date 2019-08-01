package com.surpass.vision.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.surpass.vision.realTimeData.RealTimeDataManager;
import com.surpass.vision.server.ServerManager;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.user.UserManager;
@Component
public class InitServiceRunner implements CommandLineRunner {

	@Autowired
	RedisService rs;
	
	@Autowired
	UpdateGraphDirctory ug;
	
	@Autowired
	UserManager userManager;
	
	@Autowired
	RealTimeDataManager realTimeDataManager;
	@Override
	public void run(String... args) throws Exception {
		System.out.println("===========================>> started.");
		// TODO Auto-generated method stub
		
		// 初始化用户缓存
		userManager.init();
		// 初始化点缓存
		ServerManager.init();		
		// 初始化实时数据组缓存
		realTimeDataManager.getAdminRealTimeDataHashtable();
		// 初始化图形缓存
		
		
		
		// 初始化目录
		ug.updateGraphDirctory();
		// 初始化服务器、装置
		
		rs.set("test", "aaaa");
		System.out.println(rs.get("test"));
		
		ServerManager s = ServerManager.getInstance();
		
	}

}
