package com.surpass.vision.server;

import java.security.Principal;

public class MyPrincipal implements Principal {

	    private String loginName;

	    public MyPrincipal(String loginName) {
	    	System.out.println("MyPrincipal("+loginName+")");
	        this.loginName = loginName;
	    }


	@Override
	public String getName() {
		// TODO Auto-generated method stub
    	System.out.println("MyPrincipal.getName");
		return loginName;
	}

}
