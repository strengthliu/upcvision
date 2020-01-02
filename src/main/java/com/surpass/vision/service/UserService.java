package com.surpass.vision.service;

import java.util.List;

import com.surpass.vision.domain.DepartmentInfo;
import com.surpass.vision.domain.UserInfo;

public interface UserService {
	
	// 删除用户
	public boolean delUser(Double userID);
	// 授权
	//UserSpace buildUserSpace(Integer userID, String... token);

	public UserInfo getUserById(Double userID);
	
	public DepartmentInfo getDepartById(Integer depID);

	public List<UserInfo> getAllUsers();

	public void createOrUpdateUser(UserInfo ui);

	public List<DepartmentInfo> getDepartmentList();
	public void createOrUpdateDepartmentInfo(DepartmentInfo ui);
	// 删除部门
	public void delDepartmentInfo(Integer deptID);

	public Integer getMaxDepartId();

	public UserInfo getUserByName(String uname);
}
