package com.surpass.vision.lineAlertData;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

import com.surpass.vision.XYGraph.XYGraphManager;
import com.surpass.vision.alertData.AlertDataManager;
import com.surpass.vision.domain.HistoryData;
import com.surpass.vision.domain.LineAlertData;
import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.domain.User;
import com.surpass.vision.graph.GraphManager;
import com.surpass.vision.historyData.HistoryDataManager;
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
public class LineAlertDataManager {


	@Reference
	@Autowired
	RedisService redisService;

	@Autowired
	UserManager userManager;
//	@Autowired
//	ServerManager serverManager;

	@Autowired
	PointGroupService pointGroupService;


	

	public Hashtable<String, LineAlertData> getAdminLineAlertDataHashtable() {
		Hashtable<String, LineAlertData> ret = new Hashtable<String, LineAlertData>();
		List<PointGroupData> pgdl = pointGroupService.getAdminLineAlertData();
		for (int i = 0; i < pgdl.size(); i++) {
			PointGroupData pgd = pgdl.get(i);
			LineAlertData lineAlertData = new LineAlertData();

			lineAlertData.setCreater(pgd.getCreater());
			lineAlertData.setCreater(userManager.getUserByID(pgd.getCreater()));

			lineAlertData.setId(pgd.getId());
			lineAlertData.setName(pgd.getName());
			lineAlertData.setOtherrule1(pgd.getOtherrule1());
			lineAlertData.setOtherrule2(pgd.getOtherrule2());

			lineAlertData.setOwner(pgd.getOwner());
			lineAlertData.setOwner(userManager.getUserByID(pgd.getOwner()));

			lineAlertData.setPoints(pgd.getPoints());
			ArrayList<Point> pal = new ArrayList<>();
			String[] pids = IDTools.splitID(pgd.getPoints());
			for (int ipids = 0; ipids < pids.length; ipids++) {
				Point p = ServerManager.getInstance().getPointByID(pids[ipids]);
				pal.add(p);
			}
			lineAlertData.setPoints(pal);

			lineAlertData.setShared(pgd.getShared());
			ArrayList<User> ul = new ArrayList<User>();
			String[] sharedIds = IDTools.splitID(pgd.getShared());
			for (int isharedIDs = 0; isharedIDs < sharedIds.length; isharedIDs++) {
				User u = userManager.getUserByID(sharedIds[isharedIDs]);
				ul.add(u);
			}
			lineAlertData.setShared(ul);

			lineAlertData.setType(pgd.getType());

			ret.put(pgd.getId().toString(), lineAlertData);
		}
		return ret;
	}


	/**
	 * 从缓存里取数据，如果没有，再调用服务。 
	 * @param graphs
	 * @return
	 */
	public Hashtable<String, LineAlertData> getLineAlertDataHashtableByKeys(String linealertdata) {
		Hashtable<String, LineAlertData> ret = new Hashtable<String, LineAlertData> ();
		// 分隔key
		String [] keys = IDTools.splitID(linealertdata);
		for(int ik = 0;ik<keys.length;ik++) {
			// 从缓存里取图
			LineAlertData g = (LineAlertData)redisService.get(keys[ik]);
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
