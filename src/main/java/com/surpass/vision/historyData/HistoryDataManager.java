package com.surpass.vision.historyData;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.surpass.vision.XYGraph.XYGraphManager;
import com.surpass.vision.alertData.AlertDataManager;
import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.domain.AlertData;
import com.surpass.vision.domain.HistoryData;
import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.domain.RealTimeData;
import com.surpass.vision.domain.HistoryData;
import com.surpass.vision.domain.User;
import com.surpass.vision.graph.GraphManager;
import com.surpass.vision.lineAlertData.LineAlertDataManager;
import com.surpass.vision.mapper.PointGroupDataMapper;
import com.surpass.vision.mapper.UserSpaceDataMapper;
import com.surpass.vision.pointGroup.PointGroupDataManager;
import com.surpass.vision.historyData.HistoryDataManager;
import com.surpass.vision.server.Point;
import com.surpass.vision.server.ServerManager;
import com.surpass.vision.service.PointGroupService;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.tools.IDTools;
import com.surpass.vision.user.UserManager;
import com.surpass.vision.userSpace.UserSpaceManager;

@Component
public class HistoryDataManager extends PointGroupDataManager {

	@Reference
	@Autowired
	RedisService redisService;


	@Autowired
	UserManager userManager;
//	@Autowired
//	ServerManager serverManager;

	@Autowired
	PointGroupService pointGroupService;

	
	
	public HistoryData copyFromPointGroupData(PointGroupData pgd) {
		if (pgd == null)
			return null;
		HistoryData HistoryData = new HistoryData();

		HistoryData.setCreater(pgd.getCreater());
		HistoryData.setCreaterUser(userManager.getUserByID(pgd.getCreater()));

		HistoryData.setId(pgd.getId());
		HistoryData.setName(pgd.getName());
		HistoryData.setOtherrule1(pgd.getOtherrule1());
		HistoryData.setOtherrule2(pgd.getOtherrule2());
		HistoryData.setDesc(pgd.getOtherrule2());

		HistoryData.setOwner(pgd.getOwner());
		HistoryData.setOwnerUser(userManager.getUserByID(pgd.getOwner()));

		HistoryData.setPoints(pgd.getPoints());
		ArrayList<Point> pal = new ArrayList<>();
		String[] pids = IDTools.splitID(pgd.getPoints());
		for (int ipids = 0; ipids < pids.length; ipids++) {
			String serverName = splitServerName(pids[ipids]);
			String pName = splitPointName(pids[ipids]);
			Point p = ServerManager.getInstance().getPointByID(serverName,pName);
			pal.add(p);
		}
		HistoryData.setPointList(pal);

		HistoryData.setShared(pgd.getShared());
		ArrayList<User> ul = new ArrayList<User>();
		String[] sharedIds = IDTools.splitID(pgd.getShared());
		for (int isharedIDs = 0; isharedIDs < sharedIds.length; isharedIDs++) {
			User u = userManager.getUserByID(sharedIds[isharedIDs]);
			ul.add(u);
		}
		HistoryData.setSharedUsers(ul);

		HistoryData.setType(pgd.getType());

		return HistoryData;
	}

	public Hashtable<String, HistoryData> getAdminHistoryDataHashtable() {
		Hashtable<String, HistoryData> ret = new Hashtable<String, HistoryData>();
		List<PointGroupData> pgdl = pointGroupService.getAdminHistoryData();
		for (int i = 0; i < pgdl.size(); i++) {
			PointGroupData pgd = pgdl.get(i);
			HistoryData HistoryData = copyFromPointGroupData(pgd);
			ret.put(IDTools.toString(pgd.getId()), HistoryData);
			System.out.println("pgd.getId().toString()="+pgd.getId().toString());
			redisService.set(GlobalConsts.Key_HistoryData_pre_ + IDTools.toString(HistoryData.getId()), HistoryData);
		}
		return ret;
	}

