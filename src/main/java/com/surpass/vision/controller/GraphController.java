package com.surpass.vision.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Reference;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
import com.surpass.vision.domain.Graph;
import com.surpass.vision.exception.ExceptionEnum;
import com.surpass.vision.exception.GirlFriendNotFoundException;
import com.surpass.vision.exception.ResponseBean;
import com.surpass.vision.graph.GraphDataManager;
import com.surpass.vision.graph.GraphManager;
import com.surpass.vision.server.Point;
import com.surpass.vision.server.ServerManager;
import com.surpass.vision.service.AuthorcationService;
import com.surpass.vision.service.GraphService;
import com.surpass.vision.tools.FileTool;
import com.surpass.vision.userSpace.UserSpaceManager;

@RestController
//@RequestMapping("/v1")
public class GraphController extends BaseController {

	@Reference
	@Autowired
	GraphManager graphManager;

	@Reference
	@Autowired
	GraphDataManager graphDataManager;

	@Reference
	@Autowired
	UserSpaceManager userSpaceManager;

	@Value("${upc.graphPath}")
	private String graphPath;

	@Value("${upc.graphServerPath}")
	private String graphServerPath;

	// upc.graphServerPath
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

		ToWeb ret = authercation(uid, token, GlobalConsts.Operation_getGraphList, ur);
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
		Double id = null;
		if (StringUtil.isBlank(idstr)) {

		} else {
			id = Double.valueOf(idstr);
		}

		// 认证+权限
		Graph g = graphManager.getGraphByKeys(id);
		UserRight ur = g.getRight(uid);

		ToWeb ret = authercation(uid, token, GlobalConsts.Operation_shareGraph, ur);
		if (!StringUtil.isBlank(ret.getStatus()) && (!ret.getStatus().contentEquals(GlobalConsts.ResultCode_SUCCESS)))
			return ret;

