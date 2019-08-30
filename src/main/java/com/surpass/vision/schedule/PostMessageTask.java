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

import com.surpass.vision.server.SocketServer;
import com.surpass.vision.tools.FileTool;

@Configuration
@Component // 此注解必加
@EnableScheduling // 此注解必加
public class PostMessageTask {
	private static final Logger LOGGER =  LoggerFactory.getLogger(PostMessageTask.class);
	
	@Autowired
	SocketServer socketServer;
	public void sayHello(){
		LOGGER.info("Hello world, i'm the king of the world!!!");
	}
	@Scheduled(cron="*/1 * * * * ?")
	public void postMessage() {
		//LOGGER.info("Websocket遍历主题群发消息。");
		try {
			socketServer.sendAllMessage();
			 // FileTool.find(graphPath);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
