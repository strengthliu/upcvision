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
	
	public User getUserByID(String userId) {
		UserInfo ui = getUserInfoByID(userId);
		User u = new User();
		u.setId(ui.getId());
		u.setName(ui.getName());
		return u;
	}
	
	public UserInfo getUserInfoByID(String userId) {
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
		List<UserInfo> users = userService.getAllUsers();
		for(int i=0;i<users.size();i++)
			redisService.set(GlobalConsts.Key_UserInfo_Pre+users.get(i).getId().toString(), users.get(i));
	}

	public boolean hasRight(Integer role, String operation) {
		// TODO 现在权限是按简单角色定义的，未来扩展时，从这里修改
		switch(operation) {
		case GlobalConsts.Operation_createOrUpdateRealTimeData:
			if(role <3) return true;
			return false;
		case GlobalConsts.Operation_getRealTimeDataList:
			return true;
		}
		return false;
	}

}
