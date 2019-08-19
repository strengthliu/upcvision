package com.surpass.vision.pointGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Component;

import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.domain.PointGroup;
import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.domain.RealTimeData;
import com.surpass.vision.tools.IDTools;

@Component
public class PointGroupDataManager {
	
	public static Set compareRight(PointGroupData rtd, PointGroupData oldRtd, String keyaggrandizement) {
		// 如果旧数据为空，就是增加
		if(oldRtd == null || StringUtil.isBlank(oldRtd.getCreater()) || StringUtil.isBlank(oldRtd.getOwner())) {
			Set<String> s1 = new HashSet<String>();
			if(keyaggrandizement.contentEquals(GlobalConsts.KeyDecrement)) return s1;
			try {
				s1.add(rtd.getCreater());
				s1.add(rtd.getOwner());
				String [] idShare1 = IDTools.splitID(rtd.getShared());
				for(int ii=0;ii<=idShare1.length;ii++)
					try {
						if(!s1.contains(idShare1[ii]))
							s1.add(idShare1[ii]);
					}catch(Exception e) {
						
					}
				return s1;
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		// 如果新数据为空，就是删除
		if(rtd == null || StringUtil.isBlank(rtd.getCreater()) || StringUtil.isBlank(rtd.getOwner())) {
			Set<String> s1 = new HashSet<String>();
			if(keyaggrandizement.contentEquals(GlobalConsts.KeyAggrandizement)) return s1;
			try {
				s1.add(oldRtd.getCreater());
				s1.add(oldRtd.getOwner());
				String [] idShare1 = IDTools.splitID(oldRtd.getShared());
				for(int ii=0;ii<=idShare1.length;ii++)
					try {
						if(!s1.contains(idShare1[ii]))
							s1.add(idShare1[ii]);
					}catch(Exception e) {
						
					}
				return s1;
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

		String idOwnerChange = "";
		if(!rtd.getOwner().contentEquals(oldRtd.getOwner())) idOwnerChange=rtd.getOwner();
		String [] idShare1 = IDTools.splitID(rtd.getShared());
		String [] idShare2 = IDTools.splitID(oldRtd.getShared());
		
		switch(keyaggrandizement) {
		case GlobalConsts.KeyAggrandizement:
			// 增加的分享用户ID
			String [] aggrandizement = compareStringArray(idShare1,idShare2);
			Set<String> s1 = new HashSet<String>();
			for(int indAggrandizement=0;indAggrandizement<aggrandizement.length;indAggrandizement++) {
				try {
					if(!s1.contains(aggrandizement[indAggrandizement]))
						s1.add(aggrandizement[indAggrandizement]);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			s1.add(idOwnerChange);
			return s1;
		case GlobalConsts.KeyDecrement:
			// 减少的分享用户ID
			String [] decreasing =  compareStringArray(idShare2,idShare1);
			Set<String> s2 = new HashSet<String>();
			for(int inddecreaement=0;inddecreaement<decreasing.length;inddecreaement++) {
				try {
					if(!s2.contains(decreasing[inddecreaement]))
					s2.add(decreasing[inddecreaement]);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			return s2;
		}
		return null;
	}

	/**
	 * 比较两个点组，返回权限差异
	 * @param rtd
	 * @param oldRtd
	 * @return 有两个元素，一个是增加权限，一个减少权限。
	 */
	public static Hashtable<String, PointGroup> compareRight(PointGroup rtd, PointGroup oldRtd) {
		// TODO Auto-generated method stub
		Hashtable<String, PointGroup> ret = new Hashtable<String, PointGroup>();
		String idOwnerChange = "";
		if(!rtd.getOwner().contentEquals(oldRtd.getOwner())) idOwnerChange=rtd.getOwner();
		String [] idShare1 = IDTools.splitID(rtd.getShared());
		String [] idShare2 = IDTools.splitID(oldRtd.getShared());
		// 增加的分享用户ID
		String [] aggrandizement = compareStringArray(idShare1,idShare2);
		// 减少的分享用户ID
		String [] decreasing =  compareStringArray(idShare2,idShare1);
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
	 * @param s
	 * @param t
	 * @return
	 */
	public static String[] compareStringArray(String [] s,String [] t) {
		Arrays.sort(s);Arrays.sort(t);
		ArrayList<String> ret = new ArrayList<String>();
		for(int i=0;i<s.length;i++) {
			String ss = s[i];
			for(int j=0;j<t.length;j++) {
				String tt = t[j];
				if(ss.contentEquals(tt)) ret.add(ss);
			}
		}
		return t;
	}


}
