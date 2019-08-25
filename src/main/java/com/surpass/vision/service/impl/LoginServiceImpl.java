package com.surpass.vision.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.surpass.vision.domain.UserInfo;
import com.surpass.vision.mapper.UserInfoMapper;
import com.surpass.vision.service.LoginService;
import com.surpass.vision.userSpace.UserSpaceManager;

@Service
public class LoginServiceImpl implements LoginService {

	
	@Autowired
	UserInfoMapper userInfoMapper;
	
	
	@Override
	public UserInfo VerificationAccount(String name, String password) {
		
		List<UserInfo> uil =userInfoMapper.selectByName(name);
		if(uil == null) {
			throw new IllegalStateException("没有这个用户");
		}
		for(int i=0;i<uil.size();i++) {
			UserInfo ui = uil.get(i);
			if(password.contentEquals(ui.getPwd())) return ui;
		}
		throw new IllegalStateException("用户名密码不正确");
	}


	
}
