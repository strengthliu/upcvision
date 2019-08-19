package com.surpass.vision.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.mapper.PointGroupDataMapper;
import com.surpass.vision.service.PointGroupService;

@Service
public class PointGroupServiceImpl implements PointGroupService {
	@Autowired
	PointGroupDataMapper pointGroupDataMapper;

	@Override
	public List<PointGroupData> getAdminXYGraph() {
		return pointGroupDataMapper.getAdminXYGraph();
	}

	@Override
	public List<PointGroupData> getAdminRealTimeData() {
		return pointGroupDataMapper.getAdminRealTimeData();
	}

	@Override
	public List<PointGroupData> getAdminAlertData() {
		return pointGroupDataMapper.getAdminAlertData();	
	}

	@Override
	public List<PointGroupData> getAdminHistoryData() {
		return pointGroupDataMapper.getAdminHistoryData();	
	}

	@Override
	public List<PointGroupData> getAdminLineAlertData() {
		return pointGroupDataMapper.getAdminLineAlertData();	
	}
	
	@Async("taskExecutor")
	@Override
	public Object newPointGroupData(PointGroupData pgd) {
		pointGroupDataMapper.insert(pgd);
		return null;
	}

	@Async("taskExecutor")
	@Override
	public void deletePointGroupItem(Double id) {
		pointGroupDataMapper.deleteByPrimaryKey(Double.valueOf(id));
		
	}

}
