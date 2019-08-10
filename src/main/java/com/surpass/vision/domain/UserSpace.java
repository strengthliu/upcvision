package com.surpass.vision.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import com.alibaba.fastjson.JSONObject;

public class UserSpace implements Serializable {
Hashtable<String,AlertData> alertData;
	
	/**
	 * 用户可见的图
	 */
	Hashtable<String, ArrayList<Graph>> graphs;
	Hashtable<String,HistoryData> historyData;
	Hashtable<String,LineAlertData> lineAlertData;
	
	Hashtable<String,RealTimeData> realTimeData;

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

	Hashtable<String,XYGraph> xyGraph;

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

	public Hashtable<String, ArrayList<Graph>> getGraphs() {
		return graphs;
	}

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

	public void setGraphs(Hashtable<String, ArrayList<Graph>> graphs) {
		this.graphs = graphs;
	}

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
}
