package com.surpass.vision.schedule;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.surpass.vision.graph.GraphDataManager;
import com.surpass.vision.tools.FileTool;

@Configuration
@Component // 此注解必加
//@EnableScheduling // 此注解必加
public class UpdateGraphDirctory {
	private static final Logger LOGGER =  LoggerFactory.getLogger(UpdateGraphDirctory.class);
	
	@Value("${upc.graphPath}")
	private String graphPath;
	
	public void sayHello(){
		LOGGER.info("Hello world, i'm the king of the world!!!");
	}
	
	@Autowired
	GraphDataManager graphDataManager;
	
//	@Scheduled(cron="*/1 * * * * ?")
	public void updateGraphDirctory() {
		LOGGER.info("在定时计划中，开始更新图形目录数据..  UpdateGraphDirctory in "+graphPath);
		try {
			 // FileTool.find(graphPath);
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
