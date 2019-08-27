package com.surpass.vision.server;

import com.surpass.vision.server.PointList.Ind;

public class DataViewer{
	Double pointGroupID;
	
	Long[] ids;
	Double[] datas;
	String[] tagNames;
	
//	Integer []cacheInd;
	Ind []ind;
	long startTime;
	long endTime;

	public Point[] getPoints() {
		Point[] ps = new Point[ids.length];
		for(int i=0;i<ids.length;i++) {
			Point p = new Point();
			p.setId(ids[i]);
			p.setTagName(tagNames[i]);
			ps[i] = p;
		}
		return ps;
	}
	public Long[] values() {
		Long[] ret = new Long[ids.length];
		for(int i=0;i<ret.length;i++) {
			ret[i] = ind[i].getValue();
		}
		return ret;
	}
}
