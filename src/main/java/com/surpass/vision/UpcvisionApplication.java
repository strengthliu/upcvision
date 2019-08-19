package com.surpass.vision;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.mybatis.spring.annotation.MapperScan;
//import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
//import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
//import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAutoConfiguration
@EnableConfigurationProperties
//@ImportResource({"classpath:applicationContext.xml"})
@ComponentScan(basePackages={"com.surpass.vision",  
		"com.surpass.vision.util",  
		"com.surpass.vision.service",  
		"com.surpass.vision.service.impl", 
		"com.surpass.vision.schedule",
		"com.surpass.vision.server",
		"com.surpass.vision.user",
		"com.surpass.vision.userSpace",
		"com.surpass.vision.graph",
		"com.surpass.vision.pointGroup",
		"com.surpass.vision.realTimeData",
		"com.surpass.vision.historyData",
		"com.surpass.vision.lineAlertData",
		"com.surpass.vision.alertData",
		"com.surpass.vision.domain",
		"com.surpass.vision.exception"})  

@MapperScan(basePackages={"com.surpass.vision.mapper"})
@EnableAsync
@SpringBootApplication
public class UpcvisionApplication {

	public static void main(String[] args) {
		SpringApplication.run(UpcvisionApplication.class, args);
	}
	
    @EnableAsync
    @Configuration
    class TaskPoolConfig {

        @Bean("taskExecutor")
        public Executor taskExecutor() {
            ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
            executor.setCorePoolSize(10);
            executor.setMaxPoolSize(20);
            executor.setQueueCapacity(200);
            executor.setKeepAliveSeconds(60);
            executor.setThreadNamePrefix("taskExecutor-");
            executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
            return executor;
        }
    }
//    @Override  
//    public void customize(ConfigurableEmbeddedServletContainer container) {
//        //指定项目名称
//        container.setContextPath("/demo");
//        //指定端口地址
//        container.setPort(8090);  
//    }  
}


