package com.surpass.vision.realTimeData;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

import com.surpass.vision.XYGraph.XYGraphManager;
import com.surpass.vision.alertData.AlertDataManager;
import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.domain.RealTimeData;
import com.surpass.vision.domain.User;
import com.surpass.vision.domain.XYGraph;
import com.surpass.vision.graph.GraphManager;
import com.surpass.vision.historyData.HistoryDataManager;
import com.surpass.vision.lineAlertData.LineAlertDataManager;
import com.surpass.vision.mapper.PointGroupDataMapper;
import com.surpass.vision.mapper.UserSpaceDataMapper;
import com.surpass.vision.server.Point;
import com.surpass.vision.server.ServerManager;
import com.surpass.vision.service.PointGroupService;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.tools.IDTools;
import com.surpass.vision.user.UserManager;
import com.surpass.vision.userSpace.UserSpaceManager;

@Component
public class RealTimeDataManager {

	@Reference
	@Autowired
	RedisService redisService;

	@Autowired
	PointGroupService pointGroupService;

	@Autowired
	UserManager userManager;

	public RealTimeData generateRealTimeDataFromPointGroupData(PointGroupData pgd) {
		if (pgd == null)
			return null;
		RealTimeData realTimeData = new RealTimeData();

		realTimeData.setCreater(pgd.getCreater());
		realTimeData.setCreater(userManager.getUserByID(pgd.getCreater()));

		realTimeData.setId(pgd.getId());
		realTimeData.setName(pgd.getName());
		realTimeData.setOtherrule1(pgd.getOtherrule1());
		realTimeData.setOtherrule2(pgd.getOtherrule2());

		realTimeData.setOwner(pgd.getOwner());
		realTimeData.setOwner(userManager.getUserByID(pgd.getOwner()));

		realTimeData.setPoints(pgd.getPoints());
		ArrayList<Point> pal = new ArrayList<>();
		String[] pids = IDTools.splitID(pgd.getPoints());
		for (int ipids = 0; ipids < pids.length; ipids++) {
			Point p = ServerManager.getInstance().getPointByID(pids[ipids]);
			pal.add(p);
		}
		realTimeData.setPoints(pal);

		realTimeData.setShared(pgd.getShared());
		ArrayList<User> ul = new ArrayList<User>();
		String[] sharedIds = IDTools.splitID(pgd.getShared());
		for (int isharedIDs = 0; isharedIDs < sharedIds.length; isharedIDs++) {
			User u = userManager.getUserByID(sharedIds[isharedIDs]);
			ul.add(u);
		}
		realTimeData.setShared(ul);

		realTimeData.setType(pgd.getType());

		return realTimeData;
	}

	public Hashtable<String, RealTimeData> getAdminRealTimeDataHashtable() {
		Hashtable<String, RealTimeData> ret = new Hashtable<String, RealTimeData>();
		List<PointGroupData> pgdl = pointGroupService.getAdminRealTimeData();
		for (int i = 0; i < pgdl.size(); i++) {
			PointGroupData pgd = pgdl.get(i);
			RealTimeData realTimeData = new RealTimeData();

			realTimeData.setCreater(pgd.getCreater());
			realTimeData.setCreater(userManager.getUserByID(pgd.getCreater()));

			realTimeData.setId(pgd.getId());
			realTimeData.setName(pgd.getName());
			realTimeData.setOtherrule1(pgd.getOtherrule1());
			realTimeData.setOtherrule2(pgd.getOtherrule2());

			realTimeData.setOwner(pgd.getOwner());
			realTimeData.setOwner(userManager.getUserByID(pgd.getOwner()));

			realTimeData.setPoints(pgd.getPoints());
			ArrayList<Point> pal = new ArrayList<>();
			String[] pids = IDTools.splitID(pgd.getPoints());
			for (int ipids = 0; ipids < pids.length; ipids++) {
				Point p = ServerManager.getInstance().getPointByID(pids[ipids]);
				pal.add(p);
			}
			realTimeData.setPoints(pal);

			realTimeData.setShared(pgd.getShared());
			ArrayList<User> ul = new ArrayList<User>();
			String[] sharedIds = IDTools.splitID(pgd.getShared());
			for (int isharedIDs = 0; isharedIDs < sharedIds.length; isharedIDs++) {
				User u = userManager.getUserByID(sharedIds[isharedIDs]);
				ul.add(u);
			}
			realTimeData.setShared(ul);

			realTimeData.setType(pgd.getType());

			ret.put(pgd.getId().toString(), realTimeData);
			redisService.set(GlobalConsts.Key_RealTimeData_pre_ + realTimeData.getId().toString(), realTimeData);
		}
		return ret;
	}

	/**
	 * 从缓存里取数据，如果没有，再调用服务。
	 * 
	 * @param graphs
	 * @return
	 */
	public Hashtable<String, RealTimeData> getRealTimeDataHashtableByKeys(String realtimedata) {
		Hashtable<String, RealTimeData> ret = new Hashtable<String, RealTimeData>();
		// 分隔key
		String[] keys = IDTools.splitID(realtimedata);
		for (int ik = 0; ik < keys.length; ik++) {
			// 从缓存里取图
			RealTimeData g = (RealTimeData) redisService.get(keys[ik]);
			if (g == null) {
				// TODO: 如果没有, 从数据库里取

				// 再设置缓存
			}

			ret.put(g.getName(), g);
		}
		//
		return ret;
	}

	public RealTimeData createOrUpdateRealTimeData(String type, String name, String owner, String creater, String shared,
			String points, String otherrule1, String otherrule2) {
		// 先写缓存，返回
		// 异步处理:
		// 创建一条数据库记录
		// 更新相关人的信息
		
//		pointGroupService
		return null;
	}

}
