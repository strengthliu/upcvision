package com.surpass.vision.graph;

import java.util.List;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.domain.FileList;
import com.surpass.vision.domain.Graph;
import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.domain.User;
import com.surpass.vision.mapper.PointGroupDataMapper;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.tools.IDTools;
import com.surpass.vision.user.UserManager;

@Component 
public class GraphDataManager {

	@Reference
	@Autowired
	RedisService redisService;
	
	@Autowired
	PointGroupDataMapper pointGroupDataMapper;

	@Reference
	@Autowired
	UserManager userManager;


	/**
	 * 从缓存里取数据，如果没有，再调用服务。 
	 * @param graphs
	 * @return
	 */
	public Hashtable<String, Graph> getGraphHashtableByKeys(String graphs) {
		Hashtable<String, Graph> ret = new Hashtable<String, Graph> ();
		// 分隔key
		String [] keys = IDTools.splitID(graphs);
		for(int ik = 0;ik<keys.length;ik++) {
			// 从缓存里取图
			Graph g = (Graph)redisService.get(keys[ik]);
			if(g == null) {
				// TODO: 如果没有, 从数据库里取
				
				// 再设置缓存
			}
			
			ret.put(g.getName(), g);
		}
		// 
		return ret;
	}

	/**
	 * 获取给管理员的图形列表
	 * @return
	 */
	public Graph getAdminGraphHashtable() {
		// 从数据库中取出所有图信息
		List<PointGroupData> pgdl = pointGroupDataMapper.getAdminGraphData();
		// 把目录填写上权限信息
		Hashtable<String,PointGroupData> pgdh = new Hashtable<String,PointGroupData>();
		for(int i =0;i<pgdl.size();i++) {
			PointGroupData pgd = pgdl.get(i);
			// 数据库里的name = path+fileSeperator+name
			String key = pgd.getName();
			pgdh.put(key, pgd);
		}
		// TODO 这里可能有空指针问题
		FileList fl = GraphManager.getInstance().getCurrentFileList();
		if(fl == null) return null;// 没有初始化
		Graph ret = getGraph(GraphManager.getInstance().getArllGraph(),pgdh);
		return ret;
	}
	
	/**
	 * 
	 * @param fl 文件树型
	 * @param pgdh 数据库里的带权限的点组数据
	 * @return
	 */
	public Graph getGraph(FileList fl,Hashtable<String,PointGroupData> pgdh) {
		if(fl == null) return null ;
		Graph ret = copyGraphFromFileList(fl);
		// 数据库里的name = path+fileSeperator+name
		String key = fl.getPath()+File.pathSeparator+fl.getName();
		PointGroupData pgd = pgdh.get(key);
		if(pgd!=null) { // 如果有这个权限设置，就加进去
			// ------------------ 设置创建者 -------------------
			String createrId = pgd.getCreater();
			User creater = userManager.getUserByID(createrId);
			if(creater == null) {
				// 用户信息还没有初始化,就初始化后重新取一遍。
				userManager.init();
				creater = userManager.getUserByID(createrId);
				// 如果还是没有，就是这个ID不存在。
				if(creater == null) throw new IllegalStateException("没有'"+createrId+"'这个用户id。");
			}
			ret.setCreater(creater);
			
			// ------------------ 设置拥有者 -------------------
			ret.setId(pgd.getId());
			String ownerId = pgd.getOwner();
			User owner = userManager.getUserByID(ownerId);
			if(owner == null) throw new IllegalStateException("没有'"+ownerId+"'这个用户id。");
			ret.setCreater(owner);
			
			// ------------------ 设置分享者 -------------------
			String sharedIds = pgd.getShared();
			String []ids = IDTools.splitID(sharedIds);
			ArrayList<User> shares = new ArrayList<User>();
			for(int iids=0;iids<ids.length;iids++) {
				String sid = ids[iids];
				User share = userManager.getUserByID(sid);
				if(owner == null) throw new IllegalStateException("没有'"+ownerId+"'这个用户id。");
				shares.add(share);
			}
			ret.setShared(shares);
			
			ret.setOtherrule1(pgd.getOtherrule2());
			ret.setOtherrule2(pgd.getOtherrule1());
		}
		return ret;
	}

	public Graph copyGraphFromFileList(FileList fl) {
		Graph ret = new Graph();
		
		ret.setChanged(fl.getChanged());
		ret.setFile(fl.isFile());
		ret.setSVG(fl.isSVG());
		ret.setName(fl.getName());
		ret.setPath(fl.getPath());
		ret.setPointIDs(fl.getPointIDs());
		ret.setChildren(fl.getChildren());
		return ret;
	}

}
