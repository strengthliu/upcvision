package com.surpass.vision.service.impl;

import java.util.Hashtable;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.surpass.vision.XYGraph.XYGraphManager;
import com.surpass.vision.alertData.AlertDataManager;
import com.surpass.vision.domain.AlertData;
import com.surpass.vision.domain.DepartmentInfo;
import com.surpass.vision.domain.FileList;
import com.surpass.vision.domain.Graph;
import com.surpass.vision.domain.HistoryData;
import com.surpass.vision.domain.LineAlertData;
import com.surpass.vision.domain.RealTimeData;
import com.surpass.vision.domain.UserInfo;
import com.surpass.vision.domain.UserRight;
import com.surpass.vision.domain.UserSpace;
import com.surpass.vision.domain.UserSpaceData;
import com.surpass.vision.domain.XYGraph;
import com.surpass.vision.graph.GraphDataManager;
import com.surpass.vision.graph.GraphManager;
import com.surpass.vision.historyData.HistoryDataManager;
import com.surpass.vision.lineAlertData.LineAlertDataManager;
import com.surpass.vision.mapper.DepartmentInfoMapper;
import com.surpass.vision.mapper.UserInfoMapper;
import com.surpass.vision.mapper.UserSpaceDataMapper;
import com.surpass.vision.realTimeData.RealTimeDataManager;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.service.UserService;
import com.surpass.vision.tools.TokenTools;
import com.surpass.vision.userSpace.UserSpaceManager;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserInfoMapper userMapper;
	
	@Autowired
	DepartmentInfoMapper departmentInfoMapper;
//	@Autowired
//	UserSpaceDataMapper userSpaceDataMapper;
//	@Autowired
//	GraphDataManager graphDataManager;

//	@Reference
//	@Autowired
//	RedisService redisService;


//	@Async("taskExecutor")
	@Override
	public boolean delUser(Double userID) {
		int r = userMapper.deleteByPrimaryKey(userID);
		if(r>0)
			return true;
		else
			return false;
	}

	@Override
	public UserInfo getUserById(Double userID) {
		return userMapper.selectByPrimaryKey(userID);
	}

	@Override
	public List<UserInfo> getAllUsers() {
		return userMapper.selectAdminUserInfo();
	}

	
	@Async("taskExecutor")
	@Override
	public void createOrUpdateUser(UserInfo ui) {
		UserInfo ut = userMapper.selectByPrimaryKey(ui.getId());
		if(ut==null) {
			userMapper.insert(ui);
		}else {
			userMapper.updateByPrimaryKeySelective(ui);
		}
	}

	@Override
	public List<DepartmentInfo> getDepartmentList() {
		return departmentInfoMapper.selectAllDepartment();
	}

	
	@Override
	public void createOrUpdateDepartmentInfo(DepartmentInfo di) {
		System.out.println("UserService.createOrUpateDepartmentInfo ->"+System.currentTimeMillis());
		DepartmentInfo dt = departmentInfoMapper.selectByPrimaryKey(di.getId());
		if(dt==null) {
			departmentInfoMapper.insert(di);
		}else {
			departmentInfoMapper.updateByPrimaryKey(di);
		}
	}

	@Override
	public void delDepartmentInfo(Integer deptID) {
		departmentInfoMapper.deleteByPrimaryKey(deptID);
	}

	@Override
	public Integer getMaxDepartId() {
		return departmentInfoMapper.selectMaxId();
	}

	@Override
	public DepartmentInfo getDepartById(Integer depID) {
		return departmentInfoMapper.selectByPrimaryKey(depID);
	}

	@Override
	public UserInfo getUserByName(String name) {
		List<UserInfo> uil =userMapper.selectByName(name);
		if(uil == null || uil.size()==0) {
			return null;
		}
		if(uil.size()>1)
			throw new IllegalStateException("有多个同名的用户。");
		
		return uil.get(0);
	}

}
