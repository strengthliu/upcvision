package com.surpass.vision.XYGraph;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.domain.Graph;
import com.surpass.vision.domain.LineAlertData;
import com.surpass.vision.domain.XYGraph;
import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.domain.User;
import com.surpass.vision.domain.XYGraph;
import com.surpass.vision.mapper.PointGroupDataMapper;
import com.surpass.vision.pointGroup.PointGroupDataManager;
import com.surpass.vision.server.Point;
import com.surpass.vision.server.ServerManager;
import com.surpass.vision.service.PointGroupService;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.tools.IDTools;
import com.surpass.vision.user.UserManager;
@Component
public class XYGraphManager extends PointGroupDataManager {
	@Reference
	@Autowired
	RedisService redisService;
	

	@Autowired
	PointGroupService pointGroupService;
	
	@Autowired
	UserManager userManager;

	public XYGraph copyFromPointGroupData(PointGroupData pgd) {
		if (pgd == null)
			return null;
		XYGraph XYGraph = new XYGraph();

		XYGraph.setCreater(pgd.getCreater());
		XYGraph.setCreaterUser(userManager.getUserByID(pgd.getCreater()));

		XYGraph.setId(pgd.getId());
		XYGraph.setName(pgd.getName());
		XYGraph.setOtherrule1(pgd.getOtherrule1());
		XYGraph.setOtherrule2(pgd.getOtherrule2());
		XYGraph.setDesc(pgd.getOtherrule2());

		XYGraph.setOwner(pgd.getOwner());
		XYGraph.setOwnerUser(userManager.getUserByID(pgd.getOwner()));

		XYGraph.setPoints(pgd.getPoints());
		ArrayList<Point> pal = new ArrayList<>();
		String[] pids = IDTools.splitID(pgd.getPoints());
		for (int ipids = 0; ipids < pids.length; ipids++) {
			String serverName = splitServerName(pids[ipids]);
			String pName = splitPointName(pids[ipids]);
			Point p = ServerManager.getInstance().getPointByID(serverName,pName);
			pal.add(p);
		}
		XYGraph.setPointList(pal);

		XYGraph.setShared(pgd.getShared());
		ArrayList<User> ul = new ArrayList<User>();
		String[] sharedIds = IDTools.splitID(pgd.getShared());
		for (int isharedIDs = 0; isharedIDs < sharedIds.length; isharedIDs++) {
			User u = userManager.getUserByID(sharedIds[isharedIDs]);
			ul.add(u);
		}
		XYGraph.setSharedUsers(ul);

		XYGraph.setType(pgd.getType());

		return XYGraph;
	}

	public Hashtable<String, XYGraph> getAdminXYGraphHashtable() {
		Hashtable<String, XYGraph> ret = new Hashtable<String, XYGraph>();
		List<PointGroupData> pgdl = pointGroupService.getAdminXYGraph();
		for (int i = 0; i < pgdl.size(); i++) {
			PointGroupData pgd = pgdl.get(i);
			XYGraph XYGraph = copyFromPointGroupData(pgd);
			ret.put(IDTools.toString(pgd.getId()), XYGraph);
			System.out.println("pgd.getId().toString()="+pgd.getId().toString());
			redisService.set(GlobalConsts.Key_XYGraph_pre_ + IDTools.toString(XYGraph.getId()), XYGraph);
		}
		return ret;
	}

