package com.surpass.vision.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.surpass.vision.service.GraphService;

@RestController
//@RequestMapping("/v1")
public class ListController {

		@Reference
		@Autowired
		GraphService gs;

		/**
		 * @param req 请求对象，包含type：列表类型；token：用户token。
		 * @param request
		 * @return 列表，每个元素包含作者和组名。
		 * @throws Exception
		 */
		@RequestMapping(value = "getList", method = { RequestMethod.POST, RequestMethod.GET })
		public @ResponseBody JSONArray getChange(@RequestBody JSONObject req,HttpServletRequest request)
				throws Exception {
			String type;
			String token;
			
			
			JSONArray ret = new JSONArray();
			return ret;
		}
}
