package com.surpass.vision.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import com.surpass.vision.domain.HistoryData;
import com.surpass.vision.domain.UserRight;
import com.surpass.vision.domain.UserSpace;
import com.surpass.vision.historyData.HistoryDataManager;
import com.surpass.vision.service.AuthorcationService;
import com.surpass.vision.service.GraphService;
import com.surpass.vision.tools.TimeTools;

@RestController
//@RequestMapping("/v1")
public class HistoryDataController extends BaseController {

//	@Reference
	@Autowired
	HistoryDataManager historyDataManager;

	/**
	 * 获取指定用户的实时数据列表
	 * 
	 * @param user
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getHistoryDataList", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb getHistoryDataList(@RequestBody JSONObject user, HttpServletRequest request) throws Exception {
		Double uid = user.getDouble("uid");
		String token = user.getString("token");
		// 认证+权限
		UserRight ur = new UserRight();
		ToWeb ret = authercation(uid, token, GlobalConsts.Operation_getHistoryDataList, ur);
		if (!StringUtil.isBlank(ret.getStatus()))
			return ret;

		// 取出用户空间
		UserSpace us = userSpaceManager.getUserSpace(Double.valueOf(uid));
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
			Hashtable<String, HistoryData> data = us.getHistoryData();
			ret.setMsg("成功");
			ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
//					ret.setData();
//					ret.setData("data", data);
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("HistoryData", hm);
//					hm.put("userInfo", us.getUser());
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
	@RequestMapping(value = "newHistoryDataGroup", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb newHistoryDataGroup(@RequestBody JSONObject user, HttpServletRequest request) throws Exception {
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
		ToWeb ret;

		// TODO: 检查参数合法性

		String idstr = user.getString("id");
		Double id = null;
		if (StringUtil.isBlank(idstr)) {
			// 认证+权限
			UserRight ur = new UserRight();
			ret = authercation(uid, token, GlobalConsts.Operation_createHistoryData, ur);
			if (!StringUtil.isBlank(ret.getStatus())
					&& (!ret.getStatus().contentEquals(GlobalConsts.ResultCode_SUCCESS)))
				return ret;

		} else {
			id = Double.valueOf(idstr);
			// 认证+权限
			HistoryData g = this.historyDataManager.getHistoryDataByKeys(id);
			UserRight ur = g.getRight(uid);
			ret = authercation(uid, token, GlobalConsts.Operation_updateHistoryData, ur);
			if (!StringUtil.isBlank(ret.getStatus())
					&& (!ret.getStatus().contentEquals(GlobalConsts.ResultCode_SUCCESS)))
				return ret;
		}

		// TODO: 检查参数合法性

		try {
			HistoryData rtd = historyDataManager.createHistoryData(GlobalConsts.Type_historydata_, name, owner, creater,
					points, otherrule2, otherrule1, idstr);
			if (rtd != null) {
				// 更新用户空间
				UserSpace us = userSpaceManager.getUserSpaceRigidly(Double.valueOf(uid));
				userSpaceManager.updateHistoryData(rtd, Double.valueOf(0));
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

	@RequestMapping(value = "deleteHistoryDataGroup", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb deleteHistoryDataGroup(@RequestBody JSONObject user, HttpServletRequest request) throws Exception {
		Double uid = user.getDouble("uid");
		String token = user.getString("token");
		String idstr = user.getString("id");
		Double idd = null;
		if (StringUtil.isBlank(idstr)) {

		} else {
			idd = Double.valueOf(idstr);
		}

		// 认证+权限
		HistoryData g = this.historyDataManager.getHistoryDataByKeys(idd);
		if(g==null) {
			ToWeb ret = ToWeb.buildResult();
			ret.setStatus(GlobalConsts.ResultCode_INVALIDATION);
			ret.setMsg("没有指定ID的历史数据，请清除浏览器缓存再试。如果还有，请联系系统管理员。");
		}
		UserRight ur = g.getRight(uid);
		ToWeb ret = authercation(uid, token, GlobalConsts.Operation_updateHistoryData, ur);
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
		try {
			HistoryData rtd = historyDataManager.getHistoryDataByKeys(rtdId);
			if (rtd == null || StringUtil.isBlank(rtd.getOwner())) {
				ret.setStatus(GlobalConsts.ResultCode_INVALIDATION);
				ret.setMsg("没有指定ID的数据。");
				ret.setRefresh(true);
				return ret;
			}
			rtd = historyDataManager.deleteHistoryData(id);
			if (rtd != null) {
				// 更新用户空间
				// UserSpace us = userSpaceManager.getUserSpaceRigidly(Long.valueOf(uid));
				userSpaceManager.updateHistoryData(null, rtd);
				ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
				ret.setMsg("成功");
				ret.setData("data", id);
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

	@RequestMapping(value = "shareRightHistoryData", method = { RequestMethod.POST, RequestMethod.GET })
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
		HistoryData g = this.historyDataManager.getHistoryDataByKeys(id);
		UserRight ur = g.getRight(uid);
		ToWeb ret = authercation(uid, token, GlobalConsts.Operation_updateHistoryData, ur);
		if (!StringUtil.isBlank(ret.getStatus()) && (!ret.getStatus().contentEquals(GlobalConsts.ResultCode_SUCCESS)))
			return ret;

		// 取出参数
		// var
		// data={'uid':uid,'token':token,'userIds':Array.from(selectedUsers),'type':"HistoryData"};
		// {'uid':uid,'token':token,'points':selectedPoints,'name':targetName}
		JSONArray juserIds = user.getJSONArray("userIds");
		String type = user.getString("type");
		List<String> userIds = JSONObject.parseArray(juserIds.toJSONString(), String.class);
		// TODO: 检查参数合法性

		try {
			HistoryData rtd = historyDataManager.updateShareRight(id, userIds);
			if (rtd != null) {
				// 更新用户空间
				UserSpace us = userSpaceManager.getUserSpaceRigidly(Double.valueOf(uid));
				userSpaceManager.updateHistoryData(rtd, Double.valueOf(0));
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
	
	/**
	 * 获取一个历史数据
	 * 
	 * @param user
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getHistoryData", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb getHistoryData(@RequestBody JSONObject user, HttpServletRequest request) throws Exception {
		Double uid = user.getDouble("uid");
		String token = user.getString("token");
		String idstr = user.getString("id");
		Double idd = null;
		if (StringUtil.isBlank(idstr)) {

		} else {
			idd = Double.valueOf(idstr);
		}

		// 认证+权限
		HistoryData g = this.historyDataManager.getHistoryDataByKeys(idd);
		UserRight ur = g.getRight(uid);
		ToWeb ret = authercation(uid, token, GlobalConsts.Operation_getHistoryData, ur);
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
//		new Timestamp();
//		new Date(beginTimeStr).UTC(year, month, date, hrs, min, sec);
		Date endTime = null;
		Long _endTime = null;
		try {
			if(StringUtil.isBlank(endTimeStr)) {
				SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				_endTime=TimeTools.parseSecond(sDateFormat.parse("2019-09-22 21:00:00").getTime());
				_endTime=TimeTools.parseSecond(System.currentTimeMillis());
			}
			else {
//				endTime = new Date(endTimeStr);
				_endTime = TimeTools.parseSecond(Long.valueOf(endTimeStr));
			}

			if(StringUtil.isBlank(beginTimeStr)) {
				SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				_beginTime=TimeTools.parseSecond(sDateFormat.parse("2019-09-22 19:00:00").getTime());
//				_beginTime=TimeTools.parseSecond(System.currentTimeMillis());
				_beginTime=_endTime-2*60*60;
			}
			else {
//				beginTime = new Date(beginTimeStr);
				_beginTime = TimeTools.parseSecond(Long.valueOf(beginTimeStr));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			ret.setStatus(GlobalConsts.ResultCode_INVALIDATION);
			ret.setMsg("日期格式不正确");
			return ret;
		}

		try {
//			historyDataManager.getHistoryData(rtdId, Long.valueOf(1), Long.valueOf(1));
			ArrayList<?> rtd = historyDataManager.getHistoryData(rtdId, _beginTime, _endTime);
			
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
