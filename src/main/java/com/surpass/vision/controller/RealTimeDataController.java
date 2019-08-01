package com.surpass.vision.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.service.AuthorcationService;
import com.surpass.vision.service.GraphService;
import com.surpass.vision.service.RealTimeDataService;
import com.surpass.vision.domain.*;
import com.surpass.vision.realTimeData.RealTimeDataManager;

@RestController
//@RequestMapping("/v1")
public class RealTimeDataController {

		@Reference
		@Autowired
		RealTimeDataService realTimeDataService;
		
		@Autowired
		RealTimeDataManager realTimeDataManager;
		
		@Reference
		@Autowired
		AuthorcationService as;

		/**
		 * 获取指定用户的实时数据列表
		 * @param user
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "getRealTimeDataList", method = { RequestMethod.POST, RequestMethod.GET })
		public @ResponseBody JSONObject getRealTimeDataList(@RequestBody JSONObject user,HttpServletRequest request)
				throws Exception {
			// TODO
			// 认证
		 	JSONObject ret = new JSONObject();
			Integer uid = user.getInteger("uid");
			String token = user.getString("token");
			if(!as.tokenVerification(uid, token)) {
//				throw new NotAuthorizedException("You Don't Have Permission");
				ret.put("resultCode", GlobalConsts.ResultCode_AuthericationError);
				return ret;
			}
			
			// 取出参数
			// 调用服务
			List<RealTimeData> rtdst = realTimeDataService.getRealTimeDataList(uid);
			
			// 先到用户空间里找，没有就建用户空间，
			return null;
		}

		/**
		 * 获取实时数据
		 * @param user
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "getRealTimeData", method = { RequestMethod.POST, RequestMethod.GET })
		public @ResponseBody JSONObject getRealTimeData(@RequestBody JSONObject user,HttpServletRequest request)
				throws Exception {
			// TODO
			return null;
		}
		
		/**
		 * 新建一个实时数据列表
		 * @param user
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "newRealTimeData", method = { RequestMethod.POST, RequestMethod.GET })
		public @ResponseBody JSONObject newRealTimeData(@RequestBody JSONObject user,HttpServletRequest request)
				throws Exception {
			// 认证
		 	JSONObject ret = new JSONObject();
			Integer uid = user.getInteger("uid");
			String token = user.getString("token");
			if(!as.tokenVerification(uid, token)) {
//				throw new NotAuthorizedException("You Don't Have Permission");
				ret.put("resultCode", GlobalConsts.ResultCode_AuthericationError);
				return ret;
			}
			
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
		    RealTimeData rtd = realTimeDataService.newRealTimeData(type,name,owner,creater,shared,points,otherrule1,otherrule2);
		    
			return null;
		}

}
