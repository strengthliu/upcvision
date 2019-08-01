package com.surpass.vision.service;

import java.util.List;

import com.surpass.vision.domain.RealTimeData;

public interface RealTimeDataService {

	List<RealTimeData> getRealTimeDataList(Integer uid);

	RealTimeData newRealTimeData(String type, String name, String owner, String creater, String shared, String points,
			String otherrule1, String otherrule2);

}
