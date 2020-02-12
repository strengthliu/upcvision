package com.surpass.vision.pointGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.domain.DepartmentInfo;
import com.surpass.vision.domain.Graph;
import com.surpass.vision.domain.PointGroup;
import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.domain.RealTimeData;
import com.surpass.vision.domain.User;
import com.surpass.vision.domain.XYGraph;
import com.surpass.vision.server.DataViewer;
import com.surpass.vision.server.Point;
import com.surpass.vision.server.ServerManager;
import com.surpass.vision.server.ServerPoint;
import com.surpass.vision.service.PointGroupService;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.tools.IDTools;
import com.surpass.vision.user.UserManager;

@Component
public class PointGroupDataManager {
	@Reference
	@Autowired
	RedisService redisService;

	@Autowired
	PointGroupService pointGroupService;

//	@Autowired
//	ServerManager serverManager;

	@Autowired
	UserManager userManager;
	
	private void setPointGroupIDsFromDepart(DepartmentInfo di,String ids,String type) {
		switch(type) {
		case GlobalConsts.Key_AlertData_pre_:
			di.setAlertdata(ids);
			break;
		case GlobalConsts.Key_Graph_pre_:
			di.setGraphs(ids);
			break;
		case GlobalConsts.Key_HistoryData_pre_:
			di.setHistorydata(ids);
			break;
		case GlobalConsts.Key_LineAlertData_pre_:
			di.setLinealertdata(ids);
			break;
		case GlobalConsts.Key_RealTimeData_pre_:
			di.setRealtimedata(ids);
			break;
		case GlobalConsts.Key_XYGraph_pre_:
			di.setXygraph(ids);
			break;
		}
	}
	
	private ArrayList<String> getPointGroupIDsFromDepart(DepartmentInfo di,String type) {
		String pointGroupIDs = "";
		switch(type) {
		case GlobalConsts.Key_AlertData_pre_:
			pointGroupIDs = di.getAlertdata();
			break;
		case GlobalConsts.Key_Graph_pre_:
			pointGroupIDs = di.getGraphs();
			break;
		case GlobalConsts.Key_HistoryData_pre_:
			pointGroupIDs = di.getHistorydata();
			break;
		case GlobalConsts.Key_LineAlertData_pre_:
			pointGroupIDs = di.getLinealertdata();
			break;
		case GlobalConsts.Key_RealTimeData_pre_:
			pointGroupIDs = di.getRealtimedata();
			break;
		case GlobalConsts.Key_XYGraph_pre_:
			pointGroupIDs = di.getXygraph();
			break;
		}
		ArrayList<String> ret = new ArrayList<String>();
		ret.addAll(Arrays.asList(IDTools.splitID(pointGroupIDs)));
		return ret;
	}
	
	public PointGroup updateShareRight(PointGroup instance,String type,Double itemId, List<String> userIdsid, List<String> depIdsid) {
		PointGroupData pgd = pointGroupService.getPointGroupDataByID(itemId);
		if(pgd == null) {
			throw new IllegalStateException("没有id为"+itemId+"这个数据");
		}
		
		// 更新depart表
		String ss = pgd.getShareddepart(); // 修改之前部门ID，用于与depIdsid对比
		List<String> sls = Arrays.asList(IDTools.splitID(ss));
		// 对比出增加和减少的ID
		List<String> sa = IDTools.getAggrandizement(sls, depIdsid);
		List<String> sd = IDTools.getDecreament(sls, depIdsid);
		
		for(int inda=0;inda<sa.size();inda++) { // 原比新多的，要去掉
			String said = sa.get(inda);
			DepartmentInfo di = userManager.getDepartmentInfoByID(said);
			ArrayList<String> ids = getPointGroupIDsFromDepart(di,type);
			if(ids.contains(IDTools.toString(itemId))) {
				ids.remove(IDTools.toString(itemId));
			}else {
				System.out.println("本应该存在"+itemId+"，但却没查到");
			}
			String [] sids = ids.toArray(new String[ids.size()]);
			setPointGroupIDsFromDepart(di,IDTools.merge(sids),type);
			userManager.createDepartment(di);
			redisService.set(GlobalConsts.Key_DepartInfo_Pre_ + IDTools.toString(di.getId()),di);
		}

		for(int indd=0;indd<sd.size();indd++) {
			String said = sd.get(indd);
			DepartmentInfo di = userManager.getDepartmentInfoByID(said);
			ArrayList<String> ids = getPointGroupIDsFromDepart(di,type);
			if(!ids.contains(IDTools.toString(itemId))) {
				ids.add(IDTools.toString(itemId));
			}else {
				System.out.println("本应该不存在"+itemId+"，但却查到了");				
			}
			String [] sids = ids.toArray(new String[ids.size()]);
			setPointGroupIDsFromDepart(di,IDTools.merge(sids),type);
			userManager.createDepartment(di);
			redisService.set(GlobalConsts.Key_DepartInfo_Pre_ + IDTools.toString(di.getId()),di);
		}
		

		String sharedUserIDs = "";
		if(userIdsid != null) {
			sharedUserIDs = IDTools.merge(userIdsid.toArray());
		}
		pgd.setShared(sharedUserIDs);
		
		String sharedDepIDs = "";
		if(depIdsid != null) {
			sharedDepIDs = IDTools.merge(depIdsid.toArray());
		}
		pgd.setShareddepart(sharedDepIDs);
		// 更新数据库
		pointGroupService.updatePointGroupItem(pgd);

		// 更新缓存
		PointGroup rtd = copyFromPointGroupData(instance,pgd);
		redisService.set(type+IDTools.toString(rtd.getId()),rtd);

		return rtd;		
	}

