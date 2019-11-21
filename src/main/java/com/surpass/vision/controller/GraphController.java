package com.surpass.vision.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mchange.rmi.NotAuthorizedException;
import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.common.Submit;
import com.surpass.vision.common.ToWeb;
import com.surpass.vision.domain.FileList;
import com.surpass.vision.domain.Graph;
import com.surpass.vision.domain.UserRight;
import com.surpass.vision.domain.UserSpace;
import com.surpass.vision.exception.ExceptionEnum;
import com.surpass.vision.exception.GirlFriendNotFoundException;
import com.surpass.vision.exception.ResponseBean;
import com.surpass.vision.graph.GraphManager;
import com.surpass.vision.server.Point;
import com.surpass.vision.server.ServerManager;
import com.surpass.vision.service.AuthorcationService;
import com.surpass.vision.service.GraphService;

@RestController
//@RequestMapping("/v1")
public class GraphController extends BaseController {

	@Reference
	@Autowired
	GraphManager graphManager;

	/**
	 * 获取指定用户的实时数据列表
	 * 
	 * @param user
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getGraphList", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb getGraphList(@RequestBody JSONObject user, HttpServletRequest request) throws Exception {
		Double uid = user.getDouble("uid");
		String token = user.getString("token");
		// 认证+权限
		UserRight ur = new UserRight();
		
		ToWeb ret = authercation(uid, token, GlobalConsts.Operation_getGraphList,ur);
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
			Graph data = us.getGraph();
			ret.setMsg("成功");
			ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
//				ret.setData();
//				ret.setData("data", data);
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("Graph", hm);
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

	
	@RequestMapping(value = "shareRightGraph", method = { RequestMethod.POST, RequestMethod.GET })
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
		Graph g = graphManager.getGraphByKeys(id);
		UserRight ur = g.getRight(uid);
		
		ToWeb ret = authercation(uid, token, GlobalConsts.Operation_shareGraph,ur);
		if (!StringUtil.isBlank(ret.getStatus()) && (!ret.getStatus().contentEquals(GlobalConsts.ResultCode_SUCCESS)))
			return ret;

		// 取出参数
		// var data={'uid':uid,'token':token,'userIds':Array.from(selectedUsers),'type':"Graph"};
		// {'uid':uid,'token':token,'points':selectedPoints,'name':targetName}
		JSONArray juserIds = user.getJSONArray("userIds");
		String type = user.getString("type");
			
		List<String> userIds = JSONObject.parseArray(juserIds.toJSONString(), String.class);
		// TODO: 检查参数合法性

		try {
			Graph rtd = graphManager.updateShareRight(id,userIds);
			if (rtd != null) {
				// 更新用户空间
				UserSpace us = userSpaceManager.getUserSpaceRigidly(Double.valueOf(uid));
				userSpaceManager.updateGraph(rtd,Double.valueOf(0));
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
	
	
	/**
	 * 获取指定用户的实时数据列表
	 * 
	 * @param user
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Submit
	@RequestMapping(value = "getGraphPointInfoMapper", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb getGraphPointInfoMapper(@RequestBody JSONObject uidToken,  HttpServletRequest request) throws Exception {
		String uid = uidToken.getString("uid");
		String token = uidToken.getString("token");
		String graphID = uidToken.getString("graphID");
		Double id = null;
		// 认证+权限
		UserRight ur = new UserRight();
		ToWeb ret = authercation(Double.valueOf(uid), token, GlobalConsts.Operation_getRealTimeDataList,ur);
		if (!StringUtil.isBlank(ret.getStatus()) && ret.getStatus()!=GlobalConsts.ResultCode_SUCCESS)
			return ret;
		
		try {
			id = Double.valueOf(graphID);
		}catch(Exception e) {
			ret.setStatus(GlobalConsts.ResultCode_INVALIDATION);
			ret.setMsg("参数错误：图形ID必须是数字。");
			return ret;
		}
		
		if (StringUtil.isBlank(graphID)) {
			ret.setStatus(GlobalConsts.ResultCode_INVALIDATION);
			ret.setMsg("参数错误：图形ID不能为空。");
			return ret;
		}

		// 取出信息
		Graph g = graphManager.getGraphByKeys(id);
		ArrayList<String> txtids = g.getPointTextIDs();
		List<Point> pl = g.getPointList();
		Hashtable<String,Point> data = new Hashtable<String,Point>();
		for(int i=0;i<txtids.size();i++) {
			data.put(txtids.get(i), pl.get(i));
		}
		ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
		ret.setData("data",data);
		return ret;
	}

	
}

