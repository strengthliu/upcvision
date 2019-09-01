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
	

	@Override
	public List<PointGroupData> getAdminGraphData() {
		return pointGroupDataMapper.getAdminGraphData();	
	}

	@Async("taskExecutor")
	@Override
	public Object newPointGroupData(PointGroupData pgd) {
		//如果有就更新，
		PointGroupData _pgd = pointGroupDataMapper.selectByPrimaryKey(pgd.getId());
		if(_pgd==null)
			pointGroupDataMapper.insert(pgd);
		else
			pointGroupDataMapper.updateByPrimaryKey(pgd);
		return null;
	}

	@Async("taskExecutor")
	@Override
	public void deletePointGroupItem(Double id) {
		pointGroupDataMapper.deleteByPrimaryKey(Double.valueOf(id));
		
	}


	@Override
	public PointGroupData getPointGroupDataByID(Double itemId) {
		
		return pointGroupDataMapper.selectByPrimaryKey(Double.valueOf(itemId));
	}

	@Async("taskExecutor")
	@Override
	public void updatePointGroupItem(PointGroupData pgd) {
		PointGroupData pgdt = pointGroupDataMapper.selectByPrimaryKey(pgd.getId());
		if(pgdt != null)
			pointGroupDataMapper.updateByPrimaryKeySelective(pgd);
		else
			pointGroupDataMapper.insert(pgd);
	}

//	@Override
//	public PointGroupData getAlertDataByID(Double itemId) {
//		
//		return pointGroupDataMapper.selectByPrimaryKey(Double.valueOf(itemId));
//	}

}
