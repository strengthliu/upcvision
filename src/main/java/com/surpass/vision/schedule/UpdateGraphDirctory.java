package com.surpass.vision.schedule;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.surpass.vision.tools.FileTool;

@Configuration
@Component // 此注解必加
@EnableScheduling // 此注解必加
public class UpdateGraphDirctory {
	private static final Logger LOGGER =  LoggerFactory.getLogger(UpdateGraphDirctory.class);
	
	@Value("${upc.graphPath}")
	private String graphPath;
	
	@Value("${upc.graphPathDepth}")
	private int depth;
	
	public void sayHello(){
		LOGGER.info("Hello world, i'm the king of the world!!!");
	}
	public void updateGraphDirctory() {
		LOGGER.info("UpdateGraphDirctory in "+graphPath);
		try {
			FileTool.find(graphPath, depth);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
