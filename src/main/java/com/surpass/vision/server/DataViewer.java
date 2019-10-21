package com.surpass.vision.server;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Component;

import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.server.PointList.Ind;

@Component
public class DataViewer {
	Double pointGroupID;
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	String type;
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
	String [] pointTextIDs;
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

	private ServerPoint[] _sps;
	private void copySps() {
		_sps = new ServerPoint[sps.length];
		for(int i=0;i<sps.length;i++) {
			try {
				_sps[i] = sps[i].clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void setSps(ServerPoint[] sps) {
		this.sps = sps;
		copySps();
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
//		if(time == null) // 返回的实时数据是没有时间的
			time = System.currentTimeMillis();
//			time = ServerManager.getInstance().getServerCurrentTime();
		ret.put("time", time);
		return ret;
	}

	/**
	 * 图形取值时用
	 * @return
	 */
	public HashMap valuesByID() {
		HashMap<String, Object> ret = new HashMap<String, Object>();
		if (ind != null)
			for (int i = 0; i < ind.length; i++) {
//				System.out.println("build data by tagName: "+tagNames[i]+" = "+ind[i].getValue());
//				ret.put(tagNames[i], ind[i].getValue());
				ret.put(this.pointTextIDs[i], ind[i].getValue());
			}
		else {
//			System.out.println("DataViewer.values datas为空。");
		}
//		if(time == null) // 返回的实时数据是没有时间的
			time = System.currentTimeMillis();
//			time = ServerManager.getInstance().getServerCurrentTime();
		ret.put("time", time);
		return ret;

//		HashMap ret = new HashMap();
//		if (ind != null)
//			for (int i = 0; i < ind.length; i++) {
////				System.out.println("build data by id: "+ids[i]+" = "+ind[i].getValue());
//				ret.put(ids[i], ind[i].getValue());
//			}
//		else {
//			System.out.println("DataViewer.values datas为空。");
//		}
////		if(time == null) 
//			time = System.currentTimeMillis();
////		time = ServerManager.getInstance().getServerCurrentTime();
//		ret.put("time", time);
//		return ret;
	}

	/**
	 * 重构DVS。现在的tagNames，不包含服务器名，需要加上。目前假设所有服务器中，tagName是唯一的。
	 */
	public void rebuild() {
		if(!type.contentEquals(GlobalConsts.Type_graph_))
			return;
		ArrayList<String> pointTextIds = new ArrayList<String>();
		for(int indtagNames=0;indtagNames<tagNames.length;indtagNames++) {
			String ptid = getPointTextId(tagNames[indtagNames]);
			if(!StringUtil.isBlank(ptid))
				pointTextIds.add(ptid);
			else
				pointTextIds.add(""); // 避免空指针
		}
		this.pointTextIDs = pointTextIds.toArray(new String[0]);
	}

	
	/**
	 * TODO:  
	 * 取数据时错，pointTextIds与tagNames不是只有一套，一个tagName对应多个pointTextIds
	 * @param tagName
	 * @return
	 */
	private String getPointTextId(String tagName) {
		for(int indsps =0;indsps<_sps.length;indsps++) {
			ServerPoint sp = _sps[indsps];
			if(_sps[indsps]==null)
				return "";
			for(int inds=0;inds<sp.tagNames.length;inds++) {
				if(tagName.contentEquals(sp.tagNames[inds])) {
					if(sp.pointTextIDs==null)
						return "";
					String ret = sp.pointTextIDs[inds];
					sp.tagNames[inds] = "";
					return ret;
				}
			}
		}
		return null;

	}
	
}
