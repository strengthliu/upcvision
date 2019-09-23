package com.surpass.vision.alertData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.surpass.vision.XYGraph.XYGraphManager;
import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.domain.AlertData;
import com.surpass.vision.domain.PointAlertData;
import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.domain.AlertData;
import com.surpass.vision.domain.User;
import com.surpass.vision.graph.GraphManager;
import com.surpass.vision.historyData.HistoryDataManager;
import com.surpass.vision.lineAlertData.LineAlertDataManager;
import com.surpass.vision.mapper.PointGroupDataMapper;
import com.surpass.vision.mapper.UserSpaceDataMapper;
import com.surpass.vision.pointGroup.PointGroupDataManager;
import com.surpass.vision.server.Point;
import com.surpass.vision.server.ServerManager;
import com.surpass.vision.service.PointGroupService;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.tools.IDTools;
import com.surpass.vision.user.UserManager;
import com.surpass.vision.userSpace.UserSpaceManager;

@Component
public class AlertDataManager extends PointGroupDataManager {

	@Reference
	@Autowired
	RedisService redisService;

	@Autowired
	PointGroupService pointGroupService;

	@Autowired
	UserManager userManager;
	
	@Autowired
	ServerManager serverManager;

	
	
	public AlertData copyFromPointGroupData(PointGroupData pgd) {
		if (pgd == null)
			return null;
		AlertData AlertData = new AlertData();

		AlertData.setCreater(pgd.getCreater());
		AlertData.setCreaterUser(userManager.getUserByID(pgd.getCreater()));

		AlertData.setId(pgd.getId());
		AlertData.setName(pgd.getName());
		AlertData.setOtherrule1(pgd.getOtherrule1());
		AlertData.setOtherrule2(pgd.getOtherrule2());
		AlertData.setDesc(pgd.getOtherrule2());

		AlertData.setOwner(pgd.getOwner());
		AlertData.setOwnerUser(userManager.getUserByID(pgd.getOwner()));

		AlertData.setPoints(pgd.getPoints());
		ArrayList<Point> pal = new ArrayList<>();
		String[] pids = IDTools.splitID(pgd.getPoints());
		for (int ipids = 0; ipids < pids.length; ipids++) {
			String serverName = splitServerName(pids[ipids]);
			String pName = splitPointName(pids[ipids]);
			Point p = ServerManager.getInstance().getPointByID(serverName,pName);
			pal.add(p);
		}
		AlertData.setPointList(pal);

		AlertData.setShared(pgd.getShared());
		ArrayList<User> ul = new ArrayList<User>();
		String[] sharedIds = IDTools.splitID(pgd.getShared());
		for (int isharedIDs = 0; isharedIDs < sharedIds.length; isharedIDs++) {
			User u = userManager.getUserByID(sharedIds[isharedIDs]);
			ul.add(u);
		}
		AlertData.setSharedUsers(ul);

		AlertData.setType(pgd.getType());

		return AlertData;
	}

	public Hashtable<String, AlertData> getAdminAlertDataHashtable() {
		Hashtable<String, AlertData> ret = new Hashtable<String, AlertData>();
		List<PointGroupData> pgdl = pointGroupService.getAdminAlertData();
		for (int i = 0; i < pgdl.size(); i++) {
			PointGroupData pgd = pgdl.get(i);
			AlertData AlertData = copyFromPointGroupData(pgd);
			ret.put(IDTools.toString(pgd.getId()), AlertData);
			System.out.println("pgd.getId().toString()="+pgd.getId().toString());
			redisService.set(GlobalConsts.Key_AlertData_pre_ + IDTools.toString(AlertData.getId()), AlertData);
		}
		return ret;
	}

	/**
	 * 从缓存里取数据，如果没有，再调用服务。
	 * 
	 * @param graphs
	 * @return
	 */
	public Hashtable<String, AlertData> getAlertDataHashtableByKeys(String AlertdataID) {
		Hashtable<String, AlertData> ret = new Hashtable<String, AlertData>();
		// 分隔key
		String[] keys = IDTools.splitID(AlertdataID);
		for (int ik = 0; ik < keys.length; ik++) {
			// 从缓存里取图
			AlertData g = (AlertData) redisService.get(keys[ik]);
			if (g == null) {
				// TODO: 如果没有, 从数据库里取

				// 再设置缓存
			}

			ret.put(IDTools.toString(g.getId()), g);
		}
		//
		return ret;
	}


