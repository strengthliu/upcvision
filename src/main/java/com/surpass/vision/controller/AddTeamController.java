package com.surpass.vision.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

@RestController
// @RequestMapping("/v1")
public class AddTeamController {

//	// @Reference
//	@Autowired
//	t1Mapper t1m;
	
	
	
//	@Autowired
//	AddTeamMapper addTeam;
//
	
	@RequestMapping(value = "addTeamApply", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody JSONObject addTeamApply(@RequestBody JSONObject j, HttpServletRequest request)
			throws Exception {
		JSONObject ret = new JSONObject();
		System.out.println(j.toJSONString());
		return ret;
	}
//	@Autowired
//	ContactMapper contact;
//
//	@RequestMapping(value = "addTeamApply", method = { RequestMethod.POST, RequestMethod.GET })
//	public @ResponseBody JSONObject addTeamApply(@RequestBody JSONObject j, HttpServletRequest request)
//			throws Exception {
//		JSONObject ret = new JSONObject();
//		System.out.println(j.toJSONString());
//		try {
//			AddTeam at = new AddTeam();
//			at.setName(j.getString("name"));
//			at.setSex(j.getString("sex"));
//			at.setSexname(j.getString("sexName"));
//			at.setPhone(j.getString("phone"));
//			at.setContactavailabledayname(j.getString("contactAvailableDayName"));
//			at.setContactavailableday(j.getString("contactAvailableDay"));
//			at.setContactavailabletimename(j.getString("contactAvailableTimeName"));
//			at.setContactavailabletime(j.getString("contactAvailableTime"));
//			at.setIp(this.getRemoteHost(request));
//			at.setCityname(j.getString("cityName"));
//			at.setCity(j.getString("city"));
//			at.setProvincename(j.getString("provinceName"));
//			at.setProvince(j.getString("province"));
//			at.setApplytime(this.getCurrentTime());
//			at.setEmail(j.getString("email"));
//
//			addTeam.insert(at);
//			ret.put("status", "ok");
//
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return ret;
//	}
//
//	@RequestMapping(value = "contact", method = { RequestMethod.POST, RequestMethod.GET })
//	public @ResponseBody JSONObject contact(@RequestBody JSONObject j, HttpServletRequest request)
//			throws Exception {
//		JSONObject ret = new JSONObject();
//		
//		System.out.println(j.toJSONString());
//		try {
//			Contact at = new Contact();
////			at.set小朋友姓名(j.getString("name"));
////			at.set性别代码(j.getString("sex"));
////			at.set性别(j.getString("sexName"));
////			at.set手机号(j.getString("phone"));
////			at.set出生年月日(j.getString("contactAvailableDayName"));
////			at.set申请人ip地址(this.getRemoteHost(request));
////			at.set城市(j.getString("cityName"));
////			at.set城市代码(j.getString("city"));
////			at.set省份(j.getString("provinceName"));
////			at.set省份代码(j.getString("province"));
////			at.set预约时间(this.getCurrentTime());
////
////
//			at.setName(j.getString("name"));
//			at.setSex(j.getString("sex"));
//			at.setSexname(j.getString("sexName"));
//			at.setPhone(j.getString("phone"));
//			at.setBirthday(j.getString("birthday"));
//			at.setBirthdayname(j.getString("birthdayName"));
//			at.setIp(this.getRemoteHost(request));
//			at.setCityname(j.getString("cityName"));
//			at.setCity(j.getString("city"));
//			at.setProvincename(j.getString("provinceName"));
//			at.setProvince(j.getString("province"));
//			at.setApplytime(this.getCurrentTime());
//			contact.insert(at);
//			ret.put("status", "ok");
//
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return ret;
//	}

	// public static void main(String []argc) {
	// AddTeamController ac = new AddTeamController();
	// ac.getCurrentTime();
	// }
	public String getCurrentTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd HH点mm分ss秒");
		String d = format.format(System.currentTimeMillis());
		System.out.println(d);
		return d;
	}

	public String getRemoteHost(javax.servlet.http.HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}
}
