package com.surpass.vision.service.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.surpass.vision.domain.RealTimeData;
import com.surpass.vision.domain.UserSpace;
import com.surpass.vision.domain.UserSpaceData;
import com.surpass.vision.mapper.PointGroupDataMapper;
import com.surpass.vision.mapper.UserSpaceDataMapper;
import com.surpass.vision.service.UserService;
import com.surpass.vision.service.UserSpaceService;
import com.surpass.vision.userSpace.UserSpaceManager;

@Service
public class UserSpaceServiceImpl implements UserSpaceService {
	/**
	 * 用户空间服务
	 * ---------------------------------------
	 * @author 刘强 2019年7月29日 上午8:16:32 
	 */

	
	@Autowired
	UserSpaceDataMapper userSpaceDataMapper;
	

	/**
	 * 数据库mapper。
	 * ---------------------------------------
	 * @author 刘强 2019年7月29日 上午8:17:17 
	 */
	@Autowired
	UserSpaceDataMapper usdMapper;



	@Override
	public UserSpaceData getUserSpaceById(Double userID) {
		return userSpaceDataMapper.selectByPrimaryKey(userID);
	}

	@Async("taskExecutor")
	@Override
	public void updateUserSpace(Double uid, UserSpaceData us) {
		UserSpaceData usdm = us;
		UserSpaceData usd = usdMapper.selectByPrimaryKey(uid);
		if(usd == null) { // 插入
			usdMapper.insert(usdm);
		} else {
			usdMapper.updateByPrimaryKeySelective(usdm);
		}
		
	}

	@Override
	public boolean deleteUserSpace(Double uid) {
		int i = usdMapper.deleteByPrimaryKey(uid);
		if(i==1) return true;
		else return false;
	}

}