	public PointGroup deletePointGroup(String type,PointGroup oldRtd,String oldRtdIdStr) {
//		PointGroup oldRtd = null;
		if(StringUtil.isBlank(oldRtdIdStr)) return null;
		Double oldRtdId = Double.valueOf(oldRtdIdStr);
        //pgd.setType(GlobalConsts.Type_XYGraph_);
		// 异步处理:
		try {
			// 先写缓存XYGraph，返回
			if(oldRtdId == null || oldRtdId==0)
				oldRtd = new XYGraph();
			else 
				oldRtd = getPointGroupByKeys(type,oldRtd,oldRtdId);
//			oldRtd = getXYGraphByKeys(oldRtdId);
			
			// 删除一条数据库记录
			pointGroupService.deletePointGroupItem(oldRtdId);	
			// 删除缓存
			redisService.delete(type+oldRtdId);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return oldRtd;
		}
		
		return oldRtd;	
	}
	
	@Deprecated
	public PointGroup getPointGroupRigidlyByKey(String type,PointGroup rtd,Double idstr) {
		return getPointGroupByKeys(type,rtd,idstr);
	}

	protected PointGroup getPointGroupByKeys(String type,PointGroup rtd,Double idstr) {
		if(idstr==null||idstr==0) {
			throw new IllegalStateException("id不能为空。");
		}
		Double id = Double.valueOf(idstr);
		PointGroup ret = (PointGroup) this.redisService.get(type+idstr);
		if(ret==null) {
			PointGroupData pgd = pointGroupService.getPointGroupDataByID(id);
			ret = copyFromPointGroupData(rtd,pgd);
			this.redisService.set(type+IDTools.toString(id), ret);
		}
		return ret;
	}

