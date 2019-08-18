package com.surpass.vision.controller;

import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.common.Submit;
import com.surpass.vision.common.ToWeb;
import com.surpass.vision.domain.UserSpace;
import com.surpass.vision.realTimeData.RealTimeDataManager;
import com.surpass.vision.server.Server;
import com.surpass.vision.server.ServerManager;

@RestController
public class ServerInfoController extends BaseController {
	@Autowired
	RealTimeDataManager realTimeDataManager;

	/**
	 * 获取指定用户的实时数据列表
	 * 
	 * @param user
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Submit
	@RequestMapping(value = "getServerInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb getServerInfo(@RequestBody JSONObject uidToken,  HttpServletRequest request) throws Exception {
		String uid = uidToken.getString("uid");
		String token = uidToken.getString("token");
		// 认证+权限
		ToWeb ret = authercation(Long.valueOf(uid), token, GlobalConsts.Operation_getRealTimeDataList);
		if (!StringUtil.isBlank(ret.getStatus()) && ret.getStatus()!=GlobalConsts.ResultCode_SUCCESS)
			return ret;

		// 取出服务器信息
		Hashtable<String,Server> data =ServerManager.getInstance().getServers();
		ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
		ret.setData("data",data);
		return ret;
	}
}
