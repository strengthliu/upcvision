package com.surpass.vision.domain;

import java.io.Serializable;
import java.util.Hashtable;

import com.alibaba.fastjson.JSONObject;

public class UserSpace implements Serializable {
//	public UserSpace(UserSpaceData usd) {
//		// TODO:
//		Integer uid = usd.getUid();
//		
//	}
	public UserSpace() {
		// TODO:
	}
	
	/**
	 * 用户信息
	 */
	UserInfo user;
	/**
	 * 用户权限信息
	 */
	UserRight right;
	/**
	 * 
	 */
	String token;
//	Integer userID;
	
	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public UserRight getRight() {
		return right;
	}

	public void setRight(UserRight right) {
		this.right = right;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Hashtable<String, Graph> getGraphs() {
		return graphs;
	}

	public void setGraphs(Hashtable<String, Graph> graphs) {
		this.graphs = graphs;
	}

	public Hashtable<String, XYGraph> getXyGraph() {
		return xyGraph;
	}

	public void setXyGraph(Hashtable<String, XYGraph> xyGraph) {
		this.xyGraph = xyGraph;
	}

	public Hashtable<String, RealTimeData> getRealTimeData() {
		return realTimeData;
	}

	public void setRealTimeData(Hashtable<String, RealTimeData> realTimeData) {
		this.realTimeData = realTimeData;
	}

	public Hashtable<String, AlertData> getAlertData() {
		return alertData;
	}

	public void setAlertData(Hashtable<String, AlertData> alertData) {
		this.alertData = alertData;
	}

	public Hashtable<String, HistoryData> getHistoryData() {
		return historyData;
	}

	public void setHistoryData(Hashtable<String, HistoryData> historyData) {
		this.historyData = historyData;
	}

	public Hashtable<String, LineAlertData> getLineAlertData() {
		return lineAlertData;
	}

	public void setLineAlertData(Hashtable<String, LineAlertData> lineAlertData) {
		this.lineAlertData = lineAlertData;
	}

	/**
	 * 用户可见的图
	 */
	Hashtable<String,Graph> graphs;
	
	Hashtable<String,XYGraph> xyGraph;
	
	Hashtable<String,RealTimeData> realTimeData;
	
	Hashtable<String,AlertData> alertData;
	
	Hashtable<String,HistoryData> historyData;

	Hashtable<String,LineAlertData> lineAlertData;

	public JSONObject toJSONObject() {
		// TODO:
		return null;
	}
}
