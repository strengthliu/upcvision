package com.surpass.vision.service.impl;

import java.util.Hashtable;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Service;

import com.surpass.vision.XYGraph.XYGraphManager;
import com.surpass.vision.alertData.AlertDataManager;
import com.surpass.vision.domain.AlertData;
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
//	@Autowired
//	UserSpaceDataMapper userSpaceDataMapper;
//	@Autowired
//	GraphDataManager graphDataManager;

//	@Reference
//	@Autowired
//	RedisService redisService;


	@Override
	public boolean newUser(UserInfo user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delUser(Integer userID) {
		// TODO Auto-generated method stub
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

}
