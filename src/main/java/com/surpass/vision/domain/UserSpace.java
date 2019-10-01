package com.surpass.vision.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.surpass.vision.appCfg.GlobalConsts;

public class UserSpace implements Serializable {
	
	/**
	 * 用户可见的图
	 */
//	ArrayList<Graph> graphs;
	Graph graph;
	
	Hashtable<String,HistoryData> historyData;
	Hashtable<String,LineAlertData> lineAlertData;
	Hashtable<String,RealTimeData> realTimeData;
	Hashtable<String,XYGraph> xyGraph;
	Hashtable<String,AlertData> alertData;

	/**
	 * 用户权限信息
	 */
	UserRight right;

	/**
	 * 
	 */
	String token;
//	Integer userID;

	/**
	 * 用户信息
	 */
	UserInfo user;


	//	public UserSpace(UserSpaceData usd) {
//		// TODO:
//		Integer uid = usd.getUid();
//		
//	}
	public UserSpace() {
		// TODO:
	}

	public Hashtable<String, AlertData> getAlertData() {
		return alertData;
	}

//	public Hashtable<String, ArrayList<Graph>> getGraphs() {
//		return graphs;
//	}

	public Hashtable<String, HistoryData> getHistoryData() {
		return historyData;
	}

	public Hashtable<String, LineAlertData> getLineAlertData() {
		return lineAlertData;
	}

	public Hashtable<String, RealTimeData> getRealTimeData() {
		return realTimeData;
	}

	public UserRight getRight() {
		return right;
	}

	public String getToken() {
		return token;
	}

	public UserInfo getUser() {
		return user;
	}

	public Hashtable<String, XYGraph> getXyGraph() {
		return xyGraph;
	}

	public void setAlertData(Hashtable<String, AlertData> alertData) {
		this.alertData = alertData;
	}

//	public void setGraphs(Hashtable<String, ArrayList<Graph>> graphs) {
//		this.graphs = graphs;
//	}

	public void setHistoryData(Hashtable<String, HistoryData> historyData) {
		this.historyData = historyData;
	}

	public void setLineAlertData(Hashtable<String, LineAlertData> lineAlertData) {
		this.lineAlertData = lineAlertData;
	}
	
	public void setRealTimeData(Hashtable<String, RealTimeData> realTimeData) {
		this.realTimeData = realTimeData;
	}
	
	public void setRight(UserRight right) {
		this.right = right;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public void setUser(UserInfo user) {
		this.user = user;
	}

	public void setXyGraph(Hashtable<String, XYGraph> xyGraph) {
		this.xyGraph = xyGraph;
	}

	public JSONObject toJSONObject() {
		// TODO:
		return null;
	}

	/**
	 * 创建用户空间。
	 * @return
	 */
	public UserSpaceData createUserSpaceData() {
		UserSpaceData usd = new UserSpaceData();
		usd.setUid(this.user.getId());
		int count = 0;
		// graphs
//		Enumeration<String> egraphs = this.graphs.keys();
//		String graphs = "";
//		while(egraphs.hasMoreElements()) {
//			graphs += egraphs.nextElement().toString();
//			count++;
//			if(count<this.graphs.size())
//				graphs += GlobalConsts.Key_splitChar;
//		}
//		count = 0;
//		usd.setGraphs(graphs);
		String idsStr = "";
		List<Double> ids = this.graph.getIDList(null);
		for(int i=0;i<ids.size();i++) {
			idsStr = idsStr + GlobalConsts.Key_splitChar + ids.get(i);
		}
		if(idsStr.length()>=1)
			idsStr = idsStr.substring(1);
		usd.setGraphs(idsStr);

		// historydata
		Enumeration<String> ehistory = this.historyData.keys();
		String historydata = "";
		while(ehistory.hasMoreElements()) {
			historydata += ehistory.nextElement().toString();
			count++;
			if(count<this.alertData.size())
				historydata += GlobalConsts.Key_splitChar;
		}
		count = 0;
		usd.setHistorydata(historydata);


		// linealertdata
		Enumeration<String> elinealert = this.lineAlertData.keys();
		String linealertdata = "";
		while(elinealert.hasMoreElements()) {
			linealertdata += elinealert.nextElement().toString();
			count++;
			if(count<this.lineAlertData.size())
				linealertdata += GlobalConsts.Key_splitChar;
		}
		count = 0;
		usd.setLinealertdata(linealertdata);

		// realtimedata
		Enumeration<String> erealtime = this.realTimeData.keys();
		String realtimedata = "";
		while(erealtime.hasMoreElements()) {
			realtimedata += erealtime.nextElement().toString();
			count++;
			if(count<this.realTimeData.size())
				realtimedata += GlobalConsts.Key_splitChar;
		}
		count = 0;
		usd.setRealtimedata(realtimedata);

		// xygraphdata
		Enumeration<String> exygraph = this.xyGraph.keys();
		String xygraphdata = "";
		while(exygraph.hasMoreElements()) {
			xygraphdata += exygraph.nextElement().toString();
			count++;
			if(count<this.xyGraph.size())
				xygraphdata += GlobalConsts.Key_splitChar;
		}
		count = 0;
		usd.setXygraph(xygraphdata);

		// alertdata
		Enumeration<String> ealert = this.alertData.keys();
		String alertdata = "";
		while(ealert.hasMoreElements()) {
			alertdata += ealert.nextElement().toString();
			count++;
			if(count<this.alertData.size())
			alertdata += GlobalConsts.Key_splitChar;
		}
		count = 0;
		usd.setAlertdata(alertdata);

		return usd;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public boolean canDelete() {
		Hashtable<String,RealTimeData> hr = getRealTimeData();
		if(!canDelete(hr)) return false;
		Hashtable<String,AlertData> ha = getAlertData();
		if(!canDelete(ha)) return false;
//		Hashtable<String,ArrayList<Graph>> hg = getGraphs();
		Hashtable<String,HistoryData> hh = getHistoryData();
		if(!canDelete(hh)) return false;
		Hashtable<String,LineAlertData> hl = getLineAlertData();
		if(!canDelete(hl)) return false;
		Hashtable<String,XYGraph> hx = getXyGraph();
		if(!canDelete(hx)) return false;

		return true;
	}

	private boolean canDelete(Hashtable hp) {
		Enumeration<?> e = hp.elements();
		while(e.hasMoreElements()) {
			try {
				PointGroup p = (PointGroup) e.nextElement();
				UserRight ur = p.getRight(this.user.getId());
				if(ur.isCreater() || ur.isOwnner()) 
					return false;
			}catch(Exception ex) {
				
			}
		}
		return true;
	}
}
