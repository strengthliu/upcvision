package com.surpass.vision.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.surpass.vision.service.LoginService;
import com.surpass.vision.utils.TwoString;

@RestController
//@RequestMapping("/v1")
public class AuthorcationController {
	@Reference
	@Autowired
	LoginService login;

	
	@RequestMapping(value = "login", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody JSONObject login(@RequestParam String uname, @RequestParam String pwd,  HttpServletRequest request)
			throws Exception {
		System.out.println(uname+" "+pwd);
		TwoString ts = login.VerificationAccount(uname, pwd);
		
		if(ts != null) {
			System.out.println(ts.getName()+"  "+ts.getQuanxian()+"  "+ts.getPwd());
		}
		JSONObject ret = new JSONObject();
		ret.put("user", ts);
		ret.put("token","aadd");
		System.out.println(ts.toString());
		return ret;
	}

}
