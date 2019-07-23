package com.surpass.vision.controller;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.surpass.vision.domain.FileList;
import com.surpass.vision.service.GraphService;

@RestController
//@RequestMapping("/v1")
public class GraphController {

		@Reference
		@Autowired
		GraphService gs;

		@RequestMapping(value = "getGraphChange", method = { RequestMethod.POST, RequestMethod.GET })
		public @ResponseBody JSONObject getChange(@RequestBody JSONObject ver,HttpServletRequest request)
				throws Exception {
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
				ret.put("fl", fl.toJSONObject());
				return ret;
			}
		}

}
