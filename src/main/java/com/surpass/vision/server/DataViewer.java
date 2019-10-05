package com.surpass.vision.server;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.surpass.vision.server.PointList.Ind;

@Component
public class DataViewer {
	Double pointGroupID;
	boolean queryById = false;

	public boolean isQueryById() {
		return queryById;
	}

	public void setQueryById(boolean queryById) {
		this.queryById = queryById;
	}

	
	Long time ;
	ServerPoint[] sps;
	// Long[] ids;
	Long[] ids;
	String[] tagNames;

//	Integer []cacheInd;
	Ind[] ind;
	long startTime;
	long endTime;

	public Point[] getPoints(String serverName) {
		if (sps == null || sps.length == 0)
			return null;
		for (int isps = 0; isps < sps.length; isps++) {
			if (sps[isps].serverName.contentEquals(serverName)) {
				Long[] ids = sps[isps].ids;
				String[] tagNames = sps[isps].tagNames;
				Point[] ps = new Point[ids.length];
				for (int i = 0; i < ids.length; i++) {
					Point p = new Point();
					p.setId(ids[i]);
					p.setTagName(tagNames[i]);
					p.setServerName(serverName);
					ps[i] = p;
				}
				return ps;
			}
		}
		return null;
	}

//	public Long[] getIds() {
//		return ids;
//	}
	public Double getPointGroupID() {
		return pointGroupID;
	}

	public void setPointGroupID(Double pointGroupID) {
		this.pointGroupID = pointGroupID;
	}

//	public void setIds(Long[] ids) {
//		this.ids = ids;
//	}
//	public String[] getTagNames() {
//		return tagNames;
//	}
	public ServerPoint[] getSps() {
		return sps;
	}

	public void setSps(ServerPoint[] sps) {
		this.sps = sps;
	}

//	public void setTagNames(String[] tagNames) {
//		this.tagNames = tagNames;
//	}
	public HashMap<String, Object> valuesByTagName() {
		HashMap<String, Object> ret = new HashMap<String, Object>();
		if (ind != null)
			for (int i = 0; i < ind.length; i++) {
//				System.out.println("build data by tagName: "+tagNames[i]+" = "+ind[i].getValue());
				ret.put(tagNames[i], ind[i].getValue());
			}
		else {
//			System.out.println("DataViewer.values datas为空。");
		}
//		if(time == null) 
			time = ServerManager.getInstance().getServerCurrentTime();
		ret.put("time", time);
		return ret;
	}

	public HashMap valuesByID() {
		HashMap ret = new HashMap();
		if (ind != null)
			for (int i = 0; i < ind.length; i++) {
//				System.out.println("build data by id: "+ids[i]+" = "+ind[i].getValue());
				ret.put(ids[i], ind[i].getValue());
			}
		else {
			System.out.println("DataViewer.values datas为空。");
		}
//		if(time == null) 
//			time = System.currentTimeMillis();
		time = ServerManager.getInstance().getServerCurrentTime();
		ret.put("time", time);
		return ret;
	}

}
