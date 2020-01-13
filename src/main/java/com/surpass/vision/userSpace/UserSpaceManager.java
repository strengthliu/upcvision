package com.surpass.vision.userSpace;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.surpass.vision.domain.DepartmentInfo;
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

	@Value("${upc.adminName}")
	private String adminName;
	
	@Value("${upc.adminUserName}")
	private String adminUserName;
	
	@Value("${upc.adminPortrait}")
	private String adminPortrait;

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
		if (obj instanceof UserSpaceData)
			return buildUserSpace((UserSpaceData) obj,((UserSpaceData)obj).getToken());
		else {
			System.out.println(obj.toString());
			throw new IllegalStateException("缓存中保存的用户空间类型与实际不匹配。");
		}

	}

	public UserSpace getUserSpaceWithDepartData(Double uid) {
		LOGGER.info("userKey: " + GlobalConsts.Key_UserSpace_pre_ + uid.toString());
		UserSpace us = getUserSpace(uid);
		if (us==null) {
			us = this.getUserSpaceRigidly(uid);
		}
		Graph g = us.getGraph();

		// 增加部门权限内的信息
		String graphsFromDeps = null;
		DepartmentInfo di = null;

		// 如果是管理员或是管理员权限，就直接返回。
		UserInfo ui = us.getUser();
		if(IDTools.toString(ui.getId()).contentEquals(GlobalConsts.UserAdminID) || ui.getRole()==GlobalConsts.UserRoleAdmin) 
			return us;
		
//		// 如果是管理员或是管理员权限，就设置部门为超级部门，有所有信息。不需要，管理员就有所有权限了。
//		if(IDTools.toString(ui.getId()).contentEquals(GlobalConsts.UserAdminID) || ui.getRole()==GlobalConsts.UserRoleAdmin) {
//			graphsFromDeps = userManager.getAllDepartIDString();
////			di = userManager// 超级部门
//		}else {
//			di = userManager.getDepartmentInfoByID(us.getUser().getDepart());
//			graphsFromDeps = di.getGraphs();
//		}
		di = userManager.getDepartmentInfoByID(us.getUser().getDepart());
		graphsFromDeps = di.getGraphs();
		
		// 取部门图形
		Graph gd = null;
		if (StringUtil.isBlank(graphsFromDeps))
			gd = new Graph();
		else // 
			gd = graphManager.getGraphsByKeys(graphsFromDeps);
		if(g!=null) {
			g.addOrUpdateChild(gd);
			us.setGraph(g);
		}else {
			us.setGraph(gd);
		}
		
		// XY图
		Hashtable<String, XYGraph> xyGraph = us.getXyGraph();
		Hashtable<String, XYGraph> xyGraphFromDepart = new Hashtable<String, XYGraph>();
		String xygraph = di.getXygraph();
		if (StringUtil.isBlank(xygraph))
			xyGraphFromDepart = new Hashtable<String, XYGraph>();
		else
			xyGraphFromDepart = xYGraphManager.getXYGraphHashtableByKeys(xygraph);
		xyGraph.putAll(xyGraphFromDepart);
//			us.setXyGraph(xyGraph);

		// realTimeData
		Hashtable<String, RealTimeData> realTimeData = us.getRealTimeData();
		Hashtable<String, RealTimeData> realTimeDataFromDepart = null;
		String realtimedata = di.getLinealertdata();
		if (StringUtil.isBlank(realtimedata))
			realTimeDataFromDepart = new Hashtable<String, RealTimeData>();
		else
			realTimeDataFromDepart = realTimeDataManager.getRealTimeDataHashtableByKeys(realtimedata);
		realTimeData.putAll(realTimeDataFromDepart);

		// history图
		Hashtable<String, HistoryData> historyData = us.getHistoryData();
		Hashtable<String, HistoryData> historyDataFromDepart = null;
		String historydata = di.getLinealertdata();
		if (StringUtil.isBlank(historydata))
			historyDataFromDepart = new Hashtable<String, HistoryData>();
		else
			historyDataFromDepart = historyDataManager.getHistoryDataHashtableByKeys(historydata);
		historyData.putAll(historyDataFromDepart);

		// LieAlert图
		Hashtable<String, LineAlertData> lineAlertData = us.getLineAlertData();
		Hashtable<String, LineAlertData> lineAlertDataFromDepart = null;
		String linealertdata = di.getLinealertdata();
		if (StringUtil.isBlank(linealertdata))
			lineAlertDataFromDepart = new Hashtable<String, LineAlertData>();
		else
			lineAlertDataFromDepart = lineAlertDataManager.getLineAlertDataHashtableByKeys(linealertdata);
		lineAlertData.putAll(lineAlertDataFromDepart);

		// Alert图
		Hashtable<String, AlertData> alertData = us.getAlertData();
		Hashtable<String, AlertData> alertDataFromDepart = null;
		String alertdata = di.getAlertdata();
		if (StringUtil.isBlank(alertdata))
			alertDataFromDepart = new Hashtable<String, AlertData>();
		else
			alertDataFromDepart = alertDataManager.getAlertDataHashtableByKeys(alertdata);
		alertData.putAll(alertDataFromDepart);


		return us;

	}

	public void setUserSpace(UserSpaceData us) {
		if (us == null) {
			LOGGER.info("给的用户空间为空，不能设置。");
			throw new IllegalStateException("给的用户空间为空，不能设置。");
		}
		if(us instanceof UserSpace)
			((UserSpace)us).updateUserSpaceData();
		UserSpaceData ust = us.clone();
		redisService.set(GlobalConsts.Key_UserSpace_pre_ + IDTools.toString(us.getUid()), ust);
	}

	public void setUserSpaceWithStorage(UserSpaceData us) {
		if(us instanceof UserSpace)
			((UserSpace)us).updateUserSpaceData();
		UserSpaceData usd = (UserSpaceData)us.clone();
		redisService.set(GlobalConsts.Key_UserSpace_pre_ + IDTools.toString(us.getUid()), usd);
		try {
		// 更新数据库用户空间表
		userSpaceService.updateUserSpace(us.getUid(),usd);
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

//	public List<RealTimeData> getRealTimeData(Double uid) {
//		return (List<RealTimeData>) redisService.get(GlobalConsts.Key_RealTimeData_pre_ + IDTools.toString(uid));
//	}
//
//	public void setRealTimeData(Integer uid, List<RealTimeData> rtdl) {
//		redisService.set(GlobalConsts.Key_RealTimeData_pre_ + IDTools.toString(uid), rtdl);
//	}

	public UserSpace buildUserSpace(UserSpaceData usd, String... token) {
		if (usd == null) { 
			return null;
		}
		UserSpace us = new UserSpace();
		 UserInfo user = userManager.getUserInfoByID(IDTools.toString(usd.getUid()));
		// userManager.getUserByID(uid.toString());//.selectByPrimaryKey(uid);
		us.setUser(user);
		UserRight right;
		// 图形设置图形信息
		String graphs = usd.getGraphs();
		Graph g = null;
		if (StringUtil.isBlank(graphs)) {
//			g = new Graph();
		}
		else // 
			g = graphManager.getGraphsByKeys(graphs);
		us.setGraph(g);
		us.setGraphs(usd.getGraphs());
		
		// XY图
		String xygraph = usd.getXygraph();
		Hashtable<String, XYGraph> xyGraph = null;
		if (StringUtil.isBlank(xygraph))
			xyGraph = new Hashtable<String, XYGraph>();
		else
			xyGraph = xYGraphManager.getXYGraphHashtableByKeys(xygraph);
		us.setXyGraph(xyGraph);
		us.setXygraph(usd.getXygraph());
		
		// 实时数据
		String realtimedata = usd.getRealtimedata();
		Hashtable<String, RealTimeData> realTimeData = null;
		if (StringUtil.isBlank(realtimedata))
			realTimeData = new Hashtable<String, RealTimeData>();
		else
			realTimeData = realTimeDataManager.getRealTimeDataHashtableByKeys(realtimedata);
		us.setRealTimeData(realTimeData);
		usd.setRealtimedata(usd.getRealtimedata());
		
		// 报警数据
		String alertdata = usd.getAlertdata();
		Hashtable<String, AlertData> alertData = null;
		if (StringUtil.isBlank(alertdata))
			alertData = new Hashtable<String, AlertData>();
		else
			alertData = alertDataManager.getAlertDataHashtableByKeys(alertdata);
		us.setAlertData(alertData);
		us.setAlertdata(usd.getAlertdata());
		
		// 历史数据
		String historydata = usd.getHistorydata();
		Hashtable<String, HistoryData> historyData = null;
		if (StringUtil.isBlank(historydata))
			historyData = new Hashtable<String, HistoryData>();
		else
			historyData = historyDataManager.getHistoryDataHashtableByKeys(historydata);
		us.setHistoryData(historyData);
		us.setHistorydata(usd.getHistorydata());
		
		// 直线报警
		String linealertdata = usd.getLinealertdata();
		Hashtable<String, LineAlertData> lineAlertData = null;
		if (StringUtil.isBlank(linealertdata))
			lineAlertData = new Hashtable<String, LineAlertData>();
		else
			lineAlertData = lineAlertDataManager.getLineAlertDataHashtableByKeys(alertdata);
		us.setLineAlertData(lineAlertData);
		us.setLinealertdata(usd.getLinealertdata());

		String tk = "";
		if (token.length == 1)
			tk = token[0];
		else if (token.length == 0) {
			tk = TokenTools.genToken(IDTools.toString(user.getId()));
		} else
			throw new IllegalStateException("参数错误，只能有一个token。");
		us.setToken(tk);
		us.setUid(usd.getUid());
		return us;	
}

	public UserInfo getAdminUserInfo() {
		UserInfo user = new UserInfo();
		user.setId(Double.valueOf(GlobalConsts.UserAdminID));
		user.setName(adminName);
		user.setUsername(adminUserName);
		user.setPhoto(adminPortrait);
		user.setRole(GlobalConsts.UserRoleAdmin);
		return user;
	}
	/**
	 * 清空用户空间表和缓存，引到buildUserSpace，相当于刷新userSpace的数据库记录。
	 * @param userID
	 * @param token
	 * @return
	 */
	public UserSpace buildUserSpace(Double userID, String... token) {
		// 如果是超级管理员，就直接建管理员空间。
		if(userID.doubleValue() == Double.valueOf(GlobalConsts.UserAdminID).doubleValue()) {
			UserInfo user = getAdminUserInfo();
			
			if(token==null||token.length==0)
				return buildAdminUserSpace(user,null);
			else
				return buildAdminUserSpace(user,token[0]);
		}
		
		UserInfo user = userManager.getUserInfoByID(IDTools.toString(userID));
		if (user == null) {
			System.out.println("id为" + userID + "的用户不存在，不能为其建立用户空间。");
			// TODO: 由于数据不一致导致。有图形等PointGroup里，存在该用户为分享用户。
			// TODO: 发消息给管理员，修复数据。
			throw new IllegalStateException("id为" + userID + "的用户不存在，不能为其建立用户空间。");
		}
		if (user.getRole() == 1) {
			if(token==null||token.length==0)
				return buildAdminUserSpace(user,null);
			else
				return buildAdminUserSpace(user,token[0]);
		}
		
		LOGGER.info(new Date().toGMTString() + " 开始为用户初始化用户空间..");
		// 从数据库中取出用户信息
		UserSpaceData usd = userSpaceService.getUserSpaceById(userID);
		if(usd == null) {
			usd = new UserSpaceData();
			usd.setUid(userID);
			this.setUserSpaceWithStorage(usd);
		}
		UserSpace ret = buildUserSpace(usd,token);
		usd.setToken(ret.getToken());
		setUserSpace(usd);
		return ret;
			
	}

	private UserSpace buildAdminUserSpace(UserInfo user,String tk) {
		System.out.println(" buildAdminUserSpace 1 => "+new Date(System.currentTimeMillis()).toLocaleString());
		UserSpaceData usd = new UserSpaceData();// = userSpaceDataMapper.selectByPrimaryKey(userID);
		UserSpace us = new UserSpace();
		us.setUid(user.getId());
		usd.setUid(user.getId());
		us.setUser(user);
		System.out.println(" buildAdminUserSpace 2 => "+new Date(System.currentTimeMillis()).toLocaleString());
		Graph gf = graphDataManager.getAdminGraph();
		System.out.println(" buildAdminUserSpace 3 => "+new Date(System.currentTimeMillis()).toLocaleString());
		us.setGraph(gf);
//		usd.setGraphs(graphs);
		System.out.println(" buildAdminUserSpace 4 => "+new Date(System.currentTimeMillis()).toLocaleString());
		Hashtable<String, XYGraph> xyGraph = xYGraphManager.getAdminXYGraphHashtable();
		System.out.println(" buildAdminUserSpace 5 => "+new Date(System.currentTimeMillis()).toLocaleString());
		us.setXyGraph(xyGraph);
		System.out.println(" buildAdminUserSpace 6 => "+new Date(System.currentTimeMillis()).toLocaleString());
		Hashtable<String, RealTimeData> realTimeData = realTimeDataManager.getAdminRealTimeDataHashtable();
		System.out.println(" buildAdminUserSpace 7 => "+new Date(System.currentTimeMillis()).toLocaleString());
		us.setRealTimeData(realTimeData);
		System.out.println(" buildAdminUserSpace 8 => "+new Date(System.currentTimeMillis()).toLocaleString());
		Hashtable<String, AlertData> alertData = alertDataManager.getAdminAlertDataHashtable();
		System.out.println(" buildAdminUserSpace 9 => "+new Date(System.currentTimeMillis()).toLocaleString());
		us.setAlertData(alertData);
		System.out.println(" buildAdminUserSpace 10 => "+new Date(System.currentTimeMillis()).toLocaleString());
		Hashtable<String, HistoryData> historyData = historyDataManager.getAdminHistoryDataHashtable();
		System.out.println(" buildAdminUserSpace 11 => "+new Date(System.currentTimeMillis()).toLocaleString());
		us.setHistoryData(historyData);
		System.out.println(" buildAdminUserSpace 12 => "+new Date(System.currentTimeMillis()).toLocaleString());
		Hashtable<String, LineAlertData> lineAlertData = lineAlertDataManager.getAdminLineAlertDataHashtable();
		System.out.println(" buildAdminUserSpace 13 => "+new Date(System.currentTimeMillis()).toLocaleString());
		us.setLineAlertData(lineAlertData);

//		String tk = "";
		if(tk==null) {
			tk = TokenTools.genToken(IDTools.toString(user.getId()));
		}
		us.setToken(tk);
		usd.setToken(tk);
		try {
			setUserSpace(usd);
		} catch (IllegalStateException e) {
			LOGGER.info("更新管理员用户空间失败：redis error.");
			e.printStackTrace();
		}
		return us;
	}

	public UserSpace getUserSpaceRigidly(Double uid) {
		// 先从缓存里取
		UserSpace ret = this.getUserSpace(uid);
		if (ret == null) {
			ret = this.buildUserSpace(uid);
			if (ret == null)
				throw new IllegalStateException("创建用户空间失败。");
		}
		return ret;
	}

	public boolean tokenVerification(Double uid, String token) {
//		System.out.println(token + " == " + this.getUserSpace(uid).getToken());
		boolean ret = true;
		boolean r1 =  TokenTools.verificationToken(token, IDTools.toString(uid));
		boolean r2 =  token.contentEquals(getUserSpaceRigidly(uid).getToken());
		return ret && r1 && r2;
	}
	
	/***********************************************************
	 * TODO:  下面这些update权限的方法，可以抽象一下，整理出几个方法。
	 */

	/** ---------------- realtimedata begin ------------------------- **/
	public void updateRealTimeData( RealTimeData rtd,Double oldRtdId) {
		RealTimeData oldRtd = null;
		if(oldRtdId == null || oldRtdId==0)
			oldRtd = new RealTimeData();
		else 
			oldRtd = realTimeDataManager.getRealTimeDataByKeys(oldRtdId);
		if(rtd == null) { // 是删除
			rtd = new RealTimeData();
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
			this.setUserSpaceWithStorage(us);
		}
		// 去掉删除权限的用户空间数据
		Iterator<String> it1 = rightChangesdecreament.iterator();
		while (it1.hasNext()) {
			String uids = it1.next();
			// 从缓存中取出RealTimeData
			UserSpace us = getUserSpaceRigidly(Double.valueOf(uids));
			Hashtable<String,RealTimeData> hrtd = us.getRealTimeData();
			hrtd.remove(IDTools.toString(oldRtd.getId()));
			this.setUserSpaceWithStorage(us);
		}
	}
	/** ---------------- realtimedata end ------------------------- **/

	
	/** ---------------- linealertdata begin ------------------------- **/
	public void updateLineAlertData(LineAlertData rtd, Double oldRtdId) {
		if(rtd == null) rtd = new LineAlertData();
		LineAlertData oldRtd = null;
		if(oldRtdId == null || oldRtdId==0)
			oldRtd = new LineAlertData();
		else 
			oldRtd = lineAlertDataManager.getLineAlertDataByKeys(oldRtdId);
		updateLineAlertData(rtd,oldRtd);
	}
	
	public void updateLineAlertData( LineAlertData rtd,LineAlertData oldRtd) {
		if(rtd==null) rtd = new LineAlertData();
		// 跟这个RealTimeData对比用户，取出差别
		// 
		Set<String> rightChangesaggrandizement = PointGroupDataManager.compareRight(rtd,oldRtd,GlobalConsts.KeyAggrandizement);
		Set<String> rightChangesdecreament = PointGroupDataManager.compareRight(rtd,oldRtd,GlobalConsts.KeyDecrement);
		// 根据 这些用户,取的他们UserSpace，更新他们的realTimeData字段，再写回缓存。
		Iterator<String> it = rightChangesaggrandizement.iterator();
		while (it.hasNext()) {
			String uids = it.next();
			// 从缓存中取出RealTimeData
			UserSpace us = getUserSpaceRigidly(Double.valueOf(uids));
			Hashtable<String,LineAlertData> hrtd = us.getLineAlertData();
			hrtd.put(IDTools.toString(rtd.getId()), rtd);
			this.setUserSpaceWithStorage(us);
		}
		// 去掉删除权限的用户空间数据
		Iterator<String> it1 = rightChangesdecreament.iterator();
		while (it1.hasNext()) {
			String uids = it1.next();
			// 从缓存中取出RealTimeData
			UserSpace us = getUserSpaceRigidly(Double.valueOf(uids));
			Hashtable<String,LineAlertData> hrtd = us.getLineAlertData();
			hrtd.remove(IDTools.toString(oldRtd.getId()));
			this.setUserSpaceWithStorage(us);
		}//		if(rtd!=null)
//			this.lineAlertDataManager.updateLineAlertData(rtd);
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
			rtd = new AlertData();
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
			this.setUserSpaceWithStorage(us);
		}
		// 去掉删除权限的用户空间数据
		Iterator<String> it1 = rightChangesdecreament.iterator();
		while (it1.hasNext()) {
			String uids = it1.next();
			// 从缓存中取出RealTimeData
			UserSpace us = getUserSpaceRigidly(Double.valueOf(uids));
			Hashtable<String,AlertData> hrtd = us.getAlertData();
			hrtd.remove(IDTools.toString(oldRtd.getId()));
			this.setUserSpaceWithStorage(us);
		}
//		if(rtd!=null)
//			this.alertDataManager.updateAlertData(rtd);
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
			rtd = new HistoryData();
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
			this.setUserSpaceWithStorage(us);
		}
		// 去掉删除权限的用户空间数据
		Iterator<String> it1 = rightChangesdecreament.iterator();
		while (it1.hasNext()) {
			String uids = it1.next();
			// 从缓存中取出RealTimeData
			UserSpace us = getUserSpaceRigidly(Double.valueOf(uids));
			Hashtable<String,HistoryData> hrtd = us.getHistoryData();
			hrtd.remove(IDTools.toString(oldRtd.getId()));
			this.setUserSpaceWithStorage(us);
		}
//		if(rtd!=null)
//			this.historyDataManager.updateHistoryData(rtd);
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
			rtd = new XYGraph();
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
			this.setUserSpaceWithStorage(us);
		}
		// 去掉删除权限的用户空间数据
		Iterator<String> it1 = rightChangesdecreament.iterator();
		while (it1.hasNext()) {
			String uids = it1.next();
			// 从缓存中取出RealTimeData
			UserSpace us = getUserSpaceRigidly(Double.valueOf(uids));
			Hashtable<String,XYGraph> hrtd = us.getXyGraph();
			hrtd.remove(IDTools.toString(oldRtd.getId()));
			this.setUserSpaceWithStorage(us);
		}
