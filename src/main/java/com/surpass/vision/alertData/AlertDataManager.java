package com.surpass.vision.alertData;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

import com.surpass.vision.XYGraph.XYGraphManager;
import com.surpass.vision.domain.AlertData;
import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.domain.RealTimeData;
import com.surpass.vision.domain.User;
import com.surpass.vision.graph.GraphManager;
import com.surpass.vision.historyData.HistoryDataManager;
import com.surpass.vision.lineAlertData.LineAlertDataManager;
import com.surpass.vision.mapper.PointGroupDataMapper;
import com.surpass.vision.mapper.UserSpaceDataMapper;
import com.surpass.vision.realTimeData.RealTimeDataManager;
import com.surpass.vision.server.Point;
import com.surpass.vision.server.ServerManager;
import com.surpass.vision.service.PointGroupService;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.tools.IDTools;
import com.surpass.vision.user.UserManager;
import com.surpass.vision.userSpace.UserSpaceManager;

@Component
public class AlertDataManager {


	@Reference
	@Autowired
	RedisService redisService;


	UserManager userManager;
//	@Autowired
//	ServerManager serverManager;

	@Autowired
	PointGroupService pointGroupService;


	

	public Hashtable<String, AlertData> getAdminAlertDataHashtable() {
		Hashtable<String, AlertData> ret = new Hashtable<String, AlertData>();
		List<PointGroupData> pgdl = pointGroupService.getAdminAlertData();
		for (int i = 0; i < pgdl.size(); i++) {
			PointGroupData pgd = pgdl.get(i);
			AlertData alertData = new AlertData();

			alertData.setCreater(pgd.getCreater());
			alertData.setCreaterUser(userManager.getUserByID(pgd.getCreater()));

			alertData.setId(pgd.getId());
			alertData.setName(pgd.getName());
			alertData.setOtherrule1(pgd.getOtherrule1());
			alertData.setOtherrule2(pgd.getOtherrule2());

			alertData.setOwner(pgd.getOwner());
			alertData.setOwnerUser(userManager.getUserByID(pgd.getOwner()));

			alertData.setPoints(pgd.getPoints());
			ArrayList<Point> pal = new ArrayList<>();
			String[] pids = IDTools.splitID(pgd.getPoints());
			for (int ipids = 0; ipids < pids.length; ipids++) {
				Point p = ServerManager.getInstance().getPointByID(pids[ipids]);
				pal.add(p);
			}
			alertData.setPointList(pal);

			alertData.setShared(pgd.getShared());
			ArrayList<User> ul = new ArrayList<User>();
			String[] sharedIds = IDTools.splitID(pgd.getShared());
			for (int isharedIDs = 0; isharedIDs < sharedIds.length; isharedIDs++) {
				User u = userManager.getUserByID(sharedIds[isharedIDs]);
				ul.add(u);
			}
			alertData.setSharedUsers(ul);

			alertData.setType(pgd.getType());

			ret.put(IDTools.toString(pgd.getId()), alertData);
		}
		return ret;
	}

	/**
	 * 从缓存里取数据，如果没有，再调用服务。 
	 * @param graphs
	 * @return
	 */
	public Hashtable<String, AlertData> getAlertDataHashtableByKeys(String alertdata) {
		Hashtable<String, AlertData> ret = new Hashtable<String, AlertData> ();
		// 分隔key
		String [] keys = IDTools.splitID(alertdata);
		for(int ik = 0;ik<keys.length;ik++) {
			// 从缓存里取图
			AlertData g = (AlertData)redisService.get(keys[ik]);
			if(g == null) {
				// TODO: 如果没有, 从数据库里取
				
				// 再设置缓存
			}
			
			ret.put(IDTools.toString(g.getId()), g);
		}
		// 
		return ret;
	}



}
