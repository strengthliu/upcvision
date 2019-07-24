package com.surpass.vision.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.surpass.vision.server.ServerManager;
import com.surpass.vision.service.RedisService;
@Component
public class InitServiceRunner implements CommandLineRunner {

	@Autowired
	RedisService rs;
	
	@Autowired
	UpdateGraphDirctory ug;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("===========================>> started.");
		// TODO Auto-generated method stub
		// 初始化目录
		ug.updateGraphDirctory();
		// 初始化服务器、装置
		
		rs.set("test", "aaaa");
		System.out.println(rs.get("test"));
		
		ServerManager s = ServerManager.getInstance();
		
	}

}
