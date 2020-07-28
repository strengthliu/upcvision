package com.surpass.vision.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Reference;
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
import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.common.Submit;
import com.surpass.vision.common.ToWeb;
import com.surpass.vision.domain.AlertData;
import com.surpass.vision.domain.DepartmentInfo;
import com.surpass.vision.domain.User;
import com.surpass.vision.domain.UserInfo;
import com.surpass.vision.domain.UserRight;
import com.surpass.vision.domain.UserSpace;
//import com.surpass.vision.domain.UserInfo;
import com.surpass.vision.service.LoginService;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.tools.IDTools;
import com.surpass.vision.tools.TokenTools;
import com.surpass.vision.user.UserManager;
import com.surpass.vision.userSpace.UserSpaceManager;

@RestController
//@RequestMapping("/v1")
public class AuthorcationController extends BaseController {
	@Reference
	@Autowired
	LoginService login;
	
	@Value("${upc.adminPassword}")
	private String adminPassword;
	@Value("${upc.adminName}")
	private String adminName;

	@Autowired
	UserSpaceManager userSpaceManager;
	
	@Autowired
	UserManager userManager;

	@Autowired
    private RedisService redisService;
	
//	@Value("${upc.graphPath}")
//	private String graphPath;

	@Value("${upc.graphServerPath}")
	private String graphServerPath;

