package com.surpass.vision.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.common.ToWeb;
import com.surpass.vision.domain.AlertData;
import com.surpass.vision.domain.Graph;
import com.surpass.vision.domain.HistoryData;
import com.surpass.vision.domain.PointAlertData;
import com.surpass.vision.domain.RealTimeData;
import com.surpass.vision.domain.UserRight;
import com.surpass.vision.domain.UserSpace;
import com.surpass.vision.domain.AlertData;
import com.surpass.vision.alertData.AlertDataManager;
import com.surpass.vision.service.AuthorcationService;
import com.surpass.vision.service.GraphService;
import com.surpass.vision.tools.TimeTools;
import com.surpass.vision.user.UserManager;
import com.surpass.vision.userSpace.UserSpaceManager;

@RestController
//@RequestMapping("/v1")
public class AlertDataController extends BaseController {

	@Autowired
	AlertDataManager alertDataManager;

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
	@RequestMapping(value = "getAlertDataList", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb getAlertDataList(@RequestBody JSONObject user, HttpServletRequest request) throws Exception {
		Double uid = user.getDouble("uid");
		String token = user.getString("token");
		// 认证+权限
		UserRight ur = new UserRight();
		ToWeb ret = authercation(uid, token, GlobalConsts.Operation_getAlertDataList,ur);
		if (!StringUtil.isBlank(ret.getStatus()))
			return ret;

		// 取出用户空间
		UserSpace us = userSpaceManager.getUserSpaceWithDepartData(Double.valueOf(uid));
		if (us == null) {
			// token = TokenTools.genToken(uid.toString());
			try {
				us = userSpaceManager.buildUserSpace(Double.valueOf(uid), token);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
		}

		// 先到用户空间里找，没有就建用户空间，
		if (us != null) {
			Hashtable<String, AlertData> data = us.getAlertData();
			ret.setMsg("成功");
			ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
//				ret.setData();
//				ret.setData("data", data);
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("AlertData", hm);
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
	 * 新建一个实时数据列表
	 * 
	 * @param user
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "newAlertDataGroup", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb newAlertDataGroup(@RequestBody JSONObject user, HttpServletRequest request) throws Exception {
		Double uid = user.getDouble("uid");
		String token = user.getString("token");

		// 取出参数
		// {'uid':uid,'token':token,'points':selectedPoints,'name':targetName}
		String owner = user.getString("uid");
		String name = user.getString("name");
		String creater = owner;
		JSONArray points = user.getJSONArray("points");
		String otherrule2 = user.getString("desc");
		String otherrule1 = user.getString("rule");
		String otherrule5 = user.getString("thumbnail");
		ToWeb ret;
		
		// TODO: 检查参数合法性

		String idstr = user.getString("id");
		Double id = null ;
		if(StringUtil.isBlank(idstr)) {
			// 认证+权限
			UserRight ur = new UserRight();
			ret = authercation(uid, token, GlobalConsts.Operation_createAlertData,ur);
			if (!StringUtil.isBlank(ret.getStatus()) && (!ret.getStatus().contentEquals(GlobalConsts.ResultCode_SUCCESS)))
				return ret;
			
		}else {
			id = Double.valueOf(idstr);
		// 认证+权限
			AlertData g = this.alertDataManager.getAlertDataByKeys(id);
		UserRight ur = g.getRight(uid);
		ret = authercation(uid, token, GlobalConsts.Operation_updateAlertData,ur);
		if (!StringUtil.isBlank(ret.getStatus()) && (!ret.getStatus().contentEquals(GlobalConsts.ResultCode_SUCCESS)))
			return ret;
		}

		
		// 取出参数
		// {'uid':uid,'token':token,'points':selectedPoints,'name':targetName}

		
		// TODO: 检查参数合法性

		try {
			AlertData rtd = alertDataManager.createAlertData(GlobalConsts.Type_alertdata_, name, owner, creater,points,otherrule2,otherrule1,idstr);
			if (rtd != null) {
				// 更新用户空间
				UserSpace us = userSpaceManager.getUserSpaceRigidly(Double.valueOf(uid));
				userSpaceManager.updateAlertData(rtd,Double.valueOf(0));
				ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
				ret.setMsg("成功");
				ret.setData("data",rtd);
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
	
	@RequestMapping(value = "deleteAlertDataGroup", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb deleteAlertDataGroup(@RequestBody JSONObject user, HttpServletRequest request) throws Exception {
		Double uid = user.getDouble("uid");
		String token = user.getString("token");
		String idstr = user.getString("id");
		Double idd = null ;
		if(StringUtil.isBlank(idstr)) {
			
		}else {
			idd = Double.valueOf(idstr);
		}
		
		// 认证+权限
		AlertData g = this.alertDataManager.getAlertDataByKeys(idd);
		UserRight ur = g.getRight(uid);
		ToWeb ret = authercation(uid, token, GlobalConsts.Operation_updateAlertData,ur);
		if (!StringUtil.isBlank(ret.getStatus()) && (!ret.getStatus().contentEquals(GlobalConsts.ResultCode_SUCCESS)))
			return ret;

		// 取出参数
		// {'uid':uid,'token':token,'points':selectedPoints,'name':targetName}
		String id = user.getString("id");		
		// 检查参数合法性
		if(StringUtil.isBlank(id)) {
			ret.setStatus(GlobalConsts.ResultCode_FAIL);
			ret.setMsg("参数不正确，ID不能为空。");
			return ret;
		}
		Double rtdId = Double.valueOf(id);
		try {
			AlertData rtd = alertDataManager.getAlertDataByKeys(rtdId);
			if(rtd == null || StringUtil.isBlank(rtd.getOwner())) {
				ret.setStatus(GlobalConsts.ResultCode_INVALIDATION);
				ret.setMsg("没有指定ID的数据。");
				ret.setRefresh(true);
				return ret;
			}
			rtd = alertDataManager.deleteAlertData(id);
			if (rtd != null) {
				// 更新用户空间
				//UserSpace us = userSpaceManager.getUserSpaceRigidly(Long.valueOf(uid));
				userSpaceManager.updateAlertData(null,rtd);
				ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
				ret.setMsg("成功");
				ret.setData("data",id);
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
	
	@RequestMapping(value = "shareRightAlertData", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb shareRight(@RequestBody JSONObject user, HttpServletRequest request) throws Exception {
		Double uid = user.getDouble("uid");
		String token = user.getString("token");
		String idstr = user.getString("id");
		Double id = null;
		if (StringUtil.isBlank(idstr)) {

		} else {
			id = Double.valueOf(idstr);
		}

		// 认证+权限
		AlertData g = alertDataManager.getAlertDataByKeys(id);
		UserRight ur = g.getRight(uid);

		ToWeb ret = authercation(uid, token, GlobalConsts.Operation_updateAlertData, ur);
		if (!StringUtil.isBlank(ret.getStatus()) && (!ret.getStatus().contentEquals(GlobalConsts.ResultCode_SUCCESS)))
			return ret;

		// 取出参数
		// var
		// data={'uid':uid,'token':token,'userIds':Array.from(selectedUsers),'type':"Graph"};
		// {'uid':uid,'token':token,'points':selectedPoints,'name':targetName}
		JSONArray juserIds = user.getJSONArray("userIds");
		JSONArray jdepartIds = user.getJSONArray("departIds");
		AlertData rtd = null;
		List<String> userIds = null;
		List<String> departIds = null;
		if (juserIds != null && juserIds.size() > 0) {
			userIds = JSONObject.parseArray(juserIds.toJSONString(), String.class);
		}
		if (jdepartIds != null && jdepartIds.size() > 0) {
			departIds = JSONObject.parseArray(jdepartIds.toJSONString(), String.class);
		}
		try {
			rtd = alertDataManager.updateShareRight(id, userIds, departIds);
			if (rtd != null) {
				// 更新用户空间
				UserSpace us = userSpaceManager.getUserSpaceRigidly(Double.valueOf(uid));
//				userSpaceManager.updateAlertData(rtd, Double.valueOf(0));
				userSpaceManager.updateAlertData(g,rtd);
				ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
				ret.setData("data", rtd);
				ret.setMsg("成功");
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
	
	/**
	 * 获取实时数据
	 * 
	 * @param user
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getAlertData", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb getAlertData(@RequestBody JSONObject user, HttpServletRequest request) throws Exception {
		Double uid = user.getDouble("uid");
		String token = user.getString("token");
		String idstr = user.getString("id");
		Double idd = null;
		if (StringUtil.isBlank(idstr)) {

		} else {
			idd = Double.valueOf(idstr);
		}

		// 认证+权限
		AlertData g = this.alertDataManager.getAlertDataByKeys(idd);
		UserRight ur = g.getRight(uid);
		ToWeb ret = authercation(uid, token, GlobalConsts.Key_AlertData_pre_, ur);
		if (!StringUtil.isBlank(ret.getStatus()) && (!ret.getStatus().contentEquals(GlobalConsts.ResultCode_SUCCESS)))
			return ret;

		// 取出参数
		// {'uid':uid,'token':token,'points':selectedPoints,'name':targetName}
		String id = user.getString("id");
		// 检查参数合法性
		if (StringUtil.isBlank(id)) {
			ret.setStatus(GlobalConsts.ResultCode_FAIL);
			ret.setMsg("参数不正确，ID不能为空。");
			return ret;
		}
		Double rtdId = Double.valueOf(id);
		
		// 取时间参数
		String beginTimeStr = user.getString("beginTime");
		String endTimeStr = user.getString("endTime");
		Date beginTime = null;
		Long _beginTime = null;
		Date endTime = null;
		Long _endTime = null;
		try {
			if(StringUtil.isBlank(beginTimeStr))
				beginTime=null;
			else {
				beginTime = new Date(beginTimeStr);
				_beginTime = TimeTools.parseSecond(beginTime.getTime());
			}
			
			if(StringUtil.isBlank(endTimeStr))
				endTime=null;
			else {
				endTime = new Date(endTimeStr);
				_endTime = TimeTools.parseSecond(endTime.getTime());
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			ret.setStatus(GlobalConsts.ResultCode_INVALIDATION);
			ret.setMsg("日期格式不正确");
			return ret;
		}
		try {
			
			List<PointAlertData> rtd = alertDataManager.getAlertData(g, _beginTime, _endTime);
			
//			HistoryData rtd = historyDataManager.getHistoryData(rtdId);
			if (rtd == null ) {
				ret.setStatus(GlobalConsts.ResultCode_INVALIDATION);
				ret.setMsg("没有指定ID的数据。");
				ret.setRefresh(true);
				return ret;
			}
			if (rtd != null) {
				// 更新用户空间
				// UserSpace us = userSpaceManager.getUserSpaceRigidly(Long.valueOf(uid));
				ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
				ret.setMsg("成功");
				ret.setData("data", rtd);
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
}
