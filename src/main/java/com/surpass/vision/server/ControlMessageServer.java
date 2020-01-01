package com.surpass.vision.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.service.PointGroupService;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.user.UserManager;

@Component
public class ControlMessageServer {
	@Reference
	@Autowired
	RedisService redisService;

	@Autowired
	UserManager userManager;
//	@Autowired
//	ServerManager serverManager;

	@Autowired
	PointGroupService pointGroupService;

	public ControlMessage getMessage(String uid) {
		ControlMessage cm = (ControlMessage) redisService.get(GlobalConsts.Key_ControlMessage_pre+uid);
		redisService.delete(GlobalConsts.Key_ControlMessage_pre+uid);
		return cm;
	}
	
	public boolean setMessage(String uid,ControlMessage msg) {
		try {
			redisService.set(GlobalConsts.Key_ControlMessage_pre+uid,msg);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
}
