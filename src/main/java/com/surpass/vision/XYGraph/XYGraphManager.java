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
import com.surpass.vision.domain.PointGroup;
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
		return (com.surpass.vision.domain.XYGraph) copyFromPointGroupData(XYGraph,pgd);
	}

	public Hashtable getAdminXYGraphHashtable() {
		Hashtable ret = new Hashtable();
		return getAdminPointGroupHashtable(ret,GlobalConsts.Key_XYGraph_pre_,new XYGraph());
	}

	/**
	 * 从缓存里取数据，如果没有，再调用服务。
	 * 
	 * @param graphs
	 * @return
	 */
	public Hashtable getXYGraphHashtableByKeys(String LineAlertDataID) {
//		Hashtable ret = new Hashtable();
		return getPointGroupHashtableByKeys(GlobalConsts.Key_XYGraph_pre_,new XYGraph(),LineAlertDataID);
	}

	private XYGraph getXYGraphRigidlyByKey(String idstr) {
		if(StringUtil.isBlank(idstr)) {
			throw new IllegalStateException("id不能为空。");
		}
		Double id = Double.valueOf(idstr);
		return (XYGraph) getPointGroupByKeys(GlobalConsts.Key_XYGraph_pre_,new XYGraph(),id);
	}

	public XYGraph createXYGraph(String name, String owner, String creater,
			JSONArray points, String otherrule2, String otherrule1, String id2) {
		XYGraph ret = new XYGraph();
		return (XYGraph) createPointGroup(ret,GlobalConsts.Key_XYGraph_pre_,name, owner, creater,
				points, otherrule2, otherrule1, id2);
	}

	public XYGraph getXYGraphByKeys(Double oldRtdId) {
		XYGraph rtd = (XYGraph)redisService.get(GlobalConsts.Key_XYGraph_pre_+IDTools.toString(oldRtdId));
		return rtd;
	}

	public XYGraph deleteXYGraph(String oldRtdIdStr) {
		if(StringUtil.isBlank(oldRtdIdStr)) return null;
		return (XYGraph) deletePointGroup(GlobalConsts.Key_XYGraph_pre_,new XYGraph(),oldRtdIdStr);
	}

	public void updateXYGraph(XYGraph rtd) {
		updatePointGroupItem(GlobalConsts.Key_XYGraph_pre_,rtd);
	}

	public XYGraph updateShareRight(Double itemId, List<String> userIdsid, List<String> depIdsid) {
		return (XYGraph) updateShareRight(new XYGraph(),GlobalConsts.Key_XYGraph_pre_,itemId, userIdsid,depIdsid);
	}
	


}