	public Hashtable<String, PointGroup> getAdminPointGroupHashtable(Hashtable<String, PointGroup> ret,String type,PointGroup ins) {
//		return (Hashtable<String, XYGraph>)getAdminPointGroupHashtable(ret,GlobalConsts.Key_XYGraph_pre_,new XYGraph());
		List<PointGroupData> pgdl = pointGroupService.getAdminPointGroupData(type);
//		List<PointGroupData> pgdl = pointGroupService.getAdminXYGraph();
		for (int i = 0; i < pgdl.size(); i++) {
			PointGroupData pgd = pgdl.get(i);
			PointGroup XYGraph = null;
			try {
				XYGraph = copyFromPointGroupData(ins.clone(),pgd);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ret.put(IDTools.toString(pgd.getId()), XYGraph);
			System.out.println("pgd.getId().toString()="+pgd.getId().toString());
			redisService.set(type + IDTools.toString(XYGraph.getId()), XYGraph);
		}
		return ret;
	}

	public Hashtable getPointGroupHashtableByKeys(String type,PointGroup ins,String LineAlertDataID) {
		Hashtable ret = new Hashtable();
		// 分隔key
		String[] keys = IDTools.splitID(LineAlertDataID);
		for (int ik = 0; ik < keys.length; ik++) {
			Double k = Double.valueOf(keys[ik]);
			PointGroup g = getPointGroupByKeys(type,ins,k);
			if (g == null) {
				System.out.println("没有指定ID="+keys[ik]+"的XY图，可能是由于数据不一致导致。");
				// TODO: 没有指定ID的XY图，可能是由于数据不一致导致。
				// TODO: 更新数据保持一致，通知管理员。
			}else
				ret.put(IDTools.toString(g.getId()), g);
		}
		return ret;
	}

	public PointGroup createPointGroup(PointGroup ret,String typeXYGraph, String name, String owner, String creater,
			JSONArray points, String otherrule2, String otherrule1, String id2) {
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
        pgd.setType(typeXYGraph);
        
		// 异步处理:
		try {
			// 创建一条数据库记录
			pointGroupService.newPointGroupData(pgd);	
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("新建实时数据失败。");
		}
        ret = copyFromPointGroupData(ret,pgd);
		// 先写缓存XYGraph，返回
		redisService.set(typeXYGraph+IDTools.toString(_id),ret);

		return ret;		
	}

	public PointGroup copyFromPointGroupData(PointGroup XYGraph,PointGroupData pgd) {
		if (pgd == null)
			return null;
//		PointGroup XYGraph = new XYGraph();

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

		XYGraph.setShareddepart(pgd.getShareddepart());
		ArrayList<DepartmentInfo> uldp = new ArrayList<DepartmentInfo>();
		String[] shareddepIds = IDTools.splitID(pgd.getShareddepart());
		for (int isharedIDs = 0; isharedIDs < shareddepIds.length; isharedIDs++) {
			DepartmentInfo u = userManager.getDepartmentInfoByID(shareddepIds[isharedIDs]);
			uldp.add(u);
		}
		XYGraph.setSharedDepartment(uldp);

		XYGraph.setType(pgd.getType());

		return XYGraph;
	}

	public void updatePointGroupItem(String key,PointGroup rtd) {
		// 更新数据库
		pointGroupService.updatePointGroupItem(rtd);
		// 写缓存RealTimeData，返回
		redisService.set(key+IDTools.toString(rtd.getId()),rtd);
	}

	public static String splitServerName(String str) {
		return splitServerName1(str);
//		str = str.toUpperCase();
////		if(str.contentEquals("CJY_TIC2218.MV"))
////			System.out.println("CJY_TIC2218.MV");
//		int _ind = str.indexOf(GlobalConsts.Key_splitCharServerPoint);
//		if (_ind < 0)
//			return ServerManager.defaultServer.getServerName();
//		else {
//			String serverName = str.substring(0, _ind);
//			if (ServerManager.hasServerName(serverName))
//				return serverName;
//			else
//				return ServerManager.defaultServer.getServerName();
//		}
	}

	public static String splitPointName(String str) {
		return splitPointName1(str);
//		str = str.toUpperCase();
//		int _ind = str.indexOf(GlobalConsts.Key_splitCharServerPoint);
//		if (_ind < 0)
//			return str;
//		else {
//			String serverName = str.substring(0, _ind);
//			if (ServerManager.hasServerName(serverName))
//				return str.substring(_ind + 1);
//			else
//				return str;
//		}
	}

	public static Set compareRight(PointGroupData rtd, PointGroupData oldRtd, String keyaggrandizement) {
		// 如果旧数据为空，就是增加
		if (oldRtd == null || StringUtil.isBlank(oldRtd.getCreater()) || StringUtil.isBlank(oldRtd.getOwner())) {
			Set<String> s1 = new HashSet<String>();
			if (keyaggrandizement.contentEquals(GlobalConsts.KeyDecrement))
				return s1;
			try {
				s1.add(rtd.getCreater());
				s1.add(rtd.getOwner());
				String[] idShare1 = IDTools.splitID(rtd.getShared());
				for (int ii = 0; ii <= idShare1.length; ii++)
					try {
						if (!s1.contains(idShare1[ii]))
							s1.add(idShare1[ii]);
					} catch (Exception e) {

					}
				return s1;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 如果新数据为空，就是删除
		if (rtd == null || StringUtil.isBlank(rtd.getCreater()) || StringUtil.isBlank(rtd.getOwner())) {
			Set<String> s1 = new HashSet<String>();
			if (keyaggrandizement.contentEquals(GlobalConsts.KeyAggrandizement))
				return s1;
			try {
				s1.add(oldRtd.getCreater());
				s1.add(oldRtd.getOwner());
				String[] idShare1 = IDTools.splitID(oldRtd.getShared());
				for (int ii = 0; ii <= idShare1.length; ii++)
					try {
						if (!s1.contains(idShare1[ii]))
							s1.add(idShare1[ii]);
					} catch (Exception e) {

					}
				return s1;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String idOwnerChange = "";
		if (!rtd.getOwner().contentEquals(oldRtd.getOwner()))
			idOwnerChange = rtd.getOwner();
		String[] idShare1 = IDTools.splitID(rtd.getShared());
		String[] idShare2 = IDTools.splitID(oldRtd.getShared());

		switch (keyaggrandizement) {
		case GlobalConsts.KeyAggrandizement:
			// 增加的分享用户ID
			String[] aggrandizement = compareStringArray(idShare1, idShare2);
			Set<String> s1 = new HashSet<String>();
			for (int indAggrandizement = 0; indAggrandizement < aggrandizement.length; indAggrandizement++) {
				try {
					if (!s1.contains(aggrandizement[indAggrandizement]))
						s1.add(aggrandizement[indAggrandizement]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(!StringUtil.isBlank(idOwnerChange))
				s1.add(idOwnerChange);
			return s1;
		case GlobalConsts.KeyDecrement:
			// 减少的分享用户ID
			String[] decreasing = compareStringArray(idShare2, idShare1);
			Set<String> s2 = new HashSet<String>();
			for (int inddecreaement = 0; inddecreaement < decreasing.length; inddecreaement++) {
				try {
					if (!s2.contains(decreasing[inddecreaement]))
						s2.add(decreasing[inddecreaement]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return s2;
		}
		return null;
	}

	/**
	 * 比较两个点组，返回权限差异
	 * 
	 * @param rtd
	 * @param oldRtd
	 * @return 有两个元素，一个是增加权限，一个减少权限。
	 */
	public static Hashtable<String, PointGroup> compareRight(PointGroup rtd, PointGroup oldRtd) {
		// TODO Auto-generated method stub
		Hashtable<String, PointGroup> ret = new Hashtable<String, PointGroup>();
		String idOwnerChange = "";
		if (!rtd.getOwner().contentEquals(oldRtd.getOwner()))
			idOwnerChange = rtd.getOwner();
		String[] idShare1 = IDTools.splitID(rtd.getShared());
		String[] idShare2 = IDTools.splitID(oldRtd.getShared());
		// 增加的分享用户ID
		String[] aggrandizement = compareStringArray(idShare1, idShare2);
		// 减少的分享用户ID
		String[] decreasing = compareStringArray(idShare2, idShare1);
		PointGroup aggrandizementPointGroup = new PointGroup();
		aggrandizementPointGroup.setOwner(idOwnerChange);
		aggrandizementPointGroup.setShared(IDTools.merge(aggrandizement));
		PointGroup decreasingPointGroup = new PointGroup();
		decreasingPointGroup.setShared(IDTools.merge(decreasing));
		ret.put(GlobalConsts.KeyAggrandizement, aggrandizementPointGroup);
		ret.put(GlobalConsts.KeyDecrement, decreasingPointGroup);
		return ret;
	}

	/**
	 * 返回s里有，而t里没有元素。
	 * 
	 * @param s
	 * @param t
	 * @return
	 */
	public static String[] compareStringArray(String[] s, String[] t) {
		Arrays.sort(s);
		Arrays.sort(t);
		ArrayList<String> ret = new ArrayList<String>();
		for (int i = 0; i < s.length; i++) {
			String ss = s[i];
			for (int j = 0; j < t.length; j++) {
				String tt = t[j];
				if (ss.contentEquals(tt))
					ret.add(ss);
			}
		}
		return t;
	}

	public PointGroup getPointsByGroupId(Double k, String type) {

		PointGroup pg = (PointGroup) redisService.get(type + IDTools.toString(k));

		return pg;
	}

	
	public DataViewer buildDataViewer(Double k, String type) {
		PointGroup pgd = getPointsByGroupId(k, type);
		DataViewer dv = new DataViewer();
		dv.setPointGroupID(k);
		dv.setType(pgd.getType());
		if(pgd==null) {
			System.out.println();
		}
		List<Point> pl = pgd.getPointList();
		if(pl==null || pl.size()==0) {
			return null;
		}
		
		HashMap<String,ServerPoint> sps = new HashMap<String,ServerPoint>();
		for (int i = 0; i < pl.size(); i++) {
			String serverName = pl.get(i).getServerName();
			
			ServerPoint sp;
			ArrayList<Long> idsa;
			ArrayList<String> tagNamesa;
			ArrayList<String> pointTextIDs = null;
			if(sps.containsKey(serverName)) {
				sp = sps.get(serverName);
//				idsa = new ArrayList<Long>();
				idsa = new ArrayList<Long>(Arrays.asList(sp.getIds()));
				tagNamesa = new ArrayList<String>();  
				tagNamesa = new ArrayList<String>( Arrays.asList(sp.getTagNames()));    
				if(pgd.getPointTextIDs()!=null) {
					pointTextIDs = new ArrayList<String>( Arrays.asList(sp.getPointTextIDs()));    					
				}
//				tagNamesa = (ArrayList<String>) Arrays.asList(sp.getTagNames());
			} else {
				sp = new ServerPoint();
				sp.setServerName(serverName);
				sps.put(serverName, sp);
				idsa = new ArrayList<Long>();
				tagNamesa = new ArrayList<String>();
				if(pgd.getPointTextIDs()!=null) {
					pointTextIDs = new ArrayList<String>();
				}
			}
			idsa.add(pl.get(i).getId());
			tagNamesa.add(pl.get(i).getTagName());
			Long[]ids = idsa.toArray(new Long[0]);
			sp.setIds(ids);
			String[]tagNames = tagNamesa.toArray(new String[0]);
			sp.setTagNames(tagNames);
			if(pgd.getPointTextIDs()!=null) {
				if(pgd.getPointList().size()!=pgd.getPointTextIDs().size()) {
					for(int ip=0;ip<pgd.getPointList().size();ip++)
						System.out.println(" "+pgd.getPointList().get(ip).getTagName());
					System.out.println("==========================================");
					for(int ip=0;ip<pgd.getPointTextIDs().size();ip++)
						System.out.println(" "+pgd.getPointTextIDs().get(ip));
					
				}
				if(i<pgd.getPointTextIDs().size())
				pointTextIDs.add(pgd.getPointTextIDs().get(i));
			}
			if(type.contentEquals(GlobalConsts.Key_Graph_pre_)) {
				String[] _pointTextIDs = pointTextIDs.toArray(new String[0]);
				sp.setPointTextIDs(_pointTextIDs);
			}
			sps.put(serverName, sp);
		}

		switch (type) {
			case GlobalConsts.Key_Graph_pre_:
				dv.setQueryById(true);
		}
//		dv.setIds(ids);
//		dv.setTagNames(tagNames);
		ServerPoint[] spsa = new ServerPoint[sps.size()];
		int index =0;
		for(Entry<?, ?> entry : sps.entrySet()){
//		    System.out.println(entry.getKey() + entry.getValue());
			ServerPoint sp = (ServerPoint) entry.getValue();
			spsa[index] = sp;
			index++;
		}

		dv.setSps(spsa);
		return dv;
	}

	// 拆分 "\\RTDBB\81_3701_01_P02_C_out"，成服务器 点位名
	public static String splitServerName1(String gPointId) {
		gPointId = gPointId.toUpperCase();

		String serverName = gPointId;
		// 如果是\\开头
//		System.out.println(GlobalConsts.Key_ServerNamePre);
//		System.out.println(GlobalConsts.Key_splitCharServerPoint);
		if(gPointId.startsWith(GlobalConsts.Key_ServerNamePre)) {
			serverName = gPointId.substring(2);
		}else {
//			serverName = ServerManager.defaultServer.getServerName();
//			return serverName;
		}
		int i = serverName.indexOf(GlobalConsts.Key_splitCharServerPoint);
		if(i>0) {
			return serverName.substring(0,i);
		} else {
			return ServerManager.defaultServer.getServerName();
		}
	}

	public static String splitPointName1(String gPointId) {
		gPointId = gPointId.toUpperCase();
		String serverName = gPointId;
		// 如果是\\开头
		if(gPointId.startsWith("\\\\")) {
			serverName = serverName.substring(2);
		}
		int i = serverName.indexOf("\\");
		if(i>0) {
			serverName = serverName.substring(i+1);
		} 
		return serverName;

	}
	public static void main(String [] argc) {
		System.out.println(PointGroupDataManager.splitServerName1( "\\\\RTDBB\\81_3701_01_P02_C_out"));
		System.out.println(PointGroupDataManager.splitPointName1("\\\\RTDBB\\81_3701_01_P02_C_out"));
	}

}
