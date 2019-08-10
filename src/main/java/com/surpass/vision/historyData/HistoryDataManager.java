package com.surpass.vision.historyData;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

import com.surpass.vision.XYGraph.XYGraphManager;
import com.surpass.vision.alertData.AlertDataManager;
import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.domain.AlertData;
import com.surpass.vision.domain.HistoryData;
import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.domain.User;
import com.surpass.vision.graph.GraphManager;
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
public class HistoryDataManager {

	@Reference
	@Autowired
	RedisService redisService;


	@Autowired
	UserManager userManager;
//	@Autowired
//	ServerManager serverManager;

	@Autowired
	PointGroupService pointGroupService;

	

	public Hashtable<String, HistoryData> getAdminHistoryDataHashtable() {

		Hashtable<String, HistoryData> ret = new Hashtable<String, HistoryData>();
		List<PointGroupData> pgdl = pointGroupService.getAdminHistoryData();
		for (int i = 0; i < pgdl.size(); i++) {
			PointGroupData pgd = pgdl.get(i);
			HistoryData historyData = new HistoryData();

			historyData.setCreater(pgd.getCreater());
			historyData.setCreaterUser(userManager.getUserByID(pgd.getCreater()));

			historyData.setId(pgd.getId());
			historyData.setName(pgd.getName());
			historyData.setOtherrule1(pgd.getOtherrule1());
			historyData.setOtherrule2(pgd.getOtherrule2());

			historyData.setOwner(pgd.getOwner());
			historyData.setOwnerUser(userManager.getUserByID(pgd.getOwner()));

			historyData.setPoints(pgd.getPoints());
			ArrayList<Point> pal = new ArrayList<>();
			String[] pids = IDTools.splitID(pgd.getPoints());
			for (int ipids = 0; ipids < pids.length; ipids++) {
				Point p = ServerManager.getInstance().getPointByID(pids[ipids]);
				pal.add(p);
			}
			historyData.setPointList(pal);

			historyData.setShared(pgd.getShared());
			ArrayList<User> ul = new ArrayList<User>();
			String[] sharedIds = IDTools.splitID(pgd.getShared());
			for (int isharedIDs = 0; isharedIDs < sharedIds.length; isharedIDs++) {
				User u = userManager.getUserByID(sharedIds[isharedIDs]);
				ul.add(u);
			}
			historyData.setSharedUsers(ul);

			historyData.setType(pgd.getType());

			ret.put(pgd.getId().toString(), historyData);
		}
		return ret;
	}


	/**
	 * 从缓存里取数据，如果没有，再调用服务。 
	 * @param graphs
	 * @return
	 */
	public Hashtable<String, HistoryData> getHistoryDataHashtableByKeys(String historydata) {
		Hashtable<String, HistoryData> ret = new Hashtable<String, HistoryData> ();
		// 分隔key
		String [] keys = IDTools.splitID(historydata);
		for(int ik = 0;ik<keys.length;ik++) {
			// 从缓存里取图
			HistoryData g = (HistoryData)redisService.get(GlobalConsts.Key_HistoryData_pre_+keys[ik]);
			if(g == null) {
				// TODO: 如果没有, 从数据库里取
				
				// 再设置缓存
			}
			
			ret.put(g.getName(), g);
		}
		// 
		return ret;
	}


}
