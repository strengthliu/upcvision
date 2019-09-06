package com.surpass.vision.userSpace;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

import com.surpass.vision.XYGraph.XYGraphManager;
import com.surpass.vision.alertData.AlertDataManager;
import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.domain.AlertData;
import com.surpass.vision.domain.Graph;
import com.surpass.vision.domain.HistoryData;
import com.surpass.vision.domain.LineAlertData;
import com.surpass.vision.domain.PointGroup;
import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.domain.RealTimeData;
import com.surpass.vision.domain.User;
import com.surpass.vision.domain.UserInfo;
import com.surpass.vision.domain.UserRight;
import com.surpass.vision.domain.UserSpace;
import com.surpass.vision.domain.UserSpaceData;
import com.surpass.vision.domain.XYGraph;
import com.surpass.vision.graph.GraphDataManager;
import com.surpass.vision.graph.GraphManager;
import com.surpass.vision.historyData.HistoryDataManager;
import com.surpass.vision.lineAlertData.LineAlertDataManager;
import com.surpass.vision.mapper.PointGroupDataMapper;
import com.surpass.vision.mapper.UserSpaceDataMapper;
import com.surpass.vision.pointGroup.PointGroupDataManager;
import com.surpass.vision.realTimeData.RealTimeDataManager;
import com.surpass.vision.schedule.UpdateGraphDirctory;
import com.surpass.vision.server.ServerManager;
import com.surpass.vision.service.PointGroupService;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.service.UserService;
import com.surpass.vision.service.UserSpaceService;
import com.surpass.vision.tools.IDTools;
import com.surpass.vision.tools.TokenTools;
import com.surpass.vision.user.UserManager;

@Component
public class UserSpaceManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserSpaceManager.class);

	@Reference
	@Autowired
	RedisService redisService;

	@Reference
	@Autowired
	UserService userService;
	@Reference
	@Autowired
	UserSpaceService userSpaceService;

	GraphManager gm;

	@Autowired
	XYGraphManager xYGraphManager;
	@Autowired
	RealTimeDataManager realTimeDataManager;
	@Autowired
	AlertDataManager alertDataManager;
	@Autowired
	HistoryDataManager historyDataManager;
	@Autowired
	LineAlertDataManager lineAlertDataManager;
	@Autowired
	UserManager userManager;
//	@Autowired
//	ServerManager serverManager;
	@Autowired
	GraphDataManager graphDataManager;
	@Autowired
	GraphManager graphManager;

//	Object graphManager;

	@Autowired
	PointGroupService pointGroupService;

	@Autowired
	UserSpaceDataMapper usdMapper;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String nk = "C:\\dev\\xiangmu\\WebRoot\\tu";
//		String path = "C:\\dev\\xiangmu\\WebRoot";
//		if (nk.contains(path))
//			nk = nk.substring(path.length());
//		while (nk.startsWith("\\"))
//			nk = nk.substring(1);
//		System.out.println(nk);
		Hashtable<String,Object> a = new Hashtable<String,Object>();
		a.put("", 1);
		System.out.println(a.get(""));


	}

	public UserSpace getUserSpace(Double uid) {
		LOGGER.info("userKey: " + GlobalConsts.Key_UserSpace_pre_ + uid.toString());
		Object obj = redisService.get(GlobalConsts.Key_UserSpace_pre_ + IDTools.toString(uid));
		if (obj == null)
			return null;
		if (obj instanceof UserSpace)
			return (UserSpace) obj;
		else {
			System.out.println(obj.toString());
			throw new IllegalStateException("缓存中保存的用户空间类型与实际不匹配。");
		}

	}

	public void setUserSpace(UserSpace us) {
		if (us == null) {
			LOGGER.info("给的用户空间为空，不能设置。");
			throw new IllegalStateException("给的用户空间为空，不能设置。");
		}
		redisService.set(GlobalConsts.Key_UserSpace_pre_ + IDTools.toString(us.getUser().getId()), us);

	}

	public void setUserSpace(Double uid, UserSpace us) {
		redisService.set(GlobalConsts.Key_UserSpace_pre_ + IDTools.toString(uid), us);
		try {
		UserSpace t = (UserSpace)redisService.get(GlobalConsts.Key_UserSpace_pre_ + IDTools.toString(uid));
		// 更新数据库用户空间表
		userSpaceService.updateUserSpace(uid,us);
		}catch(Exception e) {
			e.printStackTrace();
			
		}
	}

