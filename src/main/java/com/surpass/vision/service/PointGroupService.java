package com.surpass.vision.service;

import java.util.List;

import com.surpass.vision.domain.PointGroupData;

public interface PointGroupService {

	public List<PointGroupData> getAdminXYGraph();

	public List<PointGroupData> getAdminRealTimeData();

	public List<PointGroupData> getAdminAlertData();

	public List<PointGroupData> getAdminHistoryData();

	public List<PointGroupData> getAdminLineAlertData();

}
