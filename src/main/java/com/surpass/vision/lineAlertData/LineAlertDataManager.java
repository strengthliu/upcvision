package com.surpass.vision.lineAlertData;

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
import com.surpass.vision.domain.HistoryData;
import com.surpass.vision.domain.LineAlertData;
import com.surpass.vision.domain.LineAlertData;
import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.domain.RealTimeData;
import com.surpass.vision.domain.User;
import com.surpass.vision.domain.XYGraph;
import com.surpass.vision.graph.GraphManager;
import com.surpass.vision.lineAlertData.LineAlertDataManager;
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



	public LineAlertData copyFromPointGroupData(PointGroupData pgd) {
		if (pgd == null)
			return null;
		LineAlertData LineAlertData = new LineAlertData();

		LineAlertData.setCreater(pgd.getCreater());
		LineAlertData.setCreaterUser(userManager.getUserByID(pgd.getCreater()));

		LineAlertData.setId(pgd.getId());
		LineAlertData.setName(pgd.getName());
		LineAlertData.setOtherrule1(pgd.getOtherrule1());
		LineAlertData.setOtherrule2(pgd.getOtherrule2());
		LineAlertData.setDesc(pgd.getOtherrule2());

		LineAlertData.setOwner(pgd.getOwner());
		LineAlertData.setOwnerUser(userManager.getUserByID(pgd.getOwner()));

		LineAlertData.setPoints(pgd.getPoints());
		ArrayList<Point> pal = new ArrayList<>();
		String[] pids = IDTools.splitID(pgd.getPoints());
		for (int ipids = 0; ipids < pids.length; ipids++) {
			String serverName = splitServerName(pids[ipids]);
			String pName = splitPointName(pids[ipids]);
			Point p = ServerManager.getInstance().getPointByID(serverName,pName);
			pal.add(p);
		}
		LineAlertData.setPointList(pal);

		LineAlertData.setShared(pgd.getShared());
		ArrayList<User> ul = new ArrayList<User>();
		String[] sharedIds = IDTools.splitID(pgd.getShared());
		for (int isharedIDs = 0; isharedIDs < sharedIds.length; isharedIDs++) {
			User u = userManager.getUserByID(sharedIds[isharedIDs]);
			ul.add(u);
		}
		LineAlertData.setSharedUsers(ul);

		LineAlertData.setType(pgd.getType());

		return LineAlertData;
	}

	public Hashtable<String, LineAlertData> getAdminLineAlertDataHashtable() {
		Hashtable<String, LineAlertData> ret = new Hashtable<String, LineAlertData>();
		List<PointGroupData> pgdl = pointGroupService.getAdminLineAlertData();
		for (int i = 0; i < pgdl.size(); i++) {
			PointGroupData pgd = pgdl.get(i);
			LineAlertData LineAlertData = copyFromPointGroupData(pgd);
			ret.put(IDTools.toString(pgd.getId()), LineAlertData);
			System.out.println("pgd.getId().toString()="+pgd.getId().toString());
			redisService.set(GlobalConsts.Key_LineAlertData_pre_ + IDTools.toString(LineAlertData.getId()), LineAlertData);
		}
		return ret;
	}

	/**
	 * 从缓存里取数据，如果没有，再调用服务。
	 * 
	 * @param graphs
	 * @return
	 */
	public Hashtable<String, LineAlertData> getLineAlertDataHashtableByKeys(String LineAlertDataID) {
		Hashtable<String, LineAlertData> ret = new Hashtable<String, LineAlertData>();
		// 分隔key
		String[] keys = IDTools.splitID(LineAlertDataID);
		for (int ik = 0; ik < keys.length; ik++) {
			// 从缓存里取图
			LineAlertData g = getLineAlertDataRigidlyByKey(keys[ik]);
			if (g == null) {
				// 再设置缓存
			}else
				ret.put(IDTools.toString(g.getId()), g);
		}
		return ret;
	}


	private LineAlertData getLineAlertDataRigidlyByKey(String idstr) {
		if(StringUtil.isBlank(idstr)) {
			throw new IllegalStateException("id不能为空。");
		}
		Double id = Double.valueOf(idstr);
		LineAlertData ret = this.getLineAlertDataByKeys(id);
		if(ret == null) {
			PointGroupData pgd = pointGroupService.getPointGroupDataByID(id);
			ret = this.copyFromPointGroupData(pgd);
			this.redisService.set(GlobalConsts.Key_LineAlertData_pre_+IDTools.toString(id), ret);
		}
		return ret;
	}


	public LineAlertData createLineAlertData(String typeLineAlertData, String name, String owner, String creater,
			JSONArray points, String otherrule2, String otherrule1, String id2) {
		LineAlertData ret ;
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
        pgd.setType(GlobalConsts.Type_linealertdata_);
        
		// 异步处理:
		try {
			// 创建一条数据库记录
			pointGroupService.newPointGroupData(pgd);	
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("新建实时数据失败。");
		}
        ret = copyFromPointGroupData(pgd);
		// 先写缓存LineAlertData，返回
		redisService.set(GlobalConsts.Key_LineAlertData_pre_+IDTools.toString(_id),ret);
		
		return ret;
	}

	public LineAlertData getLineAlertDataByKeys(Double oldRtdId) {
		LineAlertData rtd = (LineAlertData)redisService.get(GlobalConsts.Key_LineAlertData_pre_+IDTools.toString(oldRtdId));
		return rtd;
	}

	public LineAlertData deleteLineAlertData(String oldRtdIdStr) {
		LineAlertData oldRtd = null;
		if(StringUtil.isBlank(oldRtdIdStr)) return null;
		Double oldRtdId = Double.valueOf(oldRtdIdStr);
        //pgd.setType(GlobalConsts.Type_LineAlertData_);
		// 异步处理:
		try {
			// 先写缓存LineAlertData，返回
			if(oldRtdId == null || oldRtdId==0)
				oldRtd = new LineAlertData();
			else 
				oldRtd = getLineAlertDataByKeys(oldRtdId);
			
			// 删除一条数据库记录
			pointGroupService.deletePointGroupItem(oldRtdId);	
			// 删除缓存
			redisService.delete(GlobalConsts.Key_LineAlertData_pre_+oldRtdId);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return oldRtd;
		}
		
		return oldRtd;	
	}

	public LineAlertData updateShareRight(Double itemId, List<String> userIdsid, List<String> depIdsid) {
		return (LineAlertData) updateShareRight(new LineAlertData(),GlobalConsts.Key_LineAlertData_pre_,itemId, userIdsid,depIdsid);
	}
	

	public void updateLineAlertData(LineAlertData rtd) {
		// 更新数据库
		pointGroupService.updatePointGroupItem(rtd);
		// 写缓存RealTimeData，返回
		redisService.set(GlobalConsts.Key_LineAlertData_pre_+IDTools.toString(rtd.getId()),rtd);
	}


}
