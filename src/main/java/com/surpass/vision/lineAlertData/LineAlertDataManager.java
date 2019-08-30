package com.surpass.vision.lineAlertData;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.surpass.vision.XYGraph.XYGraphManager;
import com.surpass.vision.alertData.AlertDataManager;
import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.domain.HistoryData;
import com.surpass.vision.domain.LineAlertData;
import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.domain.RealTimeData;
import com.surpass.vision.domain.User;
import com.surpass.vision.graph.GraphManager;
import com.surpass.vision.historyData.HistoryDataManager;
import com.surpass.vision.mapper.PointGroupDataMapper;
import com.surpass.vision.mapper.UserSpaceDataMapper;
import com.surpass.vision.pointGroup.PointGroupDataManager;
import com.surpass.vision.realTimeData.RealTimeDataManager;
import com.surpass.vision.server.Point;
import com.surpass.vision.server.ServerManager;
import com.surpass.vision.service.PointGroupService;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.tools.IDTools;
import com.surpass.vision.user.UserManager;
import com.surpass.vision.userSpace.UserSpaceManager;

@Component
public class LineAlertDataManager extends PointGroupDataManager {


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
			lineAlertData.setCreaterUser(userManager.getUserByID(pgd.getCreater()));

			lineAlertData.setId(pgd.getId());
			lineAlertData.setName(pgd.getName());
			lineAlertData.setOtherrule1(pgd.getOtherrule1());
			lineAlertData.setOtherrule2(pgd.getOtherrule2());

			lineAlertData.setOwner(pgd.getOwner());
			lineAlertData.setOwnerUser(userManager.getUserByID(pgd.getOwner()));

			lineAlertData.setPoints(pgd.getPoints());
			ArrayList<Point> pal = new ArrayList<>();
			String[] pids = IDTools.splitID(pgd.getPoints());
			for (int ipids = 0; ipids < pids.length; ipids++) {
				String serverName = splitPointName(pids[ipids]);
				String pName = splitPointName(pids[ipids]);
				Point p = ServerManager.getInstance().getPointByID(serverName,pName);
				pal.add(p);
			}
			lineAlertData.setPointList(pal);

			lineAlertData.setShared(pgd.getShared());
			ArrayList<User> ul = new ArrayList<User>();
			String[] sharedIds = IDTools.splitID(pgd.getShared());
			for (int isharedIDs = 0; isharedIDs < sharedIds.length; isharedIDs++) {
				User u = userManager.getUserByID(sharedIds[isharedIDs]);
				ul.add(u);
			}
			lineAlertData.setSharedUsers(ul);

			lineAlertData.setType(pgd.getType());

			ret.put(IDTools.toString(pgd.getId()), lineAlertData);
		}
		return ret;
	}


	public LineAlertData createLineAlertData(String typeRealtimedata, String name, String owner, String creater,
			JSONArray points, String otherrule2) {
		LineAlertData ret ;
		PointGroupData pgd = new PointGroupData();
		pgd.setCreater(creater);
		pgd.setOwner(owner);
		pgd.setName(name);
		pgd.setOtherrule2(otherrule2);
        String pointsString = "";
        for(Object jstr:points){
        	pointsString = pointsString+jstr+GlobalConsts.Key_splitChar;
            }
        if(pointsString.endsWith(GlobalConsts.Key_splitChar)) 
        	pointsString = pointsString.substring(0,pointsString.length()-GlobalConsts.Key_splitChar.length());
        pgd.setPoints(pointsString);
        Double id = IDTools.newID();
        pgd.setId(id);
        pgd.setType(GlobalConsts.Type_realtimedata_);
        ret = copyFromPointGroupData(pgd);
        
		// 异步处理:
		try {
			// TODO:  先写缓存RealTimeData，返回
			
			// 创建一条数据库记录
			pointGroupService.newPointGroupData(pgd);	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
		}

	public LineAlertData copyFromPointGroupData(PointGroupData pgd) {
		if (pgd == null)
			return null;
		LineAlertData realTimeData = new LineAlertData();

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
			String serverName = splitPointName(pids[ipids]);
			String pName = splitPointName(pids[ipids]);
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


	public LineAlertData getLineAlertDataByKeys(Double oldRtdId) {
		LineAlertData rtd = (LineAlertData)redisService.get(GlobalConsts.Key_RealTimeData_pre_+IDTools.toString(oldRtdId));
		return rtd;
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
			
			ret.put(IDTools.toString(g.getId()), g);
		}
		// 
		return ret;
	}



}
