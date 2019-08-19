package com.surpass.vision.realTimeData;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.Future;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.domain.RealTimeData;
import com.surpass.vision.domain.User;
import com.surpass.vision.pointGroup.PointGroupDataManager;
import com.surpass.vision.server.Point;
import com.surpass.vision.server.ServerManager;
import com.surpass.vision.service.PointGroupService;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.tools.IDTools;
import com.surpass.vision.user.UserManager;

// @JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class RealTimeDataManager extends PointGroupDataManager {

	@Reference
	@Autowired
	RedisService redisService;

	@Autowired
	PointGroupService pointGroupService;

	@Autowired
	UserManager userManager;
	
	
	public RealTimeData copyFromPointGroupData(PointGroupData pgd) {
		if (pgd == null)
			return null;
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
			Point p = ServerManager.getInstance().getPointByID(pids[ipids]);
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

	public Hashtable<String, RealTimeData> getAdminRealTimeDataHashtable() {
		Hashtable<String, RealTimeData> ret = new Hashtable<String, RealTimeData>();
		List<PointGroupData> pgdl = pointGroupService.getAdminRealTimeData();
		for (int i = 0; i < pgdl.size(); i++) {
			PointGroupData pgd = pgdl.get(i);
			RealTimeData realTimeData = copyFromPointGroupData(pgd);
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
	public Hashtable<String, RealTimeData> getRealTimeDataHashtableByKeys(String realtimedataID) {
		Hashtable<String, RealTimeData> ret = new Hashtable<String, RealTimeData>();
		// 分隔key
		String[] keys = IDTools.splitID(realtimedataID);
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


	public RealTimeData createRealTimeData(String typeRealtimedata, String name, String owner, String creater,
			JSONArray points, String otherrule2) {
		RealTimeData ret ;
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
			// 先写缓存RealTimeData，返回
			redisService.set(GlobalConsts.Key_RealTimeData_pre_+id,ret);
			// 创建一条数据库记录
			pointGroupService.newPointGroupData(pgd);	
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("新建实时数据失败。");
		}
		
		return ret;
	}

	public RealTimeData getRealTimeDataByKeys(Double oldRtdId) {
		RealTimeData rtd = (RealTimeData)redisService.get(GlobalConsts.Key_RealTimeData_pre_+oldRtdId);
		return rtd;
	}

	public RealTimeData deleteRealTimeData(String oldRtdIdStr) {
		RealTimeData oldRtd = null;
		if(StringUtil.isBlank(oldRtdIdStr)) return null;
		Double oldRtdId = Double.valueOf(oldRtdIdStr);
        //pgd.setType(GlobalConsts.Type_realtimedata_);
		// 异步处理:
		try {
			// 先写缓存RealTimeData，返回
			if(oldRtdId == null || oldRtdId==0)
				oldRtd = new RealTimeData();
			else 
				oldRtd = getRealTimeDataByKeys(oldRtdId);
			
			// 删除一条数据库记录
			pointGroupService.deletePointGroupItem(oldRtdId);	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return oldRtd;
		}
		
		return oldRtd;	
	}


}
