package com.surpass.vision.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.surpass.vision.service.AuthorcationService;
import com.surpass.vision.service.GraphService;

@RestController
//@RequestMapping("/v1")
public class AlertDataController {

		@Reference
		@Autowired
		GraphService gs;
		
		@Reference
		@Autowired
		AuthorcationService as;

		@RequestMapping(value = "getAlertList", method = { RequestMethod.POST, RequestMethod.GET })
		public @ResponseBody JSONObject getAlertList(@RequestBody JSONObject user,HttpServletRequest request)
				throws Exception {
			// TODO
			return null;
		}

}