	public AlertData createAlertData(String typeAlertdata, String name, String owner, String creater,
			JSONArray points, String otherrule2, String otherrule1, String id) {
		AlertData ret ;
		PointGroupData pgd = new PointGroupData();
		pgd.setCreater(creater);
		pgd.setOwner(owner);
		pgd.setName(name);
		pgd.setOtherrule2(otherrule2);
		pgd.setOtherrule1(otherrule1);
        String pointsString = "";
        for(int i = 0;i<points.size();i++) {
        	
        	String jstr = points.getString(i);
//        	int _i = jstr.indexOf()
//        	String serverName = jstr.getString("serverName");
//        	String tagName = jstr.getString("tagName");
        	pointsString = pointsString+jstr+GlobalConsts.Key_splitChar;
            }
        if(pointsString.endsWith(GlobalConsts.Key_splitChar)) 
        	pointsString = pointsString.substring(0,pointsString.length()-GlobalConsts.Key_splitChar.length());
        pgd.setPoints(pointsString);
        Double _id ;
        if(StringUtil.isBlank(id))
        	_id = IDTools.newID();
        else
        	_id = Double.valueOf(id);
        pgd.setId(_id);
        pgd.setType(GlobalConsts.Type_alertdata_);
        ret = copyFromPointGroupData(pgd);
        
		// 异步处理:
		try {
			// 先写缓存AlertData，返回
			redisService.set(GlobalConsts.Key_AlertData_pre_+IDTools.toString(_id),ret);
			// 创建一条数据库记录
			pointGroupService.newPointGroupData(pgd);	
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("新建实时数据失败。");
		}
		
		return ret;
	}

	public AlertData getAlertDataByKeys(Double oldRtdId) {
		AlertData rtd = (AlertData)redisService.get(GlobalConsts.Key_AlertData_pre_+IDTools.toString(oldRtdId));
		return rtd;
	}

	public AlertData deleteAlertData(String oldRtdIdStr) {
		AlertData oldRtd = null;
		if(StringUtil.isBlank(oldRtdIdStr)) return null;
		Double oldRtdId = Double.valueOf(oldRtdIdStr);
        //pgd.setType(GlobalConsts.Type_Alertdata_);
		// 异步处理:
		try {
			// 先写缓存AlertData，返回
			if(oldRtdId == null || oldRtdId==0)
				oldRtd = new AlertData();
			else 
				oldRtd = getAlertDataByKeys(oldRtdId);
			
			// 删除一条数据库记录
			pointGroupService.deletePointGroupItem(oldRtdId);	
			// 删除缓存
			redisService.delete(GlobalConsts.Key_AlertData_pre_+oldRtdId);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return oldRtd;
		}
		
		return oldRtd;	
	}

	public AlertData updateShareRight(Double itemId, List<String> userIdsid) {
		// TODO Auto-generated method stub
		PointGroupData pgd = pointGroupService.getPointGroupDataByID(itemId);
		if(pgd == null) {
			throw new IllegalStateException("没有id为"+itemId+"这个数据");
		}
		String sharedUserIDs = "";
		if(userIdsid != null) {
			sharedUserIDs = IDTools.merge(userIdsid.toArray());
		}
		pgd.setShared(sharedUserIDs);
		// 更新数据库
		pointGroupService.updatePointGroupItem(pgd);
		
		// 更新缓存 
		// TODO: 现在出现当用户删除以后，数据的共享用户是两个空值，怀疑是这里的问题
		AlertData rtd = this.copyFromPointGroupData(pgd);
		// 写缓存AlertData，返回
		redisService.set(GlobalConsts.Key_AlertData_pre_+IDTools.toString(rtd.getId()),rtd);
									  
		return rtd;
	}

	public void updateAlertData(AlertData rtd) {
		// 更新数据库
		pointGroupService.updatePointGroupItem(rtd);
		// 写缓存RealTimeData，返回
		redisService.set(GlobalConsts.Key_AlertData_pre_+IDTools.toString(rtd.getId()),rtd);
	}

	public List<PointAlertData> getAlertData(AlertData g, Long startTime, Long endTime) {
		// TODO Auto-generated method stub
		List<Point> pl =g.getPointList();
		List<PointAlertData> pad = null;		
		if(endTime==null || endTime==0) {
			endTime = System.currentTimeMillis() /1000;
		}
		if(startTime==null || startTime==0) {
			pad = serverManager.getPointAlertData(pl);
		} else {
			pad = serverManager.getHistoryPointAlertData(pl, startTime, endTime);
		}
		return pad;
	}


}
