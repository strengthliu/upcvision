package com.surpass.vision.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.domain.User;
import com.surpass.vision.domain.UserInfo;
import com.surpass.vision.mapper.UserInfoMapper;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.service.UserService;

@Component
public class UserManager {

	@Reference
	@Autowired
	RedisService redisService;

	@Autowired
	UserService userService;
	
	public UserInfo getUserByID(String userId) {
		UserInfo ui = (UserInfo)redisService.get(GlobalConsts.Key_UserInfo_Pre+userId);
		if(ui!=null)
			return ui;
		else {
			// 到数据库里取
			ui = userService.getUserById(Integer.parseInt(userId));
			if(ui != null) {
				redisService.set(GlobalConsts.Key_UserInfo_Pre+userId, ui);
				return ui;
			}
		}
		return null;
	}
	
	public void setUserInfo(UserInfo user) {
		redisService.set(GlobalConsts.Key_UserInfo_Pre+user.getId().toString(),user);
	}

	// 初始化用户信息到缓存
	public void init() {
		// TODO Auto-generated method stub
		List<UserInfo> users = userService.getAllUsers();
		for(int i=0;i<users.size();i++)
			redisService.set(GlobalConsts.Key_UserInfo_Pre+users.get(i).getId().toString(), users.get(i));
	}

}
