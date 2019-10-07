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
import com.alibaba.fastjson.JSONObject;
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
		realTimeData.setDesc(pgd.getOtherrule2());

		realTimeData.setOwner(pgd.getOwner());
		realTimeData.setOwnerUser(userManager.getUserByID(pgd.getOwner()));

		realTimeData.setPoints(pgd.getPoints());
		ArrayList<Point> pal = new ArrayList<>();
		String[] pids = IDTools.splitID(pgd.getPoints());
		for (int ipids = 0; ipids < pids.length; ipids++) {
			String serverName = splitServerName(pids[ipids]);
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

	public Hashtable<String, RealTimeData> getAdminRealTimeDataHashtable() {
		Hashtable<String, RealTimeData> ret = new Hashtable<String, RealTimeData>();
		List<PointGroupData> pgdl = pointGroupService.getAdminRealTimeData();
		for (int i = 0; i < pgdl.size(); i++) {
			PointGroupData pgd = pgdl.get(i);
			RealTimeData realTimeData = copyFromPointGroupData(pgd);
			ret.put(IDTools.toString(pgd.getId()), realTimeData);
			System.out.println("pgd.getId().toString()="+pgd.getId().toString());
			redisService.set(GlobalConsts.Key_RealTimeData_pre_ + IDTools.toString(realTimeData.getId()), realTimeData);
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
			RealTimeData g = getRealTimeDataRigidlyByKey(keys[ik]);
			if (g == null) {
				// 再设置缓存
			}else
				ret.put(IDTools.toString(g.getId()), g);
		}
		return ret;
	}


	private RealTimeData getRealTimeDataRigidlyByKey(String idstr) {
		if(StringUtil.isBlank(idstr)) {
			throw new IllegalStateException("id不能为空。");
		}
		Double id = Double.valueOf(idstr);
		RealTimeData ret = this.getRealTimeDataByKeys(id);
		if(ret == null) {
			PointGroupData pgd = pointGroupService.getPointGroupDataByID(id);
			ret = this.copyFromPointGroupData(pgd);
			this.redisService.set(GlobalConsts.Key_RealTimeData_pre_+IDTools.toString(id), ret);
		}
		return ret;
	}
	
	public RealTimeData getRealTimeDataByKeys(Double oldRtdId) {
		RealTimeData rtd = (RealTimeData)redisService.get(GlobalConsts.Key_RealTimeData_pre_+IDTools.toString(oldRtdId));
		return rtd;
	}
	
	public RealTimeData createRealTimeData(String typeRealtimedata, String name, String owner, String creater,
			JSONArray points, String otherrule2, String id2) {
		RealTimeData ret ;
		PointGroupData pgd = new PointGroupData();
		pgd.setCreater(creater);
		pgd.setOwner(owner);
		pgd.setName(name);
		pgd.setOtherrule2(otherrule2);
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
        if(StringUtil.isBlank(id2))
        	_id = IDTools.newID();
        else
        	_id = Double.valueOf(id2);
        pgd.setId(_id);
        pgd.setType(GlobalConsts.Type_realtimedata_);
        
		// 异步处理:
		try {
			// 创建一条数据库记录
			pointGroupService.newPointGroupData(pgd);	
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("新建实时数据失败。");
		}
        ret = copyFromPointGroupData(pgd);
		// 先写缓存RealTimeData，返回
		redisService.set(GlobalConsts.Key_RealTimeData_pre_+IDTools.toString(_id),ret);
		
		return ret;
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
			// 删除缓存
			redisService.delete(GlobalConsts.Key_RealTimeData_pre_+oldRtdId);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return oldRtd;
		}
		
		return oldRtd;	
	}

	public RealTimeData updateShareRight(Double itemId, List<String> userIdsid) {
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
		RealTimeData rtd = this.copyFromPointGroupData(pgd);
		// 写缓存RealTimeData，返回
		redisService.set(GlobalConsts.Key_RealTimeData_pre_+IDTools.toString(rtd.getId()),rtd);

		return rtd;
	}

	public void updateRealTimeData(RealTimeData rtd) {
		// 更新数据库
		pointGroupService.updatePointGroupItem(rtd);
		// 写缓存RealTimeData，返回
		redisService.set(GlobalConsts.Key_RealTimeData_pre_+IDTools.toString(rtd.getId()),rtd);
	}


}
