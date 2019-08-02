package com.surpass.vision.controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

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
import com.surpass.vision.domain.FileList;
import com.surpass.vision.exception.ExceptionEnum;
import com.surpass.vision.exception.GirlFriendNotFoundException;
import com.surpass.vision.exception.ResponseBean;
import com.surpass.vision.service.AuthorcationService;
import com.surpass.vision.service.GraphService;

@RestController
//@RequestMapping("/v1")
public class GraphController {

		@Reference
		@Autowired
		GraphService gs;
		
		@Reference
		@Autowired
		AuthorcationService as;

		@RequestMapping(value = "getAllGraph", method = { RequestMethod.POST, RequestMethod.GET })
		public @ResponseBody JSONObject getAllGraph(@RequestBody JSONObject user,HttpServletRequest request)
				throws Exception {
			/**
			 * 
			 * 简化处理，不考虑本地化及TOKEN伪造问题
			 * 也没采用Spring-Security，单纯使用 token模式。
			 *
			 Locale local = request.getLocale();
			 String language = local.getDisplayLanguage();
			 String remoteAddr = request.getRemoteAddr();
			*/
		 	JSONObject ret = new JSONObject();
			Integer uid = user.getInteger("uid");
			String token = user.getString("token");
			if(!as.tokenVerification(uid, token)) {
//				throw new NotAuthorizedException("You Don't Have Permission");
				ret.put("resultCode", "401");
				return ret;
			}
				
			Integer version = user.getInteger("version");
			if(version == null) version =0;
			
			int v = gs.getCurrentVersion();
			if(v==version) {
				/***
				 * type=1,是相邻版本，返回更新
				 * type=0是相同版本，什么都不用做
				 * type=2是版本错误，可能差了多于1个版本，重新导入服务端完整数据
				 */
				// 
				ret.put("resultCode", "200");
				ret.put("type", "0");
				return ret;
			}
			else if(v==version+1) {
				ret.put("type", "1"); 
				ret.put("version", v);
				JSONArray ja = new JSONArray();
				ArrayList<FileList> upd = gs.getCurrentUpdate();
				System.out.println(upd.size());
				for(int i=0;i<upd.size();i++) {
					JSONObject jo = new JSONObject();
					FileList fl = upd.get(i);
					jo.put("change",fl.getChanged());
					jo.put("name",fl.getName());
					jo.put("path",fl.getPath());
					jo.put("isFile",fl.isFile());
					System.out.println(fl.getName());
					ja.add(jo);
				}
				ret.put("change",ja);
				return ret;
			} else {
//				List<Graph> graphs = gs.getGraph(uid)
				FileList fl = gs.getCurrentFileList();
				if(fl==null) {
					ret.put("type", "0");
					return ret;
				}
				ret.put("type", "2");
				ret.put("version", v);
				ret.put("fl", fl);
				return ret;
			}
//			return ret;
		}
		
//	    @ExceptionHandler(GirlFriendNotFoundException.class)
//	    @ResponseBody
//	    public ResponseBean handleGirlFriendNotFound(GirlFriendNotFoundException exception){
////	        loggerError(exception);
//	        return new ResponseBean(ExceptionEnum.GIRL_FRIEND_NOT_FOUND);
//	    }
	    
		@RequestMapping(value = "getGraphChange", method = { RequestMethod.POST, RequestMethod.GET })
		public @ResponseBody JSONObject getChange(@RequestBody JSONObject ver,HttpServletRequest request)
				throws Exception {
			if(true)
			throw new GirlFriendNotFoundException("You Don't Have Permission");

			JSONObject ret = new JSONObject();
			int v = gs.getCurrentVersion();
			Integer version = Integer.parseInt(ver.getString("ver"));
			if(v==version) {
				/***
				 * type=1,是相邻版本，返回更新
				 * type=0是相同版本，什么都不用做
				 * type=2是版本错误，可能差了多于1个版本，重新导入服务端完整数据
				 */
				// 
				ret.put("type", "0");
				return ret;
			}
			else if(v==version+1) {
				ret.put("type", "1"); 
				ret.put("version", v);
				JSONArray ja = new JSONArray();
				ArrayList<FileList> upd = gs.getCurrentUpdate();
				System.out.println(upd.size());
				for(int i=0;i<upd.size();i++) {
					JSONObject jo = new JSONObject();
					FileList fl = upd.get(i);
					jo.put("change",fl.getChanged());
					jo.put("name",fl.getName());
					jo.put("path",fl.getPath());
					jo.put("isFile",fl.isFile());
					System.out.println(fl.getName());
					ja.add(jo);
				}
				ret.put("change",ja);
				return ret;
			} else {
				FileList fl = gs.getCurrentFileList();
				if(fl==null) {
					ret.put("type", "0");
					return ret;
				}
				ret.put("type", "2");
				ret.put("version", v);
				ret.put("fl", fl);
				return ret;
			}
		}

}