	/**
	 * 从缓存里取数据，如果没有，再调用服务。
	 * 
	 * @param graphs
	 * @return
	 */
	public Hashtable<String, XYGraph> getXYGraphHashtableByKeys(String XYGraphID) {
		Hashtable<String, XYGraph> ret = new Hashtable<String, XYGraph>();
		// 分隔key
		String[] keys = IDTools.splitID(XYGraphID);
		for (int ik = 0; ik < keys.length; ik++) {
			// 从缓存里取图
			XYGraph g = (XYGraph) redisService.get(keys[ik]);
			if (g == null) {
				// TODO: 如果没有, 从数据库里取

				// 再设置缓存
			}

			ret.put(IDTools.toString(g.getId()), g);
		}
		//
		return ret;
	}
	/**
	 * 从缓存里取数据，如果没有，再调用服务。
	 * 
	 * @param graphs
	 * @return
	 */
	public Hashtable<String, XYGraph> getLineAlertDataHashtableByKeys(String LineAlertDataID) {
		Hashtable<String, XYGraph> ret = new Hashtable<String, XYGraph>();
		// 分隔key
		String[] keys = IDTools.splitID(LineAlertDataID);
		for (int ik = 0; ik < keys.length; ik++) {
			// 从缓存里取图
			XYGraph g = getXYGraphRigidlyByKey(keys[ik]);
			if (g == null) {
				// 再设置缓存
			}else
				ret.put(IDTools.toString(g.getId()), g);
		}
		return ret;
	}


	private XYGraph getXYGraphRigidlyByKey(String idstr) {
		if(StringUtil.isBlank(idstr)) {
			throw new IllegalStateException("id不能为空。");
		}
		Double id = Double.valueOf(idstr);
		XYGraph ret = this.getXYGraphByKeys(id);
		if(ret == null) {
			PointGroupData pgd = pointGroupService.getPointGroupDataByID(id);
			ret = this.copyFromPointGroupData(pgd);
			this.redisService.set(GlobalConsts.Key_XYGraph_pre_+IDTools.toString(id), ret);
		}
		return ret;
	}


	public XYGraph createXYGraph(String typeXYGraph, String name, String owner, String creater,
			JSONArray points, String otherrule2, String otherrule1, String id2) {
		XYGraph ret ;
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
        pgd.setType(GlobalConsts.Type_xygraph_);
        ret = copyFromPointGroupData(pgd);
        
		// 异步处理:
		try {
			// 先写缓存XYGraph，返回
			redisService.set(GlobalConsts.Key_XYGraph_pre_+IDTools.toString(_id),ret);
			// 创建一条数据库记录
			pointGroupService.newPointGroupData(pgd);	
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("新建实时数据失败。");
		}
		
		return ret;
	}

	public XYGraph getXYGraphByKeys(Double oldRtdId) {
		XYGraph rtd = (XYGraph)redisService.get(GlobalConsts.Key_XYGraph_pre_+IDTools.toString(oldRtdId));
		return rtd;
	}

	public XYGraph deleteXYGraph(String oldRtdIdStr) {
		XYGraph oldRtd = null;
		if(StringUtil.isBlank(oldRtdIdStr)) return null;
		Double oldRtdId = Double.valueOf(oldRtdIdStr);
        //pgd.setType(GlobalConsts.Type_XYGraph_);
		// 异步处理:
		try {
			// 先写缓存XYGraph，返回
			if(oldRtdId == null || oldRtdId==0)
				oldRtd = new XYGraph();
			else 
				oldRtd = getXYGraphByKeys(oldRtdId);
			
			// 删除一条数据库记录
			pointGroupService.deletePointGroupItem(oldRtdId);	
			// 删除缓存
			redisService.delete(GlobalConsts.Key_XYGraph_pre_+oldRtdId);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return oldRtd;
		}
		
		return oldRtd;	
	}

	public XYGraph updateShareRight(Double itemId, List<String> userIdsid) {
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
		XYGraph rtd = this.copyFromPointGroupData(pgd);
		// 写缓存XYGraph，返回
		redisService.set(GlobalConsts.Key_XYGraph_pre_+IDTools.toString(rtd.getId()),rtd);

		return rtd;
	}

	public void updateXYGraph(XYGraph rtd) {
		// 更新数据库
		pointGroupService.updatePointGroupItem(rtd);
		// 写缓存RealTimeData，返回
		redisService.set(GlobalConsts.Key_XYGraph_pre_+IDTools.toString(rtd.getId()),rtd);
	}


}
