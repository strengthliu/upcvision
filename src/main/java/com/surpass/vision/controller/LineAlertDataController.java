package com.surpass.vision.controller;

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
import com.surpass.vision.domain.RealTimeData;
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

	
		@RequestMapping(value = "getLineAlerDataList", method = { RequestMethod.POST, RequestMethod.GET })
		public @ResponseBody JSONObject getLineAlerDataList(@RequestBody JSONObject user,HttpServletRequest request)
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
			Long uid = user.getLong("uid");
			String token = user.getString("token");
			// 认证+权限
			ToWeb ret = authercation(uid, token, GlobalConsts.Operation_createOrUpdateRealTimeData);
			if (!StringUtil.isBlank(ret.getStatus()) && (!ret.getStatus().contentEquals(GlobalConsts.ResultCode_SUCCESS)))
				return ret;

			// 取出参数
			// {'uid':uid,'token':token,'points':selectedPoints,'name':targetName}
			String owner = user.getString("uid");
			String name = user.getString("name");
			String creater = owner;
			JSONArray points = user.getJSONArray("points");
			String otherrule2 = user.getString("desc");
			
			// TODO: 检查参数合法性

			try {
				LineAlertData rtd = lineAlertDataManager.createLineAlertData(GlobalConsts.Type_linealertdata_, name, owner, creater,points,otherrule2);
				if (rtd != null) {
					// TODO:  更新自己的用户空间
					UserSpace us = userSpaceManager.getUserSpaceRigidly(Long.valueOf(uid));
					userSpaceManager.updateLineAlertData(rtd,Long.valueOf(0));
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