//	public UserSpace buildUserSpace(Integer uid, String string) {
//		this.buildUserSpace(uid, string)
//		UserSpace us = userService.buildUserSpace(uid, string);
//		this.setUserSpace(us);
//		return us;
//	}

	public List<RealTimeData> getRealTimeData(Double uid) {
		return (List<RealTimeData>) redisService.get(GlobalConsts.Key_RealTimeData_pre_ + IDTools.toString(uid));
	}

	public void setRealTimeData(Integer uid, List<RealTimeData> rtdl) {
		redisService.set(GlobalConsts.Key_RealTimeData_pre_ + IDTools.toString(uid), rtdl);
	}

	public UserSpace buildUserSpace(Double userID, String... token) {
//		// TODO Auto-generated method stub
//		UserSpace us = new UserSpace();
//		// 如果是管理员，就建管理员空间。
		UserInfo user = userManager.getUserInfoByID(IDTools.toString(userID));
		if (user == null)
			throw new IllegalStateException("id为" + userID + "的用户不存在，不能为其建立用户空间。");
		if (user.getRole() == 1)
			return buildAdminUserSpace(user);
		LOGGER.info(new Date().toGMTString() + " 开始为用户初始化用户空间..");
		// 从数据库中取出用户信息
		UserSpaceData usd = userSpaceService.getUserSpaceById(userID);
		Double uid = null;
		if (usd == null) { // 如果数据库中没有记录，就新建一个
			usd = new UserSpaceData();
			uid = userID;
		}
		UserSpace us = new UserSpace();
		// UserInfo ui =
		// userManager.getUserByID(uid.toString());//.selectByPrimaryKey(uid);
		us.setUser(user);
		UserRight right;
		// 图形设置图形信息
		String graphs = usd.getGraphs();
		Hashtable<String, Graph> gh = null;
		if (StringUtil.isBlank(graphs))
			gh = new Hashtable<String, Graph>();
		else // 
			gh = graphDataManager.getGraphHashtableByKeys(graphs);
		// 重新整理成以目录为key，值是一个图形列表的形式
		Hashtable<String, ArrayList<Graph>> gl = graphDataManager.rebuildGraph(gh);
		us.setGraphs(gl);
		// XY图
		String xygraph = usd.getXygraph();
		Hashtable<String, XYGraph> xyGraph = null;
		if (StringUtil.isBlank(xygraph))
			xyGraph = new Hashtable<String, XYGraph>();
		else
			xyGraph = xYGraphManager.getXYGraphHashtableByKeys(xygraph);
		us.setXyGraph(xyGraph);
		// 实时数据
		String realtimedata = usd.getRealtimedata();
		Hashtable<String, RealTimeData> realTimeData = null;
		if (StringUtil.isBlank(realtimedata))
			realTimeData = new Hashtable<String, RealTimeData>();
		else
			realTimeData = realTimeDataManager.getRealTimeDataHashtableByKeys(realtimedata);
		us.setRealTimeData(realTimeData);
		// 报警数据
		String alertdata = usd.getAlertdata();
		Hashtable<String, AlertData> alertData = null;
		if (StringUtil.isBlank(alertdata))
			alertData = new Hashtable<String, AlertData>();
		else
			alertData = alertDataManager.getAlertDataHashtableByKeys(alertdata);
		us.setAlertData(alertData);
		// 历史数据
		String historydata = usd.getHistorydata();
		Hashtable<String, HistoryData> historyData = null;
		if (StringUtil.isBlank(historydata))
			historyData = new Hashtable<String, HistoryData>();
		else
			historyData = historyDataManager.getHistoryDataHashtableByKeys(historydata);
		us.setHistoryData(historyData);
		// 直线报警
		String linealertdata = usd.getLinealertdata();
		Hashtable<String, LineAlertData> lineAlertData = null;
		if (StringUtil.isBlank(linealertdata))
			lineAlertData = new Hashtable<String, LineAlertData>();
		else
			lineAlertData = lineAlertDataManager.getLineAlertDataHashtableByKeys(alertdata);
		us.setLineAlertData(lineAlertData);

		String tk = "";
		if (token.length == 1)
			tk = token[0];
		else if (token.length == 0) {
			tk = TokenTools.genToken(IDTools.toString(userID));
		} else
			throw new IllegalStateException("参数错误，只能有一个token。");
		us.setToken(tk);
		// 保存用户空间数据到数据库
//		userSpaceService.set
		setUserSpace(us);
		return us;
	}

	private UserSpace buildAdminUserSpace(UserInfo user) {
		UserSpaceData usd;// = userSpaceDataMapper.selectByPrimaryKey(userID);
		UserSpace us = new UserSpace();
		us.setUser(user);
		Graph gf = graphDataManager.getAdminGraphHashtable();
		Hashtable<String, Graph> graph = new Hashtable<String, Graph>();
		if (gf != null) {
			graph.put(gf.getPath() + gf.getName(), gf);
			Hashtable<String, ArrayList<Graph>> gl = graphDataManager.rebuildGraph(graph);
			us.setGraphs(gl);
		}
		// 整理成单路径+文件的方式
		Hashtable<String, ArrayList<Graph>> graphList = graphDataManager.rebuildGraph(graph);
		us.setGraphs(graphList);
		
		Hashtable<String, XYGraph> xyGraph = xYGraphManager.getAdminXYGraphHashtable();
		us.setXyGraph(xyGraph);
		Hashtable<String, RealTimeData> realTimeData = realTimeDataManager.getAdminRealTimeDataHashtable();
		us.setRealTimeData(realTimeData);
		Hashtable<String, AlertData> alertData = alertDataManager.getAdminAlertDataHashtable();
		us.setAlertData(alertData);
		Hashtable<String, HistoryData> historyData = historyDataManager.getAdminHistoryDataHashtable();
		us.setHistoryData(historyData);
		Hashtable<String, LineAlertData> lineAlertData = lineAlertDataManager.getAdminLineAlertDataHashtable();
		us.setLineAlertData(lineAlertData);

		String tk = "";
		tk = TokenTools.genToken(user.toString());
		us.setToken(tk);
		try {
			setUserSpace(us);
		} catch (IllegalStateException e) {
			LOGGER.info("更新管理员用户空间失败：redis error.");
			e.printStackTrace();
		}
		return us;
	}

	public UserSpace getUserSpaceRigidly(Double uid) {
		UserSpace ret = this.getUserSpace(uid);
		if (ret == null) {
			ret = this.buildUserSpace(uid);
			if (ret == null)
				throw new IllegalStateException("创建用户空间失败。");
		}
		return ret;
	}

	public boolean tokenVerification(Double uid, String token) {
		System.out.println(token + " == " + this.getUserSpace(uid).getToken());
		return TokenTools.verificationToken(token, uid.toString())
				&& token.contentEquals(getUserSpaceRigidly(uid).getToken());
	}

	/** ---------------- realtimedata begin ------------------------- **/
	public void updateRealTimeData( RealTimeData rtd,Double oldRtdId) {
		RealTimeData oldRtd = null;
		if(oldRtdId == null || oldRtdId==0)
			oldRtd = new RealTimeData();
		else 
			oldRtd = realTimeDataManager.getRealTimeDataByKeys(oldRtdId);
		if(rtd == null) { // 是删除
			return;
		}
		updateRealTimeData(rtd,oldRtd);
	}
	
	public void updateRealTimeData( RealTimeData rtd,RealTimeData oldRtd) {
		if(rtd == null) rtd = new RealTimeData();// 第一个参数为空，就是要删除后面那个
		// 跟这个RealTimeData对比用户，取出差别
		// 
		@SuppressWarnings("unchecked")
		Set<String> rightChangesaggrandizement = PointGroupDataManager.compareRight(rtd,oldRtd,GlobalConsts.KeyAggrandizement);
		@SuppressWarnings("unchecked")
		Set<String> rightChangesdecreament = PointGroupDataManager.compareRight(rtd,oldRtd,GlobalConsts.KeyDecrement);
		// 根据 这些用户,取的他们UserSpace，更新他们的realTimeData字段，再写回缓存。
		// 增加新授权的用户的空间数据
		Iterator<String> it = rightChangesaggrandizement.iterator();
		while (it.hasNext()) {
			String uids = it.next();
			// 从缓存中取出RealTimeData
			UserSpace us = getUserSpaceRigidly(Double.valueOf(uids));
			Hashtable<String,RealTimeData> hrtd = us.getRealTimeData();
			hrtd.put(IDTools.toString(rtd.getId()), rtd);
			this.setUserSpace(Double.valueOf(uids), us);
		}
		// 去掉删除权限的用户空间数据
		Iterator<String> it1 = rightChangesdecreament.iterator();
		while (it1.hasNext()) {
			String uids = it1.next();
			// 从缓存中取出RealTimeData
			UserSpace us = getUserSpaceRigidly(Double.valueOf(uids));
			Hashtable<String,RealTimeData> hrtd = us.getRealTimeData();
			hrtd.remove(IDTools.toString(oldRtd.getId()));
			this.setUserSpace(Double.valueOf(uids), us);
		}
//		this.realTimeDataManager.updateRealTimeData(rtd);
	}
	/** ---------------- realtimedata end ------------------------- **/

	
	/** ---------------- linealertdata begin ------------------------- **/
	public void updateLineAlertData(LineAlertData rtd, Double oldRtdId) {
		if(rtd == null) return;
		LineAlertData oldRtd = null;
		if(oldRtdId == null || oldRtdId==0)
			oldRtd = new LineAlertData();
		else 
			oldRtd = lineAlertDataManager.getLineAlertDataByKeys(oldRtdId);
		updateLineAlertData(rtd,oldRtd);
	}
	
	public void updateLineAlertData( LineAlertData rtd,LineAlertData oldRtd) {
		// 跟这个RealTimeData对比用户，取出差别
		// 
		Set<String> rightChangesaggrandizement = PointGroupDataManager.compareRight(rtd,oldRtd,GlobalConsts.KeyAggrandizement);
		Set<String> rightChangesdecreament = PointGroupDataManager.compareRight(rtd,oldRtd,GlobalConsts.KeyAggrandizement);
		// 根据 这些用户,取的他们UserSpace，更新他们的realTimeData字段，再写回缓存。
		Iterator<String> it = rightChangesaggrandizement.iterator();
		while (it.hasNext()) {
			String uids = it.next();
			// 从缓存中取出RealTimeData
			UserSpace us = getUserSpaceRigidly(Double.valueOf(uids));
			Hashtable<String,LineAlertData> hrtd = us.getLineAlertData();
			hrtd.put(IDTools.toString(rtd.getId()), rtd);
			this.setUserSpace(Double.valueOf(uids), us);
		}
		this.lineAlertDataManager.updateLineAlertData(rtd);
	}
	/** ---------------- linealertdata end ------------------------- **/
	
	/** ---------------- alertdata begin ------------------------- **/
	public void updateAlertData( AlertData rtd,Double oldRtdId) {
		AlertData oldRtd = null;
		if(oldRtdId == null || oldRtdId==0)
			oldRtd = new AlertData();
		else 
			oldRtd = alertDataManager.getAlertDataByKeys(oldRtdId);
		if(rtd == null) { // 是删除
			return;
		}
		updateAlertData(rtd,oldRtd);
	}
	
	public void updateAlertData( AlertData rtd,AlertData oldRtd) {
		if(rtd == null) rtd = new AlertData();// 第一个参数为空，就是要删除后面那个
		// 跟这个RealTimeData对比用户，取出差别
		// 
		@SuppressWarnings("unchecked")
		Set<String> rightChangesaggrandizement = PointGroupDataManager.compareRight(rtd,oldRtd,GlobalConsts.KeyAggrandizement);
		@SuppressWarnings("unchecked")
		Set<String> rightChangesdecreament = PointGroupDataManager.compareRight(rtd,oldRtd,GlobalConsts.KeyDecrement);
		// 根据 这些用户,取的他们UserSpace，更新他们的realTimeData字段，再写回缓存。
		// 增加新授权的用户的空间数据
		Iterator<String> it = rightChangesaggrandizement.iterator();
		while (it.hasNext()) {
			String uids = it.next();
			// 从缓存中取出RealTimeData
			UserSpace us = getUserSpaceRigidly(Double.valueOf(uids));
			Hashtable<String,AlertData> hrtd = us.getAlertData();
			hrtd.put(IDTools.toString(rtd.getId()), rtd);
			this.setUserSpace(Double.valueOf(uids), us);
		}
		// 去掉删除权限的用户空间数据
		Iterator<String> it1 = rightChangesdecreament.iterator();
		while (it1.hasNext()) {
			String uids = it1.next();
			// 从缓存中取出RealTimeData
			UserSpace us = getUserSpaceRigidly(Double.valueOf(uids));
			Hashtable<String,RealTimeData> hrtd = us.getRealTimeData();
			hrtd.remove(IDTools.toString(oldRtd.getId()));
			this.setUserSpace(Double.valueOf(uids), us);
		}
//		this.alertDataManager.updateAlertData(rtd);
	}
	/** ---------------- alertdata end ------------------------- **/

	/** ---------------- historydata begin ------------------------- **/
	public void updateHistoryData( HistoryData rtd,Double oldRtdId) {
		HistoryData oldRtd = null;
		if(oldRtdId == null || oldRtdId==0)
			oldRtd = new HistoryData();
		else 
			oldRtd = historyDataManager.getHistoryDataByKeys(oldRtdId);
		if(rtd == null) { // 是删除
			return;
		}
		updateHistoryData(rtd,oldRtd);
	}
	
	public void updateHistoryData( HistoryData rtd,HistoryData oldRtd) {
		if(rtd == null) rtd = new HistoryData();// 第一个参数为空，就是要删除后面那个
		// 跟这个RealTimeData对比用户，取出差别
		// 
		@SuppressWarnings("unchecked")
		Set<String> rightChangesaggrandizement = PointGroupDataManager.compareRight(rtd,oldRtd,GlobalConsts.KeyAggrandizement);
		@SuppressWarnings("unchecked")
		Set<String> rightChangesdecreament = PointGroupDataManager.compareRight(rtd,oldRtd,GlobalConsts.KeyDecrement);
		// 根据 这些用户,取的他们UserSpace，更新他们的realTimeData字段，再写回缓存。
		// 增加新授权的用户的空间数据
		Iterator<String> it = rightChangesaggrandizement.iterator();
		while (it.hasNext()) {
			String uids = it.next();
			// 从缓存中取出RealTimeData
			UserSpace us = getUserSpaceRigidly(Double.valueOf(uids));
			Hashtable<String,HistoryData> hrtd = us.getHistoryData();
			hrtd.put(IDTools.toString(rtd.getId()), rtd);
			this.setUserSpace(Double.valueOf(uids), us);
		}
		// 去掉删除权限的用户空间数据
		Iterator<String> it1 = rightChangesdecreament.iterator();
		while (it1.hasNext()) {
			String uids = it1.next();
			// 从缓存中取出RealTimeData
			UserSpace us = getUserSpaceRigidly(Double.valueOf(uids));
			Hashtable<String,RealTimeData> hrtd = us.getRealTimeData();
			hrtd.remove(IDTools.toString(oldRtd.getId()));
			this.setUserSpace(Double.valueOf(uids), us);
		}
//		this.historyDataManager.updateHistoryData(rtd);
	}
	/** ---------------- historydata end ------------------------- **/

	
	/** ---------------- xygraphdata begin ------------------------- **/
	public void updateXYGraph( XYGraph rtd,Double oldRtdId) {
		XYGraph oldRtd = null;
		if(oldRtdId == null || oldRtdId==0)
			oldRtd = new XYGraph();
		else 
			oldRtd = xYGraphManager.getXYGraphByKeys(oldRtdId);
		if(rtd == null) { // 是删除
			return;
		}
		updateXYGraph(rtd,oldRtd);
	}
	
	public void updateXYGraph( XYGraph rtd,XYGraph oldRtd) {
		if(rtd == null) rtd = new XYGraph();// 第一个参数为空，就是要删除后面那个
		// 跟这个RealTimeData对比用户，取出差别
		// 
		@SuppressWarnings("unchecked")
		Set<String> rightChangesaggrandizement = PointGroupDataManager.compareRight(rtd,oldRtd,GlobalConsts.KeyAggrandizement);
		@SuppressWarnings("unchecked")
		Set<String> rightChangesdecreament = PointGroupDataManager.compareRight(rtd,oldRtd,GlobalConsts.KeyDecrement);
		// 根据 这些用户,取的他们UserSpace，更新他们的realTimeData字段，再写回缓存。
		// 增加新授权的用户的空间数据
		Iterator<String> it = rightChangesaggrandizement.iterator();
		while (it.hasNext()) {
			String uids = it.next();
			// 从缓存中取出RealTimeData
			UserSpace us = getUserSpaceRigidly(Double.valueOf(uids));
			Hashtable<String,XYGraph> hrtd = us.getXyGraph();
			hrtd.put(IDTools.toString(rtd.getId()), rtd);
			this.setUserSpace(Double.valueOf(uids), us);
		}
		// 去掉删除权限的用户空间数据
		Iterator<String> it1 = rightChangesdecreament.iterator();
		while (it1.hasNext()) {
			String uids = it1.next();
			// 从缓存中取出RealTimeData
			UserSpace us = getUserSpaceRigidly(Double.valueOf(uids));
			Hashtable<String,RealTimeData> hrtd = us.getRealTimeData();
			hrtd.remove(IDTools.toString(oldRtd.getId()));
			this.setUserSpace(Double.valueOf(uids), us);
		}
//		this.xYGraphManager.updateXYGraph(rtd);
	}
	/** ---------------- xygraphdata end ------------------------- **/

	/** ---------------- graph begin ------------------------- **/
	public void updateGraph( Graph rtd,Double oldRtdId) {
		Graph oldRtd = null;
		if(oldRtdId == null || oldRtdId==0)
			oldRtd = new Graph();
		else 
			oldRtd = graphManager.getGraphByKeys(oldRtdId);
		if(rtd == null) { // 是删除
			return;
		}
		updateGraph(rtd,oldRtd);
	}
	
	public void updateGraph( Graph rtd,Graph oldRtd) {
		if(rtd == null) rtd = new Graph();// 第一个参数为空，就是要删除后面那个
		// 跟这个RealTimeData对比用户，取出差别
		// 
		@SuppressWarnings("unchecked")
		Set<String> rightChangesaggrandizement = PointGroupDataManager.compareRight(rtd,oldRtd,GlobalConsts.KeyAggrandizement);
		@SuppressWarnings("unchecked")
		Set<String> rightChangesdecreament = PointGroupDataManager.compareRight(rtd,oldRtd,GlobalConsts.KeyDecrement);
		// 根据 这些用户,取的他们UserSpace，更新他们的realTimeData字段，再写回缓存。
		// 增加新授权的用户的空间数据
		Iterator<String> it = rightChangesaggrandizement.iterator();
		while (it.hasNext()) {
			String uids = it.next();
			// 从缓存中取出
			UserSpace us = getUserSpaceRigidly(Double.valueOf(uids));
			Hashtable<String, ArrayList<Graph>> hrtd = us.getGraphs();
//			hrtd.put(IDTools.toString(rtd.getId()), rtd);
			this.setUserSpace(Double.valueOf(uids), us);
		}
		// 去掉删除权限的用户空间数据
		Iterator<String> it1 = rightChangesdecreament.iterator();
		while (it1.hasNext()) {
			String uids = it1.next();
			// 从缓存中取出RealTimeData
			UserSpace us = getUserSpaceRigidly(Double.valueOf(uids));
			Hashtable<String,RealTimeData> hrtd = us.getRealTimeData();
			hrtd.remove(IDTools.toString(oldRtd.getId()));
			this.setUserSpace(Double.valueOf(uids), us);
		}
//		this.graphManager.updateGraph(rtd);
	}
	/** ---------------- graph end ------------------------- **/

	public boolean deleteUserSpace(Double uid) {
		UserSpace us = this.getUserSpaceRigidly(uid);
		if(!us.canDelete()) return false;
		Hashtable<String,RealTimeData> hr = us.getRealTimeData();
		Enumeration<?> e = hr.elements();
		while(e.hasMoreElements()) {
			try {
				PointGroup p = (PointGroup) e.nextElement();
				List<String> ls = p.removeUser(p.getId());
				realTimeDataManager.updateShareRight(p.getId(),ls);
			}catch(Exception ex) {	}
		}
		
		Hashtable<String,AlertData> ha = us.getAlertData();
		e = ha.elements();
		while(e.hasMoreElements()) {
			try {
				PointGroup p = (PointGroup) e.nextElement();
				List<String> ls = p.removeUser(p.getId());
				alertDataManager.updateShareRight(p.getId(),ls);
			}catch(Exception ex) {	}
		}

		Hashtable<String,ArrayList<Graph>> hg = us.getGraphs();
		e = hg.elements();
		while(e.hasMoreElements()) {
			try {
				PointGroup p = (PointGroup) e.nextElement();
				List<String> ls = p.removeUser(p.getId());
				this.graphDataManager.updateShareRight(p.getId(),ls);
			}catch(Exception ex) {	}
		}

		Hashtable<String,HistoryData> hh = us.getHistoryData();
		e = hh.elements();
		while(e.hasMoreElements()) {
			try {
				PointGroup p = (PointGroup) e.nextElement();
				List<String> ls = p.removeUser(p.getId());
				historyDataManager.updateShareRight(p.getId(),ls);
			}catch(Exception ex) {	}
		}

		Hashtable<String,LineAlertData> hl = us.getLineAlertData();
		e = hl.elements();
		while(e.hasMoreElements()) {
			try {
				PointGroup p = (PointGroup) e.nextElement();
				List<String> ls = p.removeUser(p.getId());
				lineAlertDataManager.updateShareRight(p.getId(),ls);
			}catch(Exception ex) {	}
		}

		Hashtable<String,XYGraph> hx = us.getXyGraph();
		e = hx.elements();
		while(e.hasMoreElements()) {
			try {
				PointGroup p = (PointGroup) e.nextElement();
				List<String> ls = p.removeUser(p.getId());
				xYGraphManager.updateShareRight(p.getId(),ls);
			}catch(Exception ex) {	}
		}

		// 删除数据库
		this.userSpaceService.deleteUserSpace(uid);
		// 删除缓存
		this.redisService.delete(GlobalConsts.Key_UserSpace_pre_+IDTools.toString(uid));
		return true;
	}

	private boolean deleteUserSpace(Hashtable hp) {
		Enumeration<?> e = hp.elements();
		while(e.hasMoreElements()) {
			try {
				PointGroup p = (PointGroup) e.nextElement();
				List<User> lu = p.getSharedUsers();
				List<String> ls = p.removeUser(p.getId());
//				IDTools.splitID(str, splitChar)
				AlertData rtd = alertDataManager.updateShareRight(p.getId(),ls);
			}catch(Exception ex) {	return false;}
		}
		return true;
	}


}