		// 取出参数
		// var
		// data={'uid':uid,'token':token,'userIds':Array.from(selectedUsers),'type':"Graph"};
		// {'uid':uid,'token':token,'points':selectedPoints,'name':targetName}
		JSONArray juserIds = user.getJSONArray("userIds");
		JSONArray jdepartIds = user.getJSONArray("departIds");
		Graph rtd = null;
		List<String> userIds = null;
		List<String> departIds = null;
		if (juserIds != null && juserIds.size() > 0) {
			userIds = JSONObject.parseArray(juserIds.toJSONString(), String.class);
		}
		if (jdepartIds != null && jdepartIds.size() > 0) {
			departIds = JSONObject.parseArray(jdepartIds.toJSONString(), String.class);
		}
		try {
			rtd = graphManager.updateShareRight(id, userIds, departIds);
			if (rtd != null) {
				// 更新用户空间
				UserSpace us = userSpaceManager.getUserSpaceRigidly(Double.valueOf(uid));
//				userSpaceManager.updateGraph(rtd, Double.valueOf(0));
				userSpaceManager.updateGraph(g,rtd);

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
	public ToWeb getGraphPointInfoMapper(@RequestBody JSONObject uidToken, HttpServletRequest request)
			throws Exception {
		String uid = uidToken.getString("uid");
		String token = uidToken.getString("token");
		String graphID = uidToken.getString("graphID");
		Double id = null;
		// 认证+权限
		UserRight ur = new UserRight();
		ToWeb ret = authercation(Double.valueOf(uid), token, GlobalConsts.Operation_getRealTimeDataList, ur);
		if (!StringUtil.isBlank(ret.getStatus()) && ret.getStatus() != GlobalConsts.ResultCode_SUCCESS)
			return ret;

		try {
			id = Double.valueOf(graphID);
		} catch (Exception e) {
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
		Hashtable<String, Point> data = new Hashtable<String, Point>();
		for (int i = 0; i < txtids.size(); i++) {
			data.put(txtids.get(i), pl.get(i));
		}
		ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
		ret.setData("data", data);
		return ret;
	}

	@GetMapping("/upload")
	public String upload() {
		return "upload";
	}

	@PostMapping("/uploadThumbnail")
	@ResponseBody
	public ToWeb uploadThumbnail(@RequestParam("file") MultipartFile file) {
		ToWeb ret = ToWeb.buildResult();
		if (file.isEmpty()) {
			ret.setStatus(GlobalConsts.ResultCode_INVALIDATION);
			ret.setMsg("上传失败，请选择文件");
			return ret;
		}

		String fileName = file.getOriginalFilename();
		File dirFile = new File(graphServerPath);
		// 这句必须加上，解决不同操作系统文件名大小写区分问题。
		String filePath = null;
		try {
			filePath = dirFile.getCanonicalPath();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		fileName = String.valueOf(System.currentTimeMillis()) + new Random(3).toString() + fileName;
		File dest = new File(filePath + "\\" + fileName);
		try {
			file.transferTo(dest);
			ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
			ret.setMsg("上传成功");

			ret.putData("url", fileName);
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	@PostMapping("/uploadGraphFile")
	@ResponseBody
	public ToWeb uploadGraphFile(@RequestParam("file") MultipartFile file, @RequestParam("path") String path, 
			@RequestParam("url") String url, @RequestParam("name") String name, @RequestParam("desc") String desc,
			@RequestParam("uid") String uid,@RequestParam("token") String token) {
		System.out.println("path="+path);
		try {
			path = java.net.URLDecoder.decode(path,"UTF-8");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		// 认证+权限
		Double duid;
		try {
			duid = Double.valueOf(uid);
		}catch(Exception e) {
			ToWeb ret = ToWeb.buildResult();
			ret.setStatus(GlobalConsts.ResultCode_INVALIDATION);
			ret.setMsg("用户id需要是double类型。");
			return ret;
		}
		UserRight ur = new UserRight();
		ToWeb ret = authercation(duid, token, GlobalConsts.Operation_uploadGraphFile, ur);
		if (!StringUtil.isBlank(ret.getStatus()) && ret.getStatus() != GlobalConsts.ResultCode_SUCCESS)
			return ret;
		
		if (file.isEmpty()) {
			ret.setStatus(GlobalConsts.ResultCode_INVALIDATION);
			ret.setMsg("上传失败，请选择文件");
			return ret;
		}

		String fileName = file.getOriginalFilename();
		File dirFile = new File(graphPath);
		// 这句必须加上，解决不同操作系统文件名大小写区分问题。
		String filePath = null;
		try {
			filePath = dirFile.getCanonicalPath();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (!StringUtil.isBlank(path))
			filePath = path;
		File dest = new File(filePath + "\\" + fileName);
		try {
			file.transferTo(dest);
			Graph g = FileTool.parseFileToGraph(filePath, fileName,url,name,desc,uid);
			if (g == null) {
				if (dest.delete()) {
					System.out.println(file.getName() + " 文件已被删除！");
				} else {
					System.out.println("文件删除失败！");
				}
				ret.setStatus(GlobalConsts.ResultCode_INVALIDATION);
				ret.setMsg("上传失败，上传的文件不是有效的图形文件。");
				return ret;
			}
			graphManager.addOrUpdateGraphToTree(g);
			// 更新该用户的userSpace
			userSpaceManager.updateGraph(g, new Graph());
			ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
			ret.setMsg("上传成功");
			ret.setData("graph", g);
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}


	@PostMapping("/updateGraphInfo")
	@ResponseBody
	public ToWeb updateGraphInfo(@RequestParam("id") String id, 
			@RequestParam("url") String url, @RequestParam("name") String name, @RequestParam("desc") String desc,
			@RequestParam("uid") String uid,@RequestParam("token") String token) {
		// 认证+权限
		Double duid;
		try {
			duid = Double.valueOf(uid);
		}catch(Exception e) {
			ToWeb ret = ToWeb.buildResult();
			ret.setStatus(GlobalConsts.ResultCode_INVALIDATION);
			ret.setMsg("用户id需要是double类型。");
			return ret;
		}
		Double gid;
		try {
			gid = Double.valueOf(id);
		}catch(Exception e) {
			ToWeb ret = ToWeb.buildResult();
			ret.setStatus(GlobalConsts.ResultCode_INVALIDATION);
			ret.setMsg("图形id需要是double类型。");
			return ret;
		}
		UserRight ur = new UserRight();
		ToWeb ret = authercation(duid, token, GlobalConsts.Operation_updateGraphFile, ur);
		if (!StringUtil.isBlank(ret.getStatus()) && ret.getStatus() != GlobalConsts.ResultCode_SUCCESS)
			return ret;
		try {
			Graph g = graphManager.getGraphByKeys(gid);
			if (g == null) {
				ret.setStatus(GlobalConsts.ResultCode_INVALIDATION);
				ret.setMsg("指定id的图形不是有效的图形文件。");
				return ret;
			}
			g.setDesc(desc);
			if(!StringUtil.isBlank(url) && !url.trim().toLowerCase().contentEquals("null"))
				g.setImg(url);
			g.setNickName(name);
			g = graphManager.updateGraph(g);
			// 更改了设计，用户空间里只保存graphID，不再保存内容。只更新了图形的内容就可以了。
//			// TODO: 下面这个有问题
//			graphManager.addOrUpdateGraphToTree(g);
//			// 更新该用户的userSpace
//			UserSpace uold = userSpaceManager.getUserSpace(duid);
//			Graph gus = uold.getGraph();
//			gus.addOrUpdateChild(g);
			
			ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
			ret.setMsg("更新成功");
			ret.setData("graph", g);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	@PostMapping("/deleteGraphFile")
	@ResponseBody
	public ToWeb deleteGraphFile(@RequestBody JSONObject user, HttpServletRequest request) {
		Double uid = user.getDouble("uid");
		String token = user.getString("token");
		Double id = user.getDouble("id");
		// 认证+权限
		Double duid;
		try {
			duid = Double.valueOf(uid);
		}catch(Exception e) {
			ToWeb ret = ToWeb.buildResult();
			ret.setStatus(GlobalConsts.ResultCode_INVALIDATION);
			ret.setMsg("用户id需要是double类型。");
			return ret;
		}
		Double gid;
		try {
			gid = Double.valueOf(id);
		}catch(Exception e) {
			ToWeb ret = ToWeb.buildResult();
			ret.setStatus(GlobalConsts.ResultCode_INVALIDATION);
			ret.setMsg("图形id需要是double类型。");
			return ret;
		}
		UserRight ur = new UserRight();
		ToWeb ret = authercation(duid, token, GlobalConsts.Operation_deleteGraphFile, ur);
		if (!StringUtil.isBlank(ret.getStatus()) && ret.getStatus() != GlobalConsts.ResultCode_SUCCESS)
			return ret;
		try {
			Graph g = graphManager.getGraphByKeys(gid);
			if (g == null) {
				ret.setStatus(GlobalConsts.ResultCode_INVALIDATION);
				ret.setMsg("指定id的图形不是有效的图形文件。");
				return ret;
			}
			// 删除数据、文件及缓存
			graphManager.deleteGraph(g);
			// 更新用户空间
			userSpaceManager.updateGraph(null, g);

			ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
			ret.setMsg("删除成功");
			ret.setData("graph", g);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

}
