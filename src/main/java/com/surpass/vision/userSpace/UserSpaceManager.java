package com.surpass.vision.userSpace;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

import com.surpass.vision.XYGraph.XYGraphManager;
import com.surpass.vision.alertData.AlertDataManager;
import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.domain.AlertData;
import com.surpass.vision.domain.Graph;
import com.surpass.vision.domain.HistoryData;
import com.surpass.vision.domain.LineAlertData;
import com.surpass.vision.domain.RealTimeData;
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
import com.surpass.vision.realTimeData.RealTimeDataManager;
import com.surpass.vision.schedule.UpdateGraphDirctory;
import com.surpass.vision.server.ServerManager;
import com.surpass.vision.service.PointGroupService;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.service.UserService;
import com.surpass.vision.service.UserSpaceService;
import com.surpass.vision.tools.TokenTools;
import com.surpass.vision.user.UserManager;

@Component
public class UserSpaceManager {
	private static final Logger LOGGER =  LoggerFactory.getLogger(UserSpaceManager.class);

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
	PointGroupService pointGroupService;

	@Autowired
	UserSpaceDataMapper usdMapper;

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public  UserSpace getUserSpace(Integer uid) {
		LOGGER.info("userKey: " + GlobalConsts.Key_UserSpace_pre_+uid.toString());
		Object obj = redisService.get(GlobalConsts.Key_UserSpace_pre_+uid.toString());
		if(obj == null) return null;
		if(obj instanceof UserSpace)
			return (UserSpace)obj;
		else {
			System.out.println(obj.toString());
			throw new IllegalStateException("缓存中保存的用户空间类型与实际不匹配。");
		}
		
	}


	public void setUserSpace(UserSpace us) {
		if(us == null) {
			LOGGER.info("给的用户空间为空，不能设置。");
			throw new IllegalStateException("给的用户空间为空，不能设置。");
		}
		redisService.set(GlobalConsts.Key_UserSpace_pre_+us.getUser().getId().toString(), us);
		
	}

	public void setUserSpace(Integer uid,UserSpace us) {
		redisService.set(GlobalConsts.Key_UserSpace_pre_+uid.toString(), us);
	}

//	public UserSpace buildUserSpace(Integer uid, String string) {
//		this.buildUserSpace(uid, string)
//		UserSpace us = userService.buildUserSpace(uid, string);
//		this.setUserSpace(us);
//		return us;
//	}

	public List<RealTimeData> getRealTimeData(Integer uid) {
		return (List<RealTimeData>) redisService.get(GlobalConsts.Key_RealTimeData_pre_+uid.toString());
	}
	public void setRealTimeData(Integer uid,List<RealTimeData> rtdl) {
		redisService.set(GlobalConsts.Key_RealTimeData_pre_+uid.toString(),rtdl);
	}
	public UserSpace buildUserSpace(Integer userID, String... token) {
//		// TODO Auto-generated method stub
//		UserSpace us = new UserSpace();
//		// 如果是管理员，就建管理员空间。
		UserInfo user = userManager.getUserByID(userID.toString());
		if(user == null) throw new IllegalStateException("id为"+userID+"的用户不存在，不能为其建立用户空间。");
		if (user.getRole() == 1)
			return buildAdminUserSpace(user);
		LOGGER.info(new Date().toGMTString() + " 开始为用户初始化用户空间..");
		UserSpaceData usd = userSpaceService.getUserSpaceById(userID);
		Integer uid = null;
		if(usd == null) { 
			usd = new UserSpaceData();
			uid = userID;
		}
		UserSpace us =  new UserSpace();
		//UserInfo ui = userManager.getUserByID(uid.toString());//.selectByPrimaryKey(uid);
		us.setUser(user);
		UserRight right;
		//图形
		String graphs = usd.getGraphs();
		Hashtable<String, Graph> gh = null;
		if(StringUtil.isBlank(graphs))
			gh = new Hashtable<String, Graph>();
		else
			gh = graphDataManager.getGraphHashtableByKeys(graphs);
		us.setGraphs(gh);
		//XY图
		String xygraph = usd.getXygraph();
		Hashtable<String, XYGraph> xyGraph = null;
		if(StringUtil.isBlank(xygraph))
			xyGraph = new Hashtable<String, XYGraph>();
		else
			xyGraph = xYGraphManager.getXYGraphHashtabelByKeys(xygraph);
		us.setXyGraph(xyGraph);
		//实时数据
		String realtimedata = usd.getRealtimedata();
		Hashtable<String, RealTimeData> realTimeData = null;
		if(StringUtil.isBlank(realtimedata))
			realTimeData = new Hashtable<String, RealTimeData>();
		else
			realTimeData = realTimeDataManager.getRealTimeDataHashtableByKeys(realtimedata);
		us.setRealTimeData(realTimeData);
		//报警数据
		String alertdata = usd.getAlertdata();
		Hashtable<String, AlertData> alertData = null;
		if(StringUtil.isBlank(alertdata))
			alertData = new Hashtable<String, AlertData>();
		else
			alertData = alertDataManager.getAlertDataHashtableByKeys(alertdata);
		us.setAlertData(alertData);
		// 历史数据
		String historydata = usd.getHistorydata();
		Hashtable<String, HistoryData> historyData = null;
		if(StringUtil.isBlank(historydata))
			historyData = new Hashtable<String, HistoryData>();
		else
			historyData = historyDataManager.getHistoryDataHashtableByKeys(historydata);
		us.setHistoryData(historyData);
		// 直线报警
		String linealertdata = usd.getLineAlertdata();
		Hashtable<String, LineAlertData> lineAlertData = null;
		if(StringUtil.isBlank(linealertdata))
			lineAlertData = new Hashtable<String, LineAlertData>();
		else
			lineAlertData = lineAlertDataManager
				.getLineAlertDataHashtableByKeys(alertdata);
		us.setLineAlertData(lineAlertData);

		String tk = "";
		if (token.length == 1)
			tk = token[0];
		else if (token.length == 0) {
			tk = TokenTools.genToken(userID.toString());
		} else
			throw new IllegalStateException("参数错误，只能有一个token。");
		us.setToken(tk);
		setUserSpace(us);
		return us;
	}

	private UserSpace buildAdminUserSpace(UserInfo user) {
		UserSpaceData usd;// = userSpaceDataMapper.selectByPrimaryKey(userID);
		UserSpace us = new UserSpace();
		us.setUser(user);
		Graph gf = graphDataManager.getAdminGraphHashtable();
		Hashtable<String,Graph> graph = new Hashtable<String,Graph>();
		if(gf!=null) {
			graph.put(gf.getPath()+gf.getName(), gf);
			us.setGraphs(graph);
		}
		Hashtable<String, XYGraph> xyGraph = xYGraphManager.getAdminXYGraphHashtabel();
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
		}catch(IllegalStateException e) {
			LOGGER.info("更新管理员用户空间失败：redis error.");
			e.printStackTrace();
		}
		return us;
	}

	public UserSpace getUserSpaceRigidly(Integer uid) {
		UserSpace ret = this.getUserSpace(uid);
		if(ret == null) {
			ret = this.buildUserSpace(uid);
			if(ret == null) throw new IllegalStateException("创建用户空间失败。");
		}
		return ret;
	}

	public boolean tokenVerification(Integer uid, String token) {
		return TokenTools.verificationToken(token, uid.toString()) 
				&& token.contentEquals(getUserSpaceRigidly(uid).getToken());
	}

}
