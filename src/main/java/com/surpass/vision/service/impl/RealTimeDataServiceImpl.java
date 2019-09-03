package com.surpass.vision.service.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.surpass.vision.XYGraph.XYGraphManager;
import com.surpass.vision.alertData.AlertDataManager;
import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.domain.RealTimeData;
import com.surpass.vision.domain.User;
import com.surpass.vision.domain.UserSpace;
import com.surpass.vision.domain.UserSpaceData;
import com.surpass.vision.graph.GraphManager;
import com.surpass.vision.historyData.HistoryDataManager;
import com.surpass.vision.lineAlertData.LineAlertDataManager;
import com.surpass.vision.mapper.PointGroupDataMapper;
import com.surpass.vision.mapper.UserSpaceDataMapper;
import com.surpass.vision.pointGroup.PointGroupDataManager;
import com.surpass.vision.realTimeData.RealTimeDataManager;
import com.surpass.vision.server.Point;
import com.surpass.vision.server.ServerManager;
import com.surpass.vision.service.RealTimeDataService;
import com.surpass.vision.service.UserSpaceService;
import com.surpass.vision.tools.IDTools;
import com.surpass.vision.user.UserManager;
import com.surpass.vision.userSpace.UserSpaceManager;

import io.netty.util.internal.StringUtil;

@Service
public class RealTimeDataServiceImpl implements RealTimeDataService {

	@Autowired
	UserManager userManager;

	@Autowired
	UserSpaceDataMapper userSpaceDataMapper;

	/**
	 * 数据库mapper。 ---------------------------------------
	 * 
	 * @author 刘强 2019年7月29日 上午8:17:17
	 */
	// @Reference
	@Autowired
	PointGroupDataMapper pgdMapper;

//	@Override
//	public List<RealTimeData> getRealTimeDataList(Double uid) {
//		// TODO: 考虑一下设计是否合理。因为这里使用了uerSpaceManager，是否应该在uerSpaceManager中实现。
//		// 从用户空间里取
////		List<RealTimeData> ret = userSpaceManager.getRealTimeData(uid);
//		// 如果没有
//		//if (ret == null) 
//
//			// 从数据库取
//			UserSpaceData usd = userSpaceDataMapper.selectByPrimaryKey(uid);
//			String srtd = usd.getRealtimedata();
//			if(StringUtil.isNullOrEmpty(srtd)) srtd = "";
//			String[] rtdIds = IDTools.splitID(srtd);
//			ArrayList<RealTimeData> rtdl = new ArrayList<RealTimeData>();
//			for (int i = 0; i < rtdIds.length; i++) {
//				PointGroupData pgd = pgdMapper.selectByPrimaryKey(Double.parseDouble(rtdIds[i]));
//				if (pgd != null) {
//					RealTimeData realTimeData = generateRealTimeDataFromPointGroupData(pgd);
//					rtdl.add(realTimeData);
//				}
//			}
//
////			// 更新用户空间
////			uss.updateRealTimeDataList(rtdl);
//
//
//		// 返回
//		return rtdl;
//	}
//
//	@Async("taskExecutor")
//	@Override
//	public RealTimeData newRealTimeData(String type, String name, String owner, String creater, String shared,
//			String points, String otherrule1, String otherrule2) {
//		// 构建对象
//		PointGroupData pg = new PointGroupData();
//		pg.setCreater(creater);
//		if (owner == null || owner == "")
//			owner = creater;
//		pg.setOwner(owner);
//		pg.setCreater(creater);
//		pg.setShared(shared);
//
//		pg.setName(name);
//		pg.setPoints(points);
//		pg.setOtherrule1(otherrule1);
//		pg.setOtherrule2(otherrule2);
//		// 写数据库
//		
//		double id = pgdMapper.insert(pg);
//		pg.setId(Double.valueOf(id));
//		RealTimeData rtd = generateRealTimeDataFromPointGroupData(pg);
////		// 更新用户空间
////		uss.updateRealTimeDataList(rtd);
//
//		// 返回
//		return rtd;
//	}
	
public RealTimeData generateRealTimeDataFromPointGroupData(PointGroupData pgd) {
	if(pgd==null) return null;
	RealTimeData realTimeData = new RealTimeData();

	realTimeData.setCreater(pgd.getCreater());
	realTimeData.setCreaterUser(userManager.getUserByID(pgd.getCreater()));

	realTimeData.setId(pgd.getId());
	realTimeData.setName(pgd.getName());
	realTimeData.setOtherrule1(pgd.getOtherrule1());
	realTimeData.setOtherrule2(pgd.getOtherrule2());

	realTimeData.setOwner(pgd.getOwner());
	realTimeData.setOwnerUser(userManager.getUserByID(pgd.getOwner()));

	realTimeData.setPoints(pgd.getPoints());
	ArrayList<Point> pal = new ArrayList<>();
	String[] pids = IDTools.splitID(pgd.getPoints());
	for (int ipids = 0; ipids < pids.length; ipids++) {
		String serverName = PointGroupDataManager.splitPointName(pids[ipids]);
		String pName = PointGroupDataManager.splitPointName(pids[ipids]);
		Point p = ServerManager.getInstance().getPointByID(serverName,pName);
		pal.add(p);
	}
	realTimeData.setPointList(pal);

	realTimeData.setShared(pgd.getShared());
	ArrayList<User> ul = new ArrayList<User>();
	String[] sharedIds = IDTools.splitID(pgd.getShared());
	for (int isharedIDs = 0; isharedIDs < sharedIds.length; isharedIDs++) {
		User u = userManager.getUserByID(sharedIds[isharedIDs]);
		ul.add(u);
	}
	realTimeData.setSharedUsers(ul);

	realTimeData.setType(pgd.getType());
	
	return realTimeData;
}
}
