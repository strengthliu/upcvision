package com.surpass.realkits.exception;

public class GecException extends Exception{
	public GecException(){
		super();
	}
	
	public GecException(String message){
		super(message);
	}
	
	public GecException(Exception e){
		super(e);
	}
	
	public GecException(long errorCode,String message){
		super("errorCode:" + errorCode + "message:" + message);
	}
}
