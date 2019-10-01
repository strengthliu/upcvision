package com.surpass.vision.service.impl;

import java.util.List;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.surpass.vision.domain.FileList;
import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.mapper.PointGroupDataMapper;
import com.surpass.vision.service.PointGroupService;
import com.surpass.vision.tools.IDTools;

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
	public PointGroupData newPointGroupData(PointGroupData pgd) {
		if(pgd instanceof FileList) {
			PointGroupData pgd1 = new PointGroupData();
			pgd1.setId(pgd.getId());
			pgd1.setCreater(pgd.getCreater());
			pgd1.setName(pgd.getName());
			pgd1.setOtherrule1(pgd.getOtherrule1());
			pgd1.setOtherrule2(pgd.getOtherrule2());
			pgd1.setOwner(pgd.getOwner());
			pgd1.setPoints(pgd.getPoints());
			pgd1.setShared(pgd.getShared());
			pgd1.setType(pgd.getType());
			pgd = pgd1;
		}
		
		//如果有就更新，
		PointGroupData _pgd = pointGroupDataMapper.selectByPrimaryKey(pgd.getId());
		if(_pgd==null) {
			if(pgd.getId()==null || pgd.getId()==0 )
				pgd.setId(IDTools.newID());
			pointGroupDataMapper.insert(pgd);
		}
		else
			pointGroupDataMapper.updateByPrimaryKeySelective(pgd);
		return pgd;
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

	@Override
	public PointGroupData getPointGroupDataByName(String name) {
		return pointGroupDataMapper.selectByName(name);	

	}

	@Override
	public void updateByName(String owner, String creater, String shared, String points, String otherrule1,
			String otherrule2, String name) {
		pointGroupDataMapper.updateByName(owner, creater, shared, points, otherrule1,
				otherrule2, name);
	}

	@Override
	public PointGroupData getPointGroupDataByOtherRule1(String typeGraph, String wholePath) {
		return pointGroupDataMapper.selectByTypeAndOtherRule1(typeGraph,wholePath.trim());
	}

//	@Override
//	public PointGroupData getAlertDataByID(Double itemId) {
//		
//		return pointGroupDataMapper.selectByPrimaryKey(Double.valueOf(itemId));
//	}

}