//		if(rtd!=null)
//			this.xYGraphManager.updateXYGraph(rtd);
	}
	/** ---------------- xygraphdata end ------------------------- **/

	/** ---------------- graph begin ------------------------- **/
	/**
	 * 更新用户空间中的图形数据
	 * @param rtd 要更新成的样子
	 * @param oldRtdId 旧图形id
	 */
	public void updateGraph( Graph rtd,Double oldRtdId) {
		Graph oldRtd = null;
		if(oldRtdId == null || oldRtdId==0)
			oldRtd = new Graph();
		else 
			oldRtd = graphManager.getGraphByKeys(oldRtdId);
		if(rtd == null) { // 是删除
			/**
			 * 现在允许删除图形了。
			 * @author Administrator
			 * @since 2019.11.29
			 */
//			throw new IllegalStateException("UserSpaceManager.updateGraph : 禁止删除图形。");
		}
		updateGraph(rtd,oldRtd);
	}
	
	
	
	public void updateGraph( Graph rtd,Graph oldRtd) {
		if(rtd == null) rtd = new Graph();// 第一个参数为空，就是要删除后面那个
		@SuppressWarnings("unchecked")
		Set<String> rightChangesaggrandizement = PointGroupDataManager.compareRight(rtd,oldRtd,GlobalConsts.KeyAggrandizement);
		@SuppressWarnings("unchecked")
		Set<String> rightChangesdecreament = PointGroupDataManager.compareRight(rtd,oldRtd,GlobalConsts.KeyDecrement);

		/**
		 * 问题：
		 * 当图形的数据与用户空间的数据不一致时，这里就会出现错误。
		 * 用户空间都是临时数据，只用于取用户空间时使用，是真正为了高速使用的方案，应该确保更新。
		 * 但用户空间数据库数据，
		 */
		// force更新模式。一定更新用户空间，避免不一致导致的问题。
		boolean forceSyncData = false;
		if(forceSyncData) {
			// 取出rtd中的每个用户空间，确保每一个都有这个图
			List<User> lusr = rtd.getSharedUsers();
			for(int ilusr=0;ilusr<lusr.size();ilusr++) {
				UserSpaceData usd = this.getUserSpaceRigidly(lusr.get(ilusr).getId());
				String graphids = usd.getGraphs();
				if(!StringUtil.isBlank(graphids)) {
					if(!graphids.contains(IDTools.toString(rtd.getId()))) {
						String []ida = IDTools.splitID(graphids);
						String []idat = new String[ida.length+1];
						System.arraycopy(ida, 0, idat, 0, ida.length);
						idat[ida.length] = IDTools.toString(rtd.getId());
						graphids = IDTools.merge(idat);
					}
				}else {
					graphids = IDTools.toString(rtd.getId());
				}
				usd.setGraphs(graphids);
				setUserSpaceWithStorage(usd);
			}
			
			// 取出old中的每个用户空间，确保该减去的都没有这个图。
			// 从一个图，无法确定哪些用户无这个图的权限，除非遍历所有用户。
			// 如果有办法了，这里就直接return，不用下面的了。;
		}else {
			// 根据 这些用户,取的他们UserSpace，更新他们的realTimeData字段，再写回缓存。
			// 增加新授权的用户的空间数据
			Iterator<String> it = rightChangesaggrandizement.iterator();
			while (it.hasNext()) {
				String uids = it.next();
				// 从缓存中取出
				String idstr = IDTools.toString(oldRtd.getId());
//				UserSpaceData usd = (UserSpaceData)redisService.get(GlobalConsts.Key_UserSpace_pre_ + IDTools.toString(uids));
				UserSpaceData usd = getUserSpaceRigidly(Double.valueOf(uids));
				String graphids = usd.getGraphs();
				if(!StringUtil.isBlank(graphids)) {
					if(!graphids.contains(idstr)) {
						String []ida = IDTools.splitID(graphids);
						String []idat = new String[ida.length+1];
						System.arraycopy(ida, 0, idat, 0, ida.length);
						idat[ida.length] = idstr;
						graphids = IDTools.merge(idat);
					}
				}else {
					graphids = idstr;
				}
				usd.setGraphs(graphids);
				this.setUserSpaceWithStorage(usd);
				// 把空目录补整齐，也不行，因为下一次修改，就会把这目录下的东西带过来了。
			}			
		}

		// 去掉删除权限的用户空间数据
		Iterator<String> it1 = rightChangesdecreament.iterator();
		while (it1.hasNext()) {
			String uids = it1.next();
			// 从缓存中取出
			String idstr = IDTools.toString(oldRtd.getId());
//			UserSpaceData usd = (UserSpaceData)redisService.get(GlobalConsts.Key_UserSpace_pre_ + IDTools.toString(uids));
			UserSpaceData usd = getUserSpaceRigidly(Double.valueOf(uids));
			String graphids = usd.getGraphs();
			if(!StringUtil.isBlank(graphids) && graphids.contains(idstr)) {
				String []ida = IDTools.splitID(graphids);
				String []idat = new String[ida.length-1];
				int iidat = 0;
				for(int iida=0;iida<ida.length;iida++) {
					if(!ida[iida].contentEquals(idstr)) {
						idat[iidat] = ida[iida];
						iidat++;
					}
				}
				graphids = IDTools.merge(idat);
			}
			usd.setGraphs(graphids);
			this.setUserSpaceWithStorage(usd);
		}
	}
	/** ---------------- graph end ------------------------- **/

	private void updateGraphInDeleteUser(Graph g,Double uid) {
		
		try {
			PointGroup p = (PointGroup) g;
			List<String> ls = p.removeUser(uid);
			this.graphDataManager.updateShareRight(p.getId(),ls);
			if(g.getChildren()!=null) {
				Enumeration<Graph> eg = g.getChildren().elements();
				while(eg.hasMoreElements()) {
					Graph _g = eg.nextElement();
					updateGraphInDeleteUser(_g,uid);
				}
			}
		}catch(Exception ex) {	}
	}
	
	public boolean deleteUserSpace(Double uid) {
		UserSpace us = this.getUserSpaceRigidly(uid);
		if(!us.canDelete()) return false;
		Hashtable<String,RealTimeData> hr = us.getRealTimeData();
		Enumeration<?> e = hr.elements();
		while(e.hasMoreElements()) {
			try {
				PointGroup p = (PointGroup) e.nextElement();
				List<String> ls = p.removeUser(p.getId());
				List<String> dls = p.removeDepart(p.getId());
				realTimeDataManager.updateShareRight(p.getId(),ls,dls);
			}catch(Exception ex) {	}
		}
		
		Hashtable<String,AlertData> ha = us.getAlertData();
		e = ha.elements();
		while(e.hasMoreElements()) {
			try {
				PointGroup p = (PointGroup) e.nextElement();
				List<String> ls = p.removeUser(p.getId());
				List<String> dls = p.removeDepart(p.getId());
				alertDataManager.updateShareRight(p.getId(),ls,dls);
			}catch(Exception ex) {	}
		}

		Graph hg = us.getGraph();
		updateGraphInDeleteUser(hg,uid);

		Hashtable<String,HistoryData> hh = us.getHistoryData();
		e = hh.elements();
		while(e.hasMoreElements()) {
			try {
				PointGroup p = (PointGroup) e.nextElement();
				List<String> ls = p.removeUser(p.getId());
				List<String> dls = p.removeDepart(p.getId());
				historyDataManager.updateShareRight(p.getId(),ls,dls);
			}catch(Exception ex) {	}
		}

		Hashtable<String,LineAlertData> hl = us.getLineAlertData();
		e = hl.elements();
		while(e.hasMoreElements()) {
			try {
				PointGroup p = (PointGroup) e.nextElement();
				List<String> ls = p.removeUser(p.getId());
				List<String> dls = p.removeDepart(p.getId());
				lineAlertDataManager.updateShareRight(p.getId(),ls,dls);
			}catch(Exception ex) {	}
		}

		Hashtable<String,XYGraph> hx = us.getXyGraph();
		e = hx.elements();
		while(e.hasMoreElements()) {
			try {
				PointGroup p = (PointGroup) e.nextElement();
				List<String> ls = p.removeUser(p.getId());
				List<String> dls = p.removeDepart(p.getId());
				xYGraphManager.updateShareRight(p.getId(),ls,dls);
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
				List<String> dls = p.removeDepart(p.getId());
//				IDTools.splitID(str, splitChar)
				AlertData rtd = alertDataManager.updateShareRight(p.getId(),ls,dls);
			}catch(Exception ex) {	return false;}
		}
		return true;
	}


}
