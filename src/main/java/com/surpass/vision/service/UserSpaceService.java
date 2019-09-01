package com.surpass.vision.service;

import java.util.ArrayList;
import java.util.List;

import com.surpass.vision.domain.RealTimeData;
import com.surpass.vision.domain.UserSpace;
import com.surpass.vision.domain.UserSpaceData;

public interface UserSpaceService {

	/**
	 * 更新一组实时数据
	 * @param rtdl
	 */
	void updateRealTimeDataList(ArrayList<RealTimeData> rtdl);

	/**
	 * 更新实时数据
	 * @param rtd
	 */
	void updateRealTimeDataList(RealTimeData rtd);

	UserSpaceData getUserSpaceById(Double userID);

	void updateUserSpace(Double uid, UserSpace us);



}
