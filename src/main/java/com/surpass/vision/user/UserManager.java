package com.surpass.vision.user;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.domain.User;
import com.surpass.vision.domain.UserInfo;
import com.surpass.vision.domain.UserRight;
import com.surpass.vision.mapper.UserInfoMapper;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.service.UserService;
import com.surpass.vision.tools.IDTools;

@Component
public class UserManager {

	@Reference
	@Autowired
	RedisService redisService;

	@Autowired
	UserService userService;

	public User getUserByID(String userId) {
		UserInfo ui = getUserInfoByID(userId);
		User u = new User();
		if (ui == null)
			return u;
		u.setId(ui.getId());
		u.setName(ui.getName());
		return u;
	}

	public UserInfo getUserInfoByID(String userId) {
		UserInfo ui = (UserInfo) redisService.get(GlobalConsts.Key_UserInfo_Pre_ + IDTools.toString(userId));
		if(ui!=null&&(ui.getId()==0||ui.getId()==null)) {
			System.out.println("什么鬼！！！");
		}
		if (ui != null)
			return ui;
		else {
			// 到数据库里取
			ui = userService.getUserById(Double.valueOf(userId));
			if (ui != null) {
				setUserInfo(ui);
				return ui;
			}
		}
		return null;
	}

	public void setUserInfo(UserInfo user) {
		redisService.set(GlobalConsts.Key_UserInfo_Pre_ + IDTools.toString(user.getId()), user);
	}

	// 初始化用户信息到缓存
	public void init() {
		List<UserInfo> users = userService.getAllUsers();
		for (int i = 0; i < users.size(); i++) {
			setUserInfo(users.get(i));
		}
		redisService.set(GlobalConsts.Key_UserInfo_Pre_ + "all", users);
	}

	public boolean hasRight(Integer role, String operation, UserRight ur) {
		// 如果是管理员
		if (role == 1)
			return true;

		// TODO 现在权限是按简单角色定义的，未来扩展时，从这里修改
		switch (operation) {
		case GlobalConsts.Operation_createRealTimeData:
			if (ur.canCreate(role))
				return true;
			else
				return false;
		case GlobalConsts.Operation_updateRealTimeData:
			if (ur.canUpdate(role))
				return true;
			else
				return false;
		case GlobalConsts.Operation_getRealTimeDataList:
			return true;

		case GlobalConsts.Operation_createLineAlertData:
			if (ur.canCreate(role))
				return true;
			else
				return false;
		case GlobalConsts.Operation_updateLineAlertData:
			if (ur.canUpdate(role))
				return true;
			else
				return false;
		case GlobalConsts.Operation_getAlertDataList:
			return true;
		case GlobalConsts.Operation_createAlertData:
			if (ur.canCreate(role))
				return true;
			else
				return false;
		case GlobalConsts.Operation_updateAlertData:
			if (ur.canUpdate(role))
				return true;
			else
				return false;
		case GlobalConsts.Operation_getHistoryDataList:
			return true;
		case GlobalConsts.Operation_createHistoryData:
			if (ur.canCreate(role))
				return true;
			else
				return false;
		case GlobalConsts.Operation_updateHistoryData:
			if (ur.canUpdate(role))
				return true;
			else
				return false;
		case GlobalConsts.Operation_getLineAlertDataList:
			return true;
		case GlobalConsts.Operation_getXYGraphList:
			return true;
		case GlobalConsts.Operation_createXYGraph:
			if (ur.canCreate(role))
				return true;
			else
				return false;
		case GlobalConsts.Operation_updateXYGraph:
			if (ur.canUpdate(role))
				return true;
			else
				return false;
		case GlobalConsts.Operation_getGraphList:
			return true;
		case GlobalConsts.Operation_shareGraph:
			if (ur.canUpdate(role))
				return true;
			else
				return false;
		}
		return false;
	}

	public HashMap<String, User> getUserList() {
		HashMap<String, User> ret = new HashMap<String, User>();
		List<UserInfo> users = (List<UserInfo>) redisService.get(GlobalConsts.Key_UserInfo_Pre_ + "all");
		if (users == null || users.size() == 0) {
			init();
			return getUserList();
		}
		for (int i = 0; i < users.size(); i++) {
			User u = new User();
			UserInfo ui = users.get(i);
			u.setId(ui.getId());
			u.setName(ui.getName());
			ret.put(IDTools.toString(u.getId()), u);
		}
		return ret;
	}

	public List<UserInfo> getUserInfoList() {
		List<UserInfo> users = (List<UserInfo>) redisService.get(GlobalConsts.Key_UserInfo_Pre_ + "all");
		if (users == null || users.size() == 0) {
			init();
			return getUserInfoList();
		}
		return users;
	}

	public UserInfo createUser(Double id, String name, String pwd, String email, Integer role, String photo,
			String mobile, String depart, String desc) {
		UserInfo ui = new UserInfo();
		if(id==0||id==null)
			id=IDTools.newID();
		ui.setId(id);
		ui.setName(name);
		ui.setPwd(pwd);
		ui.setEmail(email);
		ui.setRole(role);
		ui.setPhoto(photo);
		ui.setMobile(mobile);
		ui.setDesc(desc);
//		ui.setDepart(depart);
		userService.createOrUpdateUser(ui);
		this.setUserInfo(ui);
		List<UserInfo> ul = getUserInfoList();
		for (int i = 0; i < ul.size(); i++) {
			UserInfo u = ul.get(i);
			if (u.getId() == ui.getId()) {
				ul.set(i, ui);
				redisService.set(GlobalConsts.Key_UserInfo_Pre_ + "all", ul);
				return ui;
			}
		}
		ul.add(ui);
		redisService.set(GlobalConsts.Key_UserInfo_Pre_ + "all", ul);
		return ui;
	}

	public boolean deleteUser(Double userID) {
		try {
//			UserSpaceManager.
			// 删除用户数据库记录
			userService.delUser(userID);
			// 删除缓存
			List<UserInfo> lu = getUserInfoList();
			UserInfo du = getUserInfoByID(IDTools.toString(userID));
			for (int i = 0; i < lu.size(); i++) {
				System.out.println(IDTools.toString(lu.get(i).getId())+"  == "+IDTools.toString(du.getId()));
				if (lu.get(i).getId().doubleValue() == du.getId().doubleValue()) {
					lu.remove(i);
					break;
				}
			}
			redisService.set(GlobalConsts.Key_UserInfo_Pre_ + "all", lu);
			// 删除用户
			// 删除用户信息
			redisService.delete(GlobalConsts.Key_UserInfo_Pre_ + IDTools.toString(userID));
			// 删除用户空间
			redisService.delete(GlobalConsts.Key_UserSpace_pre_ + IDTools.toString(userID));

			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
