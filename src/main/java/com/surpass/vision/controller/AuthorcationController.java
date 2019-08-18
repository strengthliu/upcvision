package com.surpass.vision.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.common.Submit;
import com.surpass.vision.common.ToWeb;
import com.surpass.vision.domain.UserInfo;
import com.surpass.vision.domain.UserSpace;
//import com.surpass.vision.domain.UserInfo;
import com.surpass.vision.service.LoginService;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.tools.TokenTools;
import com.surpass.vision.userSpace.UserSpaceManager;
import com.surpass.vision.utils.TwoString;

@RestController
//@RequestMapping("/v1")
public class AuthorcationController extends BaseController {
	@Reference
	@Autowired
	LoginService login;
	
	@Autowired
	UserSpaceManager userSpaceManager;
	
    @Autowired
    private RedisService redisService;
	
    @Submit
    @RequestMapping(value = "login", method = { RequestMethod.POST, RequestMethod.GET })
	public  ToWeb login(@RequestBody JSONObject user,  HttpServletRequest request)
			throws Exception {
		ToWeb tw = ToWeb.buildResult();
		String uname = user.getString("uname");
		String pwd = user.getString("pwd");
		System.out.println(uname+" "+pwd);
		UserInfo ui = login.VerificationAccount(uname, pwd); 
		// TODO 没有这个用户
		if(ui==null) {
			tw.setStatus(GlobalConsts.ResultCode_FAIL);
			tw.setMsg("用户名密码不正确");
			tw.setRedirectUrl("login.html");
			return tw;
		}
		// 返回UserSpace
		UserSpace us = userSpaceManager.getUserSpace(ui.getId());
		String token = TokenTools.genToken(ui.getId().toString());
		if(us == null) {
			try {
			us = userSpaceManager.buildUserSpace(ui.getId(), token);
			}catch(IllegalStateException e) {
				e.printStackTrace();
			}
		} else {
			// 重新建token
			us.setToken(token);
		}
		userSpaceManager.setUserSpace(ui.getId(), us);
		tw.setStatus(GlobalConsts.ResultCode_SUCCESS);
		tw.setMsg("登录成功！");
		HashMap<String ,Object> hm = new HashMap<String ,Object>();
		hm.put("userSpace",us);
		//hm.put("userInfo", us.getUser());
		tw.setData(hm);
		return tw;
	}

	
	@RequestMapping(value = "checkAuthorcation", method = { RequestMethod.POST, RequestMethod.GET })
	public  ToWeb checkAuthorcation(@RequestParam String uid, @RequestParam String token,  HttpServletRequest request)
			throws Exception {

		Long userId = null;
		try {
			userId = Long.parseLong(uid);
			if(userId == null || userId == 0) userId = Long.valueOf(0);
		}catch(Exception e) {
			userId = Long.valueOf(0);
		}
		return authercation(userId,token);
	}

	@RequestMapping(value = "getUserSpace", method = { RequestMethod.POST, RequestMethod.GET })
	public  ToWeb getUserSpace(@RequestParam String uid, @RequestParam String token,  HttpServletRequest request)
			throws Exception {

		Long userId = null;
		try {
			userId = Long.parseLong(uid);
			if(userId == null || userId == 0) userId = Long.valueOf(0);
		}catch(Exception e) {
			userId = Long.valueOf(0);
		}
		ToWeb ret = authercation(userId,token);
		if(ret.getStatus()!= GlobalConsts.ResultCode_SUCCESS) return ret;
		UserSpace us = userSpaceManager.getUserSpaceRigidly(userId);
		ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
		ret.setData("userSpace", us);
		return ret;
	}

    //http://localhost:8888/saveCity?cityName=北京&cityIntroduce=中国首都&cityId=1
    @GetMapping(value = "saveCity")
    public String saveCity(int cityId,String cityName,String cityIntroduce){
    	UserInfo city = null ;//= new City(cityId,cityName,cityIntroduce);
        redisService.set(cityId+"",city);
        return "success";
    }



    //http://localhost:8888/getCityById?cityId=1
    @GetMapping(value = "getCityById")
    public UserInfo getCity(int cityId){
    	UserInfo city = (UserInfo) redisService.get(cityId+"");
        return city;
    }

}
