package com.surpass.vision.XYGraph;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

import com.surpass.vision.domain.Graph;
import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.domain.User;
import com.surpass.vision.domain.XYGraph;
import com.surpass.vision.mapper.PointGroupDataMapper;
import com.surpass.vision.server.Point;
import com.surpass.vision.server.ServerManager;
import com.surpass.vision.service.PointGroupService;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.tools.IDTools;
import com.surpass.vision.user.UserManager;
@Component
public class XYGraphManager {
	@Reference
	@Autowired
	RedisService redisService;
	

	@Autowired
	PointGroupService pointGroupService;
	
	@Autowired
	UserManager userManager;
	/**
	 * 从缓存里取数据，如果没有，再调用服务。 
	 * @param graphs
	 * @return
	 */
	public Hashtable<String, XYGraph> getXYGraphHashtabelByKeys(String xygraph) {
		Hashtable<String, XYGraph> ret = new Hashtable<String, XYGraph> ();
		// 分隔key
		String [] keys = IDTools.splitID(xygraph);
		for(int ik = 0;ik<keys.length;ik++) {
			// 从缓存里取图
			XYGraph g = (XYGraph)redisService.get(keys[ik]);
			if(g == null) {
				// TODO: 如果没有, 从数据库里取
				
				// 再设置缓存
			}
			
			ret.put(g.getName(), g);
		}
		// 
		return ret;
	}


	public Hashtable<String, XYGraph> getAdminXYGraphHashtabel() {
		Hashtable<String, XYGraph> ret = new Hashtable<String, XYGraph>();
		List<PointGroupData> pgdl = pointGroupService.getAdminXYGraph();
		for(int i=0;i<pgdl.size();i++) {
			PointGroupData pgd = pgdl.get(i);
			XYGraph xYGraph = new XYGraph();
			
			xYGraph.setCreater(pgd.getCreater());
			xYGraph.setCreaterUser(userManager.getUserByID(pgd.getCreater()));
			
			xYGraph.setId(pgd.getId());
			xYGraph.setName(pgd.getName());
			xYGraph.setOtherrule1(pgd.getOtherrule1());
			xYGraph.setOtherrule2(pgd.getOtherrule2());
			
			xYGraph.setOwner(pgd.getOwner());
			xYGraph.setOwnerUser(userManager.getUserByID(pgd.getOwner()));

			xYGraph.setPoints(pgd.getPoints());
			ArrayList<Point> pal = new ArrayList<>();
			String [] pids = IDTools.splitID(pgd.getPoints());
			for(int ipids=0;ipids<pids.length;ipids++) {
				Point p = ServerManager.getInstance().getPointByID(pids[ipids]);
				pal.add(p);
			}
			xYGraph.setPointList(pal);
			
			xYGraph.setShared(pgd.getShared());
			ArrayList<User> ul = new ArrayList<User>();
			String [] sharedIds = IDTools.splitID(pgd.getShared());
			for(int isharedIDs=0;isharedIDs<sharedIds.length;isharedIDs++) {
				User u = userManager.getUserByID(sharedIds[isharedIDs]);
				ul.add(u);
			}
			xYGraph.setSharedUsers(ul);
			
			xYGraph.setType(pgd.getType());

			ret.put(pgd.getId().toString(), xYGraph);
		}
		return ret;
	}

}
