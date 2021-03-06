package com.surpass.vision.controller;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.common.ToWeb;
import com.surpass.vision.domain.RealTimeData;
import com.surpass.vision.domain.UserRight;
import com.surpass.vision.domain.UserSpace;
import com.surpass.vision.server.ControlMessage;
import com.surpass.vision.server.ControlMessageServer;
import com.surpass.vision.tools.IDTools;
import com.surpass.vision.user.UserManager;
import com.surpass.vision.userSpace.UserSpaceManager;

@Component
public class BaseController {
	@Autowired
	UserManager userManager;
	
//	@Reference
//	@Autowired
//	AuthorcationManager authorcationManager;

	@Autowired
	UserSpaceManager userSpaceManager;
	
	@Autowired
	ControlMessageServer controlMessageServer;
	
	public ToWeb authercation(JSONObject user,HttpServletRequest request) {
		Double uid = user.getDouble("uid");
		String token = user.getString("token");
		return authercation(uid,token);
	}
	
	public ToWeb authercation(Double uid, String token) {
		ToWeb ret = ToWeb.buildResult();
		try {
			if(!userSpaceManager.tokenVerification(uid, token)) {
	//			throw new NotAuthorizedException("You Don't Have Permission");
				ret.setStatus(GlobalConsts.ResultCode_AuthericationError);
				ret.setMsg("用户身份验证失败。请重新登录");
				ret.setRedirectUrl("login.html");
				ret.setRefresh(true);
				return ret;
			}else {
				ControlMessage cm = controlMessageServer.getMessage(IDTools.toString(uid));
				if(cm!=null) {
					if(cm.reloadUserSpace())
						ret.setReloadUserSpace(true);
				}
			}
		}catch(Exception e) {
			ret.setStatus(GlobalConsts.ResultCode_AuthericationError);
			ret.setMsg(e.getMessage());
			ret.setRedirectUrl("login.html");
			return ret;
		}
		
		return ret;
	}

	public ToWeb authercation(Double uid, String token, String opperation, UserRight ur) {
		ToWeb ret = authercation(uid,token);
		if(!StringUtil.isBlank(ret.getStatus())) return ret;
		
		// 判断权限
		UserSpace us = userSpaceManager.getUserSpaceRigidly(uid);
		Integer role = us.getUser().getRole();
		
		
		if(!userManager.hasRight(role,opperation,ur)) {
			// 没有权限
			ret.setStatus(GlobalConsts.ResultCode_NO_PRIVILEGE);
			ret.setMsg("您没有此操作权限，如果一定要执行这个操作，请联系组态人员或系统管理员。");
			return ret;
		}
		return ret;
	}
	

}
