package com.surpass.vision.server;

import java.util.ArrayList;

public class ServerPoint implements Cloneable {
	String serverName;
	Long[] ids;
	String[] tagNames;
	String[] pointTextIDs;


	public String[] getPointTextIDs() {
		return pointTextIDs;
	}

	public void setPointTextIDs(String[] pointTextIDs) {
		this.pointTextIDs = pointTextIDs;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public String[] getTagNames() {
		return tagNames;
	}

	public void setTagNames(String[] tagNames) {
		this.tagNames = tagNames;
	}

	public Point[] getPoints() {
		if (ids == null || ids.length == 0)
			return null;

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

	@Override
	protected ServerPoint clone() throws CloneNotSupportedException {
		ServerPoint ret = (ServerPoint) super.clone();
		Long[] ids;
		String[] tagNames;
		String[] pointTextIDs;

		ret.serverName = this.serverName;
		
		ret.ids = new Long[this.ids.length];
		for(int iids=0;iids<this.ids.length;iids++) {
			ret.ids[iids] = this.ids[iids];
		}
		ret.tagNames = new String[this.tagNames.length];
		for(int iids=0;iids<this.tagNames.length;iids++) {
			ret.tagNames[iids] = this.tagNames[iids];
		}
		if(this.pointTextIDs!=null) {
			ret.pointTextIDs = new String[this.pointTextIDs.length];
			for(int iids=0;iids<this.pointTextIDs.length;iids++) {
				ret.pointTextIDs[iids] = this.pointTextIDs[iids];
			}
		}
		return ret;
		
	}
//	public void setPointTextIDs(ArrayList<String> pointTextIDs2) {
//		// TODO Auto-generated method stub
//		
//	}
	
}
