package com.surpass.vision.service;

import java.util.ArrayList;
import java.util.List;

import com.surpass.vision.domain.RealTimeData;
import com.surpass.vision.domain.UserSpace;
import com.surpass.vision.domain.UserSpaceData;

public interface UserSpaceService {

//	UserSpaceData getUserSpaceById(Double userID);
	public UserSpaceData getUserSpaceById(Double userID);
	void updateUserSpace(Double uid, UserSpaceData us);
	public boolean deleteUserSpace(Double uid);



}
