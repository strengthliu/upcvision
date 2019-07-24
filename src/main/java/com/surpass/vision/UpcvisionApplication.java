package com.surpass.vision;

import org.mybatis.spring.annotation.MapperScan;
//import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
//import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
//import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;

@EnableAutoConfiguration
@EnableConfigurationProperties
//@ImportResource({"classpath:applicationContext.xml"})
@ComponentScan(basePackages={"com.surpass.vision",  
		"com.surpass.vision.util",  
		"com.surpass.vision.service",  
		"com.surpass.vision.service.impl", 
		"com.surpass.vision.schedule",
		"com.surpass.vision.domain",
		"com.surpass.vision.graph"})  

@MapperScan(basePackages={"com.surpass.vision.mapper"})
@SpringBootApplication
public class UpcvisionApplication {

	public static void main(String[] args) {
		SpringApplication.run(UpcvisionApplication.class, args);
	}
//    @Override  
//    public void customize(ConfigurableEmbeddedServletContainer container) {
//        //指定项目名称
//        container.setContextPath("/demo");
//        //指定端口地址
//        container.setPort(8090);  
//    }  
}
