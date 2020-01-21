package com.surpass.vision.server;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyPrincipal implements Principal {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
    private String loginName;

    public MyPrincipal(String loginName) {
//	    	System.out.println("MyPrincipal("+loginName+")");
    	logger.info("构造MyPrincipal，使用名称："+loginName);
        this.loginName = loginName;
        
    }


	@Override
	public String getName() {
		// TODO Auto-generated method stub
//    	System.out.println("MyPrincipal.getName");
		logger.info("getName");
		return loginName;
	}

}
