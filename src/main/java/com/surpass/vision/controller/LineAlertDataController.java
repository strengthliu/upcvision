package com.surpass.vision.controller;

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
import com.surpass.vision.domain.LineAlertData;
import com.surpass.vision.domain.HistoryData;
import com.surpass.vision.domain.LineAlertData;
import com.surpass.vision.domain.LineAlertData;
import com.surpass.vision.domain.RealTimeData;
import com.surpass.vision.domain.UserRight;
import com.surpass.vision.domain.UserSpace;
import com.surpass.vision.lineAlertData.LineAlertDataManager;
import com.surpass.vision.realTimeData.RealTimeDataManager;
import com.surpass.vision.service.AuthorcationService;
import com.surpass.vision.service.GraphService;

@RestController
//@RequestMapping("/v1")
public class LineAlertDataController extends BaseController {

	@Autowired
	LineAlertDataManager lineAlertDataManager;

	/**
	 * 获取指定用户的实时数据列表
	 * 
	 * @param user
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getLineAlertDataList", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb getLineAlertDataList(@RequestBody JSONObject user, HttpServletRequest request) throws Exception {
		Double uid = user.getDouble("uid");
		String token = user.getString("token");
		// 认证+权限
		UserRight ur = new UserRight();
		ToWeb ret = authercation(uid, token, GlobalConsts.Operation_getLineAlertDataList,ur);
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
			Hashtable<String, LineAlertData> data = us.getLineAlertData();
			ret.setMsg("成功");
			ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
//				ret.setData();
//				ret.setData("data", data);
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("LineAlertData", hm);
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
	@RequestMapping(value = "getLineAlertData", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody JSONObject getLineAlertData(@RequestBody JSONObject user, HttpServletRequest request)
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
	@RequestMapping(value = "newLineAlertDataGroup", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb newLineAlertDataGroup(@RequestBody JSONObject user, HttpServletRequest request) throws Exception {
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
		Double id = null ;
		if(StringUtil.isBlank(idstr)) {
			// 认证+权限
			UserRight ur = new UserRight();
			ret = authercation(uid, token, GlobalConsts.Operation_createLineAlertData,ur);
			if (!StringUtil.isBlank(ret.getStatus()) && (!ret.getStatus().contentEquals(GlobalConsts.ResultCode_SUCCESS)))
				return ret;
			
		}else {
			id = Double.valueOf(idstr);
		// 认证+权限
			LineAlertData g = this.lineAlertDataManager.getLineAlertDataByKeys(id);
		UserRight ur = g.getRight(uid);
		ret = authercation(uid, token, GlobalConsts.Operation_updateLineAlertData,ur);
		if (!StringUtil.isBlank(ret.getStatus()) && (!ret.getStatus().contentEquals(GlobalConsts.ResultCode_SUCCESS)))
			return ret;
		}

		// TODO: 检查参数合法性

		try {
			LineAlertData rtd = lineAlertDataManager.createLineAlertData(GlobalConsts.Type_linealertdata_, name, owner, creater,points,otherrule2,otherrule1,idstr);
			if (rtd != null) {
				// 更新用户空间
				UserSpace us = userSpaceManager.getUserSpaceRigidly(Double.valueOf(uid));
				userSpaceManager.updateLineAlertData(rtd,Double.valueOf(0));
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
	
	@RequestMapping(value = "deleteLineAlertDataGroup", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb deleteLineAlertDataGroup(@RequestBody JSONObject user, HttpServletRequest request) throws Exception {
		Double uid = user.getDouble("uid");
		String token = user.getString("token");
		String idstr = user.getString("id");
		Double id = null ;
		if(StringUtil.isBlank(idstr)) {
			
		}else {
			id = Double.valueOf(idstr);
		}
		
		// 认证+权限
		LineAlertData g = this.lineAlertDataManager.getLineAlertDataByKeys(id);
		UserRight ur = g.getRight(uid);
		ToWeb ret = authercation(uid, token, GlobalConsts.Operation_updateLineAlertData,ur);
		if (!StringUtil.isBlank(ret.getStatus()) && (!ret.getStatus().contentEquals(GlobalConsts.ResultCode_SUCCESS)))
			return ret;

		// 取出参数
		// {'uid':uid,'token':token,'points':selectedPoints,'name':targetName}
		// 检查参数合法性
		if(StringUtil.isBlank(idstr)) {
			ret.setStatus(GlobalConsts.ResultCode_FAIL);
			ret.setMsg("参数不正确，ID不能为空。");
			return ret;
		}
		Double rtdId = Double.valueOf(id);
		try {
			LineAlertData rtd = lineAlertDataManager.getLineAlertDataByKeys(rtdId);
			if(rtd == null || StringUtil.isBlank(rtd.getOwner())) {
				ret.setStatus(GlobalConsts.ResultCode_INVALIDATION);
				ret.setMsg("没有指定ID的数据。");
				ret.setRefresh(true);
				return ret;
			}
			rtd = lineAlertDataManager.deleteLineAlertData(idstr);
			if (rtd != null) {
				// 更新用户空间
				//UserSpace us = userSpaceManager.getUserSpaceRigidly(Long.valueOf(uid));
				userSpaceManager.updateLineAlertData(null,rtd);
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
	
	@RequestMapping(value = "shareRightLineAlertData", method = { RequestMethod.POST, RequestMethod.GET })
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
		LineAlertData g = lineAlertDataManager.getLineAlertDataByKeys(id);
		UserRight ur = g.getRight(uid);

		ToWeb ret = authercation(uid, token, GlobalConsts.Operation_updateLineAlertData, ur);
		if (!StringUtil.isBlank(ret.getStatus()) && (!ret.getStatus().contentEquals(GlobalConsts.ResultCode_SUCCESS)))
			return ret;

		// 取出参数
		// var
		// data={'uid':uid,'token':token,'userIds':Array.from(selectedUsers),'type':"Graph"};
		// {'uid':uid,'token':token,'points':selectedPoints,'name':targetName}
		JSONArray juserIds = user.getJSONArray("userIds");
		JSONArray jdepartIds = user.getJSONArray("departIds");
		LineAlertData rtd = null;
		List<String> userIds = null;
		List<String> departIds = null;
		if (juserIds != null && juserIds.size() > 0) {
			userIds = JSONObject.parseArray(juserIds.toJSONString(), String.class);
		}
		if (jdepartIds != null && jdepartIds.size() > 0) {
			departIds = JSONObject.parseArray(jdepartIds.toJSONString(), String.class);
		}
		try {
			rtd = lineAlertDataManager.updateShareRight(id, userIds, departIds);
			if (rtd != null) {
				// 更新用户空间
				UserSpace us = userSpaceManager.getUserSpaceRigidly(Double.valueOf(uid));
//				userSpaceManager.updateLineAlertData(rtd, Double.valueOf(0));
				userSpaceManager.updateLineAlertData(g,rtd);
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

}