    @Submit
    @RequestMapping(value = "login", method = { RequestMethod.POST, RequestMethod.GET })
	public  ToWeb login(@RequestBody JSONObject user,  HttpServletRequest request)
			throws Exception {
		ToWeb tw = ToWeb.buildResult();
		String uname = user.getString("uname");
		String pwd = user.getString("pwd");
		System.out.println(uname+" "+pwd);
		UserInfo ui = null;
		// 对admin进行特殊处理
		if(uname.contentEquals(adminName)) {
			if(pwd.contentEquals(adminPassword))
				ui = userSpaceManager.getAdminUserInfo();
			else {
				tw.setStatus(GlobalConsts.ResultCode_FAIL);
				tw.setMsg("用户名密码不正确");
				tw.setRedirectUrl("login.html");
				return tw;
			}
		}else {
			ui = userManager.getUserInfoByName(uname);
			System.out.println("ui.getRole() = "+ui.getRole());
			if(ui!=null && ui.getRole()!=GlobalConsts.UserRoleGuest)
				try {
					ui = login.VerificationAccount(uname, pwd); 
				}catch(Exception e) {
					tw.setStatus(GlobalConsts.ResultCode_FAIL);
					tw.setMsg("用户名密码不正确");
					tw.setRedirectUrl("login.html");
					return tw;
//					
//					tw.setStatus(GlobalConsts.ResultCode_AuthericationError);
//					tw.setMsg(e.toString());
//					return tw;
				}
		}
		if(ui==null) {
			tw.setStatus(GlobalConsts.ResultCode_FAIL);
			tw.setMsg("用户名密码不正确");
			tw.setRedirectUrl("login.html");
			return tw;
		}
		// 如果是管理员，直接构造返回。
		if(IDTools.toString(ui.getId()).contentEquals(GlobalConsts.UserAdminID) || ui.getRole()==GlobalConsts.UserRoleAdmin) {
			UserSpace us = null;
//			us = userSpaceManager.getAdminUserSpace(ui.getId());
//			us = userSpaceManager.getUserSpaceRigidly(ui.getId());
			us = userSpaceManager.buildUserSpace(ui.getId());
			tw.setStatus(GlobalConsts.ResultCode_SUCCESS);
			tw.setMsg("登录成功！");
			HashMap<String ,Object> hm = new HashMap<String ,Object>();
			hm.put("userSpace",us);
			//hm.put("userInfo", us.getUser());
			tw.setData(hm);
			return tw;
		} 

		// 返回UserSpace
		UserSpace us = userSpaceManager.getUserSpace(ui.getId());
		String token = "";
		// 如果用户是guest，就返回https://pan.baidu.com/share/init?surl=vG9RwIcag-XbW7FmOH3ncg
		if(us == null) {
			try {
				token = TokenTools.genToken(IDTools.toString(ui.getId()));
				us = userSpaceManager.buildUserSpace(ui.getId(), token);
			}catch(IllegalStateException e) {
				e.printStackTrace();
			}
		} else {
			// 重新建token
			if(ui.getRole() == GlobalConsts.UserRoleGuest) {
				token = us.getToken();
			} else {
				// 重新登录，如果不是guest，重建Token。
				token = TokenTools.genToken(IDTools.toString(ui.getId()));
			}
		}
		UserSpace usWithDep = null;
		usWithDep = userSpaceManager.getUserSpaceWithDepartData(ui.getId());

		usWithDep.setToken(token);
//		UserSpace uss = userSpaceManager.getUserSpace(ui.getId());
		us.setToken(token);
		userSpaceManager.setUserSpace(us);
		
		tw.setStatus(GlobalConsts.ResultCode_SUCCESS);
		tw.setMsg("登录成功！");
		HashMap<String ,Object> hm = new HashMap<String ,Object>();
		hm.put("userSpace",us);
		//hm.put("userInfo", us.getUser());
		tw.setData(hm);
		return tw;
	}

	
	@RequestMapping(value = "checkAuthorcation", method = { RequestMethod.POST, RequestMethod.GET })
	public  ToWeb checkAuthorcation(@RequestParam String uid, @RequestParam String token,  HttpServletRequest request)
			throws Exception {

		Double userId = null;
		try {
			userId = Double.valueOf(uid);
			if(userId == null || userId == 0) userId = Double.valueOf(0);
		}catch(Exception e) {
			userId = Double.valueOf(0);
		}
		return authercation(userId,token);
	}

	
	/**
	 * 获取用户列表，用于分享。
	 * @param uid
	 * @param token
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getUserList", method = { RequestMethod.POST, RequestMethod.GET })
	public  ToWeb getUserList(@RequestParam String uid, @RequestParam String token,  HttpServletRequest request)
			throws Exception {

		Double userId = null;
		try {
			userId = Double.valueOf(uid);
			if(userId == null || userId == 0) userId = Double.valueOf(0);
		}catch(Exception e) {
			userId = Double.valueOf(0);
		}
		ToWeb ret = authercation(userId,token);
		if(ret.getStatus()!= GlobalConsts.ResultCode_SUCCESS) return ret;
		
		HashMap<String,User> hus = userManager.getUserList();
		ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
		ret.putData("users", hus);
		return ret;
	}

	@RequestMapping(value = "getUserSpace", method = { RequestMethod.POST, RequestMethod.GET })
	public  ToWeb getUserSpace(@RequestParam String uid, @RequestParam String token,  HttpServletRequest request)
			throws Exception {

		Double userId = null;
		try {
			userId = Double.valueOf(uid);
			if(userId == null || userId == 0) userId = Double.valueOf(0);
		}catch(Exception e) {
			userId = Double.valueOf(0);
		}
		ToWeb ret = authercation(userId,token);
		if(ret.getStatus()!= GlobalConsts.ResultCode_SUCCESS) return ret;
		// 如果是管理员，直接构造返回。
		UserInfo ui = userManager.getUserInfoByID(uid);
		if(IDTools.toString(userId).contentEquals(GlobalConsts.UserAdminID) || ui.getRole()==GlobalConsts.UserRoleAdmin) {
			UserSpace us = null;
//			us = userSpaceManager.getAdminUserSpace(ui.getId());
			us = userSpaceManager.buildUserSpace(userId,token);
			ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
			ret.setMsg("登录成功！");
			HashMap<String ,Object> hm = new HashMap<String ,Object>();
			hm.put("userSpace",us);
			//hm.put("userInfo", us.getUser());
			ret.setData(hm);
			return ret;
		} 

		UserSpace us = userSpaceManager.getUserSpaceWithDepartData(userId);
		ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
		ret.setData("userSpace", us);
		return ret;
	}

	@RequestMapping(value = "getUserInfoList", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb getUserInfoList(@RequestBody JSONObject user, HttpServletRequest request) throws Exception {
		Double uid = user.getDouble("uid");
		String token = user.getString("token");
		// 认证+权限
		UserRight ur = new UserRight();
		ToWeb ret = authercation(uid, token, GlobalConsts.Operation_getUserInfo,ur);
		if (!StringUtil.isBlank(ret.getStatus()) && (!ret.getStatus().contentEquals(GlobalConsts.ResultCode_SUCCESS)))
			return ret;

		// 如果请求者是管理员，就返回全部用户信息，否则就只返回他自己的信息。
		List<UserInfo>  hus = null;
		UserInfo ui =null;
		if(IDTools.toString(uid).contentEquals(GlobalConsts.UserAdminID))
			ui = userSpaceManager.getAdminUserInfo();
		else
			ui = userManager.getUserInfoByID(IDTools.toString(uid));
		if(ui!=null && ui.getRole() == 1)
			hus = userManager.getUserInfoList();
		else {
			hus = new ArrayList<UserInfo>();
			hus.add(ui);
		}
			
		ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
		ret.putData("users", hus);
		return ret;
	}

	@RequestMapping(value = "delUser", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb delUser(@RequestBody JSONObject user, HttpServletRequest request) throws Exception {
		Double uid = user.getDouble("uid");
		String token = user.getString("token");
		ToWeb ret;
		String idstr = user.getString("id");
		if(StringUtil.isBlank(idstr)) {
			ret = ToWeb.buildResult();
			ret.setStatus(GlobalConsts.ResultCode_FAIL);
			ret.setMsg("参数不正确，要删除的用户ID不能为空。");
			return ret;
		}
		// 认证+权限
		UserRight ur = new UserRight();
		// 创建用户的权限与删除用户的权限一样
		ret = authercation(uid, token, GlobalConsts.Operation_createUser,ur); 
		if (!StringUtil.isBlank(ret.getStatus()) && (!ret.getStatus().contentEquals(GlobalConsts.ResultCode_SUCCESS))) {
			return ret;
		}
		
		boolean r1 = this.userSpaceManager.deleteUserSpace(Double.valueOf(idstr));
		if(!r1) {
			ret.setStatus(GlobalConsts.ResultCode_FAIL);	
			ret.setMsg("删除用户空间失败，该用户还拥有他所创建的内容。确定要删除该用户，请联系统系统管理员。");
		}
		boolean r2 = userManager.deleteUser(Double.valueOf(idstr));
		if(r2)
			ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
		else {
			ret.setStatus(GlobalConsts.ResultCode_FAIL);	
			ret.setMsg("删除用户信息失败，请联系系统管理员。");
		}
		ret.setData("data", idstr);
		ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
		ret.setMsg("成功");
		return ret;
	}

	@RequestMapping(value = "newUser", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb newUser(@RequestBody JSONObject user, HttpServletRequest request) throws Exception {
		Double uid = user.getDouble("uid");
		String token = user.getString("token");
		// 取出参数
	    String username = user.getString("username");
	    String name = user.getString("name");
	    String address = user.getString("address");
	    String depart = user.getString("depart");
	    String desc = user.getString("desc");
	    String mobile = user.getString("mobile");
	    String email = user.getString("email");
	    Integer role = user.getInteger("role");
	    String photo = user.getString("photo");
	    String pwd = user.getString("pwd");
	    if(StringUtil.isBlank(photo)) {
	    	photo = "../../images/faces/face1.jpg";
	    }
		// TODO: 检查参数合法性

	    ToWeb ret ;
		String idstr = user.getString("id");
		Double id = null ;
		if(StringUtil.isBlank(idstr)) {
			// 新建
			// 认证+权限
			UserRight ur = new UserRight();
			ret = authercation(uid, token, GlobalConsts.Operation_createUser,ur);
			if (!StringUtil.isBlank(ret.getStatus()) && (!ret.getStatus().contentEquals(GlobalConsts.ResultCode_SUCCESS)))
				return ret;
			id = IDTools.newID();		    

		}else {
			// 修改
			id = Double.valueOf(idstr);
			// 认证+权限
			UserInfo g = this.userManager.getUserInfoByID(idstr);
			UserRight ur = g.getRight(id);
			ret = authercation(uid, token, GlobalConsts.Operation_updateUserInfo,ur);
			if (!StringUtil.isBlank(ret.getStatus()) && (!ret.getStatus().contentEquals(GlobalConsts.ResultCode_SUCCESS)))
				return ret;
			if(g.getId().doubleValue() == 2) {
				ret.setStatus(GlobalConsts.ResultCode_INVALIDATION);
				ret.setMsg("超级管理员信息不能通过页面途径修改，请使用系统安全配置方法完成信息修改。");
			}
		}
		
		try {
			UserInfo rtd = new UserInfo();
			rtd.setAddress(address);
//			rtd.setAvailable(available);
			rtd.setDepart(depart);
			rtd.setDesc(desc);
			rtd.setEmail(email);
			rtd.setId(id);
			rtd.setMobile(mobile);
			rtd.setName(name);
			rtd.setPhoto(photo);
			rtd.setPwd(pwd);
			rtd.setRole(role);
			rtd.setUsername(username);
			rtd = userManager.createUser(rtd);
//		    UserInfo rtd = this.userManager.createUser(id,name,pwd,email,role,photo,mobile,depart,desc);
			
			if (rtd != null) {
				// 更新用户空间
				UserSpace us = userSpaceManager.getUserSpaceRigidly(Double.valueOf(uid));
				us.setUser(rtd);
				userSpaceManager.setUserSpace(us);
//				userSpaceManager.updateUserInfo(rtd,Double.valueOf(0));
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

	@PostMapping("/uploadPortrait")
	@ResponseBody
	public ToWeb uploadPortrait(@RequestParam("file") MultipartFile file) {
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


	@RequestMapping(value = "delDepartment", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb delDepartment(@RequestBody JSONObject user, HttpServletRequest request) throws Exception {
		Double uid = user.getDouble("uid");
		String token = user.getString("token");
		ToWeb ret;
		String idstr = user.getString("id");
		if(StringUtil.isBlank(idstr)) {
			ret = ToWeb.buildResult();
			ret.setStatus(GlobalConsts.ResultCode_FAIL);
			ret.setMsg("参数不正确，要删除的用户ID不能为空。");
			return ret;
		}
		// 认证+权限
		UserRight ur = new UserRight();
		// 创建用户的权限与删除用户的权限一样
		ret = authercation(uid, token, GlobalConsts.Operation_createDepartment,ur); 
		if (!StringUtil.isBlank(ret.getStatus()) && (!ret.getStatus().contentEquals(GlobalConsts.ResultCode_SUCCESS))) {
			return ret;
		}
		boolean r2 = userManager.deleteDepartment(Integer.valueOf(idstr));
		if(r2)
			ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
		else {
			ret.setStatus(GlobalConsts.ResultCode_FAIL);	
			ret.setMsg("删除用户信息失败，请联系系统管理员。");
		}
		ret.setData("data", idstr);
		ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
		ret.setMsg("成功");
		return ret;
	}

	static Integer maxDepartId = -1;
	Integer newDepartId() {
		if(maxDepartId < 0) {
			maxDepartId = userManager.getMaxDepartId();
			if(maxDepartId == null)
				maxDepartId = 0;
		}
		maxDepartId++;
		return maxDepartId;
	}
	
	@RequestMapping(value = "newDepartment", method = { RequestMethod.POST, RequestMethod.GET })
	public ToWeb newDepartment(@RequestBody JSONObject user, HttpServletRequest request) throws Exception {
		Double uid = user.getDouble("uid");
		String token = user.getString("token");
		// 取出参数
	    String departName = user.getString("departname");
	    String departDesc = user.getString("departdesc");
		// TODO: 检查参数合法性

	    ToWeb ret ;
		String idstr = user.getString("id");
		Integer id = null ;
		// 新建
		// 认证+权限
		UserRight ur = new UserRight();
		ret = authercation(uid, token, GlobalConsts.Operation_createUser,ur);
		if (!StringUtil.isBlank(ret.getStatus()) && (!ret.getStatus().contentEquals(GlobalConsts.ResultCode_SUCCESS)))
			return ret;
		if(StringUtil.isBlank(idstr)) {
			id = newDepartId();	
		}else {
			id = Integer.valueOf(idstr);
		}
		System.out.println("current -> "+new Date().toString());
		try {
			DepartmentInfo rtd = new DepartmentInfo();
			rtd.setDepartname(departName);
			rtd.setDepartdesc(departDesc);
			rtd.setId(id);
			rtd = userManager.createDepartment(rtd);;
			ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
			ret.setMsg("成功");
			ret.setData("data",rtd);
			ret.setRefresh(true);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			ret.setStatus(GlobalConsts.ResultCode_AuthericationError);
			ret.setMsg("异常失败");
			return ret;
		}
	}

	
	/**
	 * 获取部门列表，用于分享。
	 * @param uid
	 * @param token
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getDepartList", method = { RequestMethod.POST, RequestMethod.GET })
	public  ToWeb getDepartList(@RequestBody JSONObject user,  HttpServletRequest request)
			throws Exception {
		Double uid = user.getDouble("uid");
		String token = user.getString("token");

		Double userId = null;
		try {
			userId = Double.valueOf(uid);
			if(userId == null || userId == 0) userId = Double.valueOf(0);
		}catch(Exception e) {
			userId = Double.valueOf(0);
		}
//		ToWeb ret = authercation(userId,token);
		ToWeb ret = ToWeb.buildResult();
		ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
		if(ret.getStatus()!= GlobalConsts.ResultCode_SUCCESS) return ret;
		
		List<DepartmentInfo> dl = userManager.getDepartList();
		ret.setStatus(GlobalConsts.ResultCode_SUCCESS);
		ret.putData("departs", dl);
		return ret;
	}

}
