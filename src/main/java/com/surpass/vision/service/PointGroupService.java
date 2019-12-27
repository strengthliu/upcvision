package com.surpass.vision.service;

import java.util.List;

import com.surpass.vision.domain.PointGroupData;

public interface PointGroupService {

	public List<PointGroupData> getAdminPointGroupData(String type);

	public List<PointGroupData> getAdminXYGraph();

	public List<PointGroupData> getAdminRealTimeData();

	public List<PointGroupData> getAdminAlertData();

	public List<PointGroupData> getAdminHistoryData();

	public List<PointGroupData> getAdminLineAlertData();
	
	public List<PointGroupData> getAdminGraphData();

	public PointGroupData newPointGroupData(PointGroupData pgd);

	public void deletePointGroupItem(Double id);

	public void updatePointGroupItem(PointGroupData pgd);

	public PointGroupData getPointGroupDataByID(Double itemId);

	public PointGroupData getPointGroupDataByName(String string);

	public void updateByName(String owner, String creater, String shared, String points, String otherrule1,
			String otherrule2, String otherrule3, String name);

	public PointGroupData getPointGroupDataByOtherRule1(String typeGraph, String wholePath);


}
