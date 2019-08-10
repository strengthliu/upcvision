package com.surpass.vision.controller;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.JSONObject;
import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.authorcation.AuthorcationManager;
import com.surpass.vision.common.ToWeb;
import com.surpass.vision.service.AuthorcationService;
import com.surpass.vision.service.GraphService;
import com.surpass.vision.service.RealTimeDataService;
import com.surpass.vision.tools.TokenTools;
import com.surpass.vision.user.UserManager;
import com.surpass.vision.userSpace.UserSpaceManager;

import com.surpass.vision.domain.*;
import com.surpass.vision.realTimeData.RealTimeDataManager;

@RestController
//@RequestMapping("/v1")
public class RealTimeDataController extends BaseController {

	@Autowired
	RealTimeDataManager realTimeDataManager;

	@Autowired
	UserManager userManager;

//		@Reference
//		@Autowired
//		AuthorcationManager authorcationManager;

	@Autowired
	UserSpaceManager userSpaceManager;

	/**
	 * 获取指定用户的实时数据列表
	 * 
	 * @param user
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getRealTimeDataList", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb getRealTimeDataList(@RequestBody JSONObject user, HttpServletRequest request) throws Exception {
		Integer uid = user.getInteger("uid");
		String token = user.getString("token");
		// 认证+权限
		ToWeb ret = authercation(uid, token, GlobalConsts.Operation_getRealTimeDataList);
		if (!StringUtil.isBlank(ret.getStatus()))
			return ret;

		// 取出用户空间
		UserSpace us = userSpaceManager.getUserSpace(uid);
		if (us == null) {
			// token = TokenTools.genToken(uid.toString());
			try {
				us = userSpaceManager.buildUserSpace(uid, token);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
		}

		// 先到用户空间里找，没有就建用户空间，
		if (us != null) {
			Hashtable<String, RealTimeData> data = us.getRealTimeData();
			ret.setMsg("成功");
			ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
//				ret.setData();
//				ret.setData("data", data);
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("realTimeData", hm);
//				hm.put("userInfo", us.getUser());
			ret.setData(hm);
			return ret;
		} else {
			ret.setStatus(GlobalConsts.ResultCode_FAIL);
			ret.setMsg("用户信息非法，请重新登录。");
			ret.setRedirectUrl("login.html");
			return ret;
		}

	}

	/**
	 * 获取实时数据
	 * 
	 * @param user
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getRealTimeData", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody JSONObject getRealTimeData(@RequestBody JSONObject user, HttpServletRequest request)
			throws Exception {
		// TODO
		return null;
	}

	/**
	 * 新建一个实时数据列表
	 * 
	 * @param user
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "newRealTimeData", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb newRealTimeData(@RequestBody JSONObject user, HttpServletRequest request) throws Exception {
		Integer uid = user.getInteger("uid");
		String token = user.getString("token");
		// 认证+权限
		ToWeb ret = authercation(uid, token, GlobalConsts.Operation_createOrUpdateRealTimeData);
		if (!StringUtil.isBlank(ret.getStatus()))
			return ret;

		// 取出参数
		String type = user.getString("type");
		String name = user.getString("name");
		String owner = user.getString("owner");
		String creater = user.getString("owner");
		String shared = user.getString("shared");
		String points = user.getString("points");
		String otherrule1 = user.getString("otherrule1");
		String otherrule2 = user.getString("otherrule2");
		// TODO: 检查参数合法性

		try {
			RealTimeData rtd = realTimeDataManager.createOrUpdateRealTimeData(type, name, owner, creater, shared,
					points, otherrule1, otherrule2);
			if (rtd != null) {
				// 更新自己的用户空间

				ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
				ret.setMsg("成功");
				ret.setRefresh(true);
				return ret;
			} else
				throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			ret.setStatus(GlobalConsts.ResultCode_AuthericationError);
			ret.setMsg("异常失败");
			return ret;
		}
	}
//
//    @MessageMapping("/hello/{index}")
//    @SendTo("/topic/greetings/{index}")
//    public Greeting greeting(HelloMessage message) throws Exception {
//        Thread.sleep(1000); // simulated delay
//        return new Greeting(HtmlUtils.htmlEscape(message.getName()) + ": "+HtmlUtils.htmlEscape(message.getContent()));
//    }

}
