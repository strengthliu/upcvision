package com.surpass.vision.service;

import java.util.List;

import com.surpass.vision.domain.UserInfo;
import com.surpass.vision.domain.UserSpace;

public interface UserService {
	
	
	// 新建用户
	public boolean newUser(UserInfo user);
	
	// 删除用户
	public boolean delUser(Integer userID);
	// 授权
	//UserSpace buildUserSpace(Integer userID, String... token);

	public UserInfo getUserById(Double userID);

	public List<UserInfo> getAllUsers();

}
