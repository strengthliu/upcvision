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
import com.surpass.vision.XYGraph.XYGraphManager;
import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.common.ToWeb;
import com.surpass.vision.domain.XYGraph;
import com.surpass.vision.domain.LineAlertData;
import com.surpass.vision.domain.RealTimeData;
import com.surpass.vision.domain.UserRight;
import com.surpass.vision.domain.UserSpace;
import com.surpass.vision.service.AuthorcationService;
import com.surpass.vision.service.GraphService;

@RestController
//@RequestMapping("/v1")
public class XYGraphController extends BaseController{

	@Autowired
	XYGraphManager xYGraphManager;

	/**
	 * 获取指定用户的实时数据列表
	 * 
	 * @param user
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getXYGraphList", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb getXYGraphList(@RequestBody JSONObject user, HttpServletRequest request) throws Exception {
		Double uid = user.getDouble("uid");
		String token = user.getString("token");
		// 认证+权限
		UserRight ur = new UserRight();
		ToWeb ret = authercation(uid, token, GlobalConsts.Operation_getXYGraphList,ur);
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
			Hashtable<String, XYGraph> data = us.getXyGraph();
			ret.setMsg("成功");
			ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
//				ret.setData();
//				ret.setData("data", data);
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("XYGraph", hm);
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
	@RequestMapping(value = "getXYGraph", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody JSONObject getXYGraph(@RequestBody JSONObject user, HttpServletRequest request)
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
	@RequestMapping(value = "newXYGraphGroup", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb newXYGraphGroup(@RequestBody JSONObject user, HttpServletRequest request) throws Exception {
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
			ret = authercation(uid, token, GlobalConsts.Operation_createXYGraph,ur);
			if (!StringUtil.isBlank(ret.getStatus()) && (!ret.getStatus().contentEquals(GlobalConsts.ResultCode_SUCCESS)))
				return ret;
			
		}else {
			id = Double.valueOf(idstr);
		// 认证+权限
			XYGraph g = this.xYGraphManager.getXYGraphByKeys(id);
		UserRight ur = g.getRight(uid);
		ret = authercation(uid, token, GlobalConsts.Operation_updateXYGraph,ur);
		if (!StringUtil.isBlank(ret.getStatus()) && (!ret.getStatus().contentEquals(GlobalConsts.ResultCode_SUCCESS)))
			return ret;
		}

		try {
			XYGraph rtd = xYGraphManager.createXYGraph(GlobalConsts.Type_xygraph_, name, owner, creater,points,otherrule2,otherrule1,idstr);
			if (rtd != null) {
				// 更新用户空间
				UserSpace us = userSpaceManager.getUserSpaceRigidly(Double.valueOf(uid));
				userSpaceManager.updateXYGraph(rtd,Double.valueOf(0));
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
	
	@RequestMapping(value = "deleteXYGraphGroup", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb deleteXYGraphGroup(@RequestBody JSONObject user, HttpServletRequest request) throws Exception {
		Double uid = user.getDouble("uid");
		String token = user.getString("token");
		String idstr = user.getString("id");
		Double id = null ;
		if(StringUtil.isBlank(idstr)) {
			
		}else {
			id = Double.valueOf(idstr);
		}
		
		// 认证+权限
		XYGraph g = this.xYGraphManager.getXYGraphByKeys(id);
		UserRight ur = g.getRight(uid);
		ToWeb ret = authercation(uid, token, GlobalConsts.Operation_updateXYGraph,ur);
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
			XYGraph rtd = xYGraphManager.getXYGraphByKeys(rtdId);
			if(rtd == null || StringUtil.isBlank(rtd.getOwner())) {
				ret.setStatus(GlobalConsts.ResultCode_INVALIDATION);
				ret.setMsg("没有指定ID的数据。");
				ret.setRefresh(true);
				return ret;
			}
			rtd = xYGraphManager.deleteXYGraph(idstr);
			if (rtd != null) {
				// 更新用户空间
				//UserSpace us = userSpaceManager.getUserSpaceRigidly(Long.valueOf(uid));
				userSpaceManager.updateXYGraph(null,rtd);
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
	
	@RequestMapping(value = "shareRightXYGraph", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb shareRight(@RequestBody JSONObject user, HttpServletRequest request) throws Exception {
		Double uid = user.getDouble("uid");
		String token = user.getString("token");
		String idstr = user.getString("id");
		Double id = null ;
		if(StringUtil.isBlank(idstr)) {
			
		}else {
			id = Double.valueOf(idstr);
		}
		
		// 认证+权限
		XYGraph g = this.xYGraphManager.getXYGraphByKeys(id);
		UserRight ur = g.getRight(uid);
		ToWeb ret = authercation(uid, token, GlobalConsts.Operation_updateXYGraph,ur);
		if (!StringUtil.isBlank(ret.getStatus()) && (!ret.getStatus().contentEquals(GlobalConsts.ResultCode_SUCCESS)))
			return ret;

		// 取出参数
		// var data={'uid':uid,'token':token,'userIds':Array.from(selectedUsers),'type':"XYGraph"};
		// {'uid':uid,'token':token,'points':selectedPoints,'name':targetName}
		JSONArray juserIds = user.getJSONArray("userIds");
		String type = user.getString("type");
		List<String> userIds = JSONObject.parseArray(juserIds.toJSONString(), String.class);
		// TODO: 检查参数合法性

		try {
			XYGraph rtd = xYGraphManager.updateShareRight(id,userIds);
			if (rtd != null) {
				// 更新用户空间
				UserSpace us = userSpaceManager.getUserSpaceRigidly(Double.valueOf(uid));
				userSpaceManager.updateXYGraph(rtd,Double.valueOf(0));
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

}
