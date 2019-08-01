package com.surpass.vision.service;

public interface AuthorcationService {

	// 校验token和用户ID的正确性
	public boolean tokenVerification(Integer id,String token);
	
	
	// 
}