	/**
	 * 从缓存里取数据，如果没有，再调用服务。
	 * 
	 * @param graphs
	 * @return
	 */
	public Hashtable<String, HistoryData> getHistoryDataHashtableByKeys(String HistoryDataID) {
		Hashtable<String, HistoryData> ret = new Hashtable<String, HistoryData>();
		// 分隔key
		String[] keys = IDTools.splitID(HistoryDataID);
		for (int ik = 0; ik < keys.length; ik++) {
			// 从缓存里取图
			HistoryData g = getRealTimeDataRigidlyByKey(keys[ik]);
			if (g == null) {
				// 再设置缓存
			}else
				ret.put(IDTools.toString(g.getId()), g);
		}
		return ret;
	}


	private HistoryData getRealTimeDataRigidlyByKey(String idstr) {
		if(StringUtil.isBlank(idstr)) {
			throw new IllegalStateException("id不能为空。");
		}
		Double id = Double.valueOf(idstr);
		HistoryData ret = this.getHistoryDataByKeys(id);
		if(ret == null) {
			PointGroupData pgd = pointGroupService.getPointGroupDataByID(id);
			ret = this.copyFromPointGroupData(pgd);
			this.redisService.set(GlobalConsts.Key_HistoryData_pre_+IDTools.toString(id), ret);
		}
		return ret;
	}
	
	public RealTimeData getRealTimeDataByKeys(Double oldRtdId) {
		RealTimeData rtd = (RealTimeData)redisService.get(GlobalConsts.Key_RealTimeData_pre_+IDTools.toString(oldRtdId));
		return rtd;
	}
	

	public HistoryData createHistoryData(String typeHistoryData, String name, String owner, String creater,
			JSONArray points, String otherrule2, String otherrule1, String id2) {
		HistoryData ret ;
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
        if(StringUtil.isBlank(id2))
        	_id = IDTools.newID();
        else
        	_id = Double.valueOf(id2);
        pgd.setId(_id);
        pgd.setType(GlobalConsts.Type_historydata_);
        ret = copyFromPointGroupData(pgd);
        
		// 异步处理:
		try {
			// 先写缓存HistoryData，返回
			redisService.set(GlobalConsts.Key_HistoryData_pre_+IDTools.toString(_id),ret);
			// 创建一条数据库记录
			pointGroupService.newPointGroupData(pgd);	
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("新建实时数据失败。");
		}
		
		return ret;
	}

	public HistoryData getHistoryDataByKeys(Double oldRtdId) {
		HistoryData rtd = (HistoryData)redisService.get(GlobalConsts.Key_HistoryData_pre_+IDTools.toString(oldRtdId));
		return rtd;
	}

	public HistoryData deleteHistoryData(String oldRtdIdStr) {
		HistoryData oldRtd = null;
		if(StringUtil.isBlank(oldRtdIdStr)) return null;
		Double oldRtdId = Double.valueOf(oldRtdIdStr);
        //pgd.setType(GlobalConsts.Type_HistoryData_);
		// 异步处理:
		try {
			// 先写缓存HistoryData，返回
			if(oldRtdId == null || oldRtdId==0)
				oldRtd = new HistoryData();
			else 
				oldRtd = getHistoryDataByKeys(oldRtdId);
			
			// 删除一条数据库记录
			pointGroupService.deletePointGroupItem(oldRtdId);	
			// 删除缓存
			redisService.delete(GlobalConsts.Key_HistoryData_pre_+oldRtdId);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return oldRtd;
		}
		
		return oldRtd;	
	}

	public HistoryData updateShareRight(Double itemId, List<String> userIdsid) {
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
		HistoryData rtd = this.copyFromPointGroupData(pgd);
		// 写缓存HistoryData，返回
		redisService.set(GlobalConsts.Key_HistoryData_pre_+IDTools.toString(rtd.getId()),rtd);

		return rtd;
	}

	public void updateHistoryData(HistoryData rtd) {
		// 更新数据库
		pointGroupService.updatePointGroupItem(rtd);
		// 写缓存RealTimeData，返回
		redisService.set(GlobalConsts.Key_HistoryData_pre_+IDTools.toString(rtd.getId()),rtd);
	}


}
