package com.surpass.vision.graph;

import java.util.List;
import java.util.Map.Entry;
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
import com.surpass.vision.domain.HistoryData;
import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.domain.RealTimeData;
import com.surpass.vision.domain.User;
import com.surpass.vision.mapper.PointGroupDataMapper;
import com.surpass.vision.pointGroup.PointGroupDataManager;
import com.surpass.vision.server.Point;
import com.surpass.vision.server.ServerManager;
import com.surpass.vision.service.PointGroupService;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.tools.IDTools;
import com.surpass.vision.user.UserManager;
import com.surpass.vision.utils.StringUtil;

@Component
public class GraphDataManager extends PointGroupDataManager {

	@Reference
	@Autowired
	RedisService redisService;

//	@Autowired
//	PointGroupDataMapper pointGroupDataMapper;

	@Autowired
	PointGroupService pointGroupService;
	// TODO Auto-generated method stub

	@Reference
	@Autowired
	UserManager userManager;

	@Value("${upc.graphPath}")
	private String graphPath;
	@Value("${upc.graphServerPath}")
	private String graphServerPath;

	@Value("${upc.graphDefaultImg}")
	private String graphDefaultImg;

	/**
	 * 从缓存里取数据，如果没有，再调用服务。
	 * 
	 * @param graphs
	 * @return
	 */
	public Hashtable<String, Graph> getGraphHashtableByKeys(String graphs) {
		Hashtable<String, Graph> ret = new Hashtable<String, Graph>();
		// 分隔key
		String[] keys = IDTools.splitID(graphs);
		for (int ik = 0; ik < keys.length; ik++) {
			// 从缓存里取图
			Graph g = (Graph) redisService.get(keys[ik]);
			if (g == null) {
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
	 * 
	 * @return
	 */
	public Graph getAdminGraphHashtable() {
		// 从数据库中取出所有图信息
		List<PointGroupData> pgdl = pointGroupService.getAdminGraphData();
		// 把目录填写上权限信息
		Hashtable<String, PointGroupData> pgdh = new Hashtable<String, PointGroupData>();
		for (int i = 0; i < pgdl.size(); i++) {
			PointGroupData pgd = pgdl.get(i);
			// 数据库里的name = path+fileSeperator+name
			String key = pgd.getName();
			pgdh.put(key, pgd);
		}
		FileList fl = GraphManager.getInstance().getCurrentFileList();
		if (fl == null)
			return null;// 没有初始化
		Graph ret = getGraph(GraphManager.getInstance().getArllGraph(), pgdh);
		return ret;
	}

	/**
	 * 
	 * @param fl   文件树型
	 * @param pgdh 数据库里的带权限的点组数据
	 * @return
	 */
	public Graph getGraph(FileList fl, Hashtable<String, PointGroupData> pgdh) {
		if (fl == null)
			return null;
		Graph ret = copyGraphFromFileList(fl, pgdh);
		// 数据库里的name = path+fileSeperator+name
		String key = fl.getPath() + File.pathSeparator + fl.getName();
		PointGroupData pgd = pgdh.get(key);
		if (pgd != null) { // 如果有这个权限设置，就加进去
			// ------------------ 设置创建者 -------------------
			String createrId = pgd.getCreater();
			User creater = userManager.getUserByID(createrId);
			if (creater == null) {
				// 用户信息还没有初始化,就初始化后重新取一遍。
				userManager.init();
				creater = userManager.getUserByID(createrId);
				// 如果还是没有，就是这个ID不存在。
				if (creater == null)
					throw new IllegalStateException("没有'" + createrId + "'这个用户id。");
			}
			ret.setCreaterUser(creater); // 

			// ------------------ 设置拥有者 -------------------
			ret.setId(Double.valueOf(pgd.getId()));
			String ownerId = pgd.getOwner();
			User owner = userManager.getUserByID(ownerId);
			if (owner == null)
				throw new IllegalStateException("没有'" + ownerId + "'这个用户id。");
			ret.setOwnerUser(owner);

			// ------------------ 设置分享者 -------------------
			String sharedIds = pgd.getShared();
			String[] ids = IDTools.splitID(sharedIds);
			ArrayList<User> shares = new ArrayList<User>();
			for (int iids = 0; iids < ids.length; iids++) {
				String sid = ids[iids];
				User share = userManager.getUserByID(sid);
				if (owner == null)
					throw new IllegalStateException("没有'" + ownerId + "'这个用户id。");
				shares.add(share);
			}
			ret.setShared(shares);

			ret.setOtherrule1(pgd.getOtherrule2());
			ret.setOtherrule2(pgd.getOtherrule1());
		}
		return ret;
	}

	public Graph copyGraphFromFileList(FileList fl, Hashtable<String, PointGroupData> pgdh) {
		Graph ret = new Graph();

		ret.setChanged(fl.getChanged());
		ret.setFile(fl.isFile());
		ret.setSVG(fl.isSVG());
		ret.setName(fl.getName());
		if(fl.isFile()) {
			ret.setFileName(fl.getName());
			String name = fl.getName();
			if(name.lastIndexOf(".")>0)
				name = name.substring(0,name.lastIndexOf("."));
			ret.setName(name);
		}
		if(fl.getImg()==null || fl.getImg() == "") ret.setImg(graphDefaultImg);
		String path = fl.getPath();
		ret.setPath(path);
		if(path!=null && path.contains(graphPath)) {
			path = path.substring(graphPath.length());
		} 
		// 如果是根，就用.代替
		//if(path==null) path=".";
		// 开头结尾加上/
		//System.out.println(path);
		String seperator = "/";
//		if(path!=null && path.length()>=1) {
////			StringUtil.replace(path, "\\\\", "a");
//			path = path.replaceAll("\\\\", "/");
//			path = path.replaceAll("\\\\\\\\", "/");
//			path = path.replaceAll("//", "/");
//		}
//		String a = "\\热力";
//		a.replaceAll("\\\\", "////");
//		System.out.println(a);
		if(!graphServerPath.startsWith(seperator)) graphServerPath = seperator + graphServerPath ;
		if(!graphServerPath.endsWith(seperator)) graphServerPath =  graphServerPath + seperator;
		if(path==null) path = graphServerPath ;
		else path = graphServerPath + path;
		path = path.replaceAll("\\\\", "/");
		path = path.replaceAll("\\\\\\\\", "/");
		path = path.replaceAll("//", "/");

		System.out.println(path);
		ret.setUrlPath(path+"/"+ret.getFileName());
		// ret.setPointIDs(fl.getPointIDs());
//		ret.setChildren(fl.getChildren());
		Hashtable<String, FileList> flh = fl.getChildren();
		if (flh != null) {
			Hashtable<String, Graph> children = new Hashtable<String, Graph>();
			flh.forEach((k, v) -> {
				Graph graph = getGraph(v, pgdh);
				children.put(k, graph);
				// System.out.println();
			});
			ret.setChildren(children);
		}

		return ret;
	}
	

	public Hashtable<String, ArrayList<Graph>> rebuildGraph(Hashtable<String, Graph> graphs) {
		Hashtable<String, ArrayList<Graph>> ret = new Hashtable<String, ArrayList<Graph>>();

		for(Entry entry : graphs.entrySet()){
			Graph graph = (Graph)entry.getValue();
			ret.putAll(rebuildGraph(graph));
		}
		return ret;
	}

	private Hashtable<String, ArrayList<Graph>> rebuildGraph(Graph g) {
		Hashtable<String, ArrayList<Graph>>	ret = new Hashtable<String, ArrayList<Graph>>();

		if(g.isFile()) { // 找到了文件
			String nk = g.getPath();
			if(nk.contains(graphPath)) 
				nk = nk.substring(graphPath.length());
			while(nk.startsWith("\\"))
				nk = nk.substring(1);
			// if(nk.length()<=0) return ret1;
			ArrayList<Graph> gs = ret.get(nk);
			if(gs==null) gs= new ArrayList<Graph>();
			String name = g.getName();
			//g.setFileName(name);
			if(name.lastIndexOf(".")>0)
				name = name.substring(0,name.lastIndexOf("."));
			g.setName(name);
			
			gs.add(g);
			ret.put(nk, gs);
			return ret;
		} else { // 如果是目录 
			if(g.getChildren().size()>0) {
				Hashtable<String,Graph> children = g.getChildren();
				for(Entry entry : children.entrySet()){
					Hashtable<String, ArrayList<Graph>> r = rebuildGraph((Graph)entry.getValue());
					for(Entry entryr : r.entrySet()){
					    ArrayList<Graph> al = ret.get(entryr.getKey());
					    if(al == null) al = new ArrayList<Graph>();
					    ArrayList<Graph> rl = r.get(entryr.getKey());
//					    al.addAll(rl);
					    if(rl!=null)
					    for(int i=0;i<rl.size();i++)
					    	al.add(rl.get(i));
					    ret.put((String) entryr.getKey(), al);
					}

//					ret.putAll(r);
				}
			}
		}
		return ret;
	}
	
	public Graph updateShareRight(Double itemId, List<String> userIdsid) {
		PointGroupData pgd = pointGroupService.getPointGroupDataByID(itemId);
		if(pgd == null) {
			throw new IllegalStateException("没有id为"+itemId+"这个数据");
		}
		String sharedUserIDs = "";
		if(userIdsid != null) {
			sharedUserIDs = IDTools.merge(userIdsid.toArray());
		}
		pgd.setShared(sharedUserIDs);
		// 更新数据库
		pointGroupService.updatePointGroupItem(pgd);
		
		// 更新缓存
		Graph rtd = this.copyFromPointGroupData(pgd);
		// 写缓存HistoryData，返回
		redisService.set(GlobalConsts.Key_HistoryData_pre_+IDTools.toString(rtd.getId()),rtd);

		return rtd;
	}


	public Graph copyFromPointGroupData(PointGroupData pgd) {
		if (pgd == null)
			return null;
		Graph graph = new Graph();

		graph.setCreater(pgd.getCreater());
		graph.setCreaterUser(userManager.getUserByID(pgd.getCreater()));

		graph.setId(pgd.getId());
		graph.setName(pgd.getName());
		graph.setOtherrule1(pgd.getOtherrule1());
		graph.setOtherrule2(pgd.getOtherrule2());
		graph.setDesc(pgd.getOtherrule2());

		graph.setOwner(pgd.getOwner());
		graph.setOwnerUser(userManager.getUserByID(pgd.getOwner()));

		graph.setPoints(pgd.getPoints());
		ArrayList<Point> pal = new ArrayList<>();
		String[] pids = IDTools.splitID(pgd.getPoints());
		for (int ipids = 0; ipids < pids.length; ipids++) {
			String serverName = splitServerName(pids[ipids]);
			String pName = splitPointName(pids[ipids]);
			Point p = ServerManager.getInstance().getPointByID(serverName,pName);
			pal.add(p);
		}
		graph.setPointList(pal);

		graph.setShared(pgd.getShared());
		ArrayList<User> ul = new ArrayList<User>();
		String[] sharedIds = IDTools.splitID(pgd.getShared());
		for (int isharedIDs = 0; isharedIDs < sharedIds.length; isharedIDs++) {
			User u = userManager.getUserByID(sharedIds[isharedIDs]);
			ul.add(u);
		}
		graph.setSharedUsers(ul);

		graph.setType(pgd.getType());

		return graph;
	}

	public Graph getGraphByKeys(Double oldRtdId) {
		Graph rtd = (Graph)redisService.get(GlobalConsts.Key_Graph_pre_+IDTools.toString(oldRtdId));
		return rtd;

	}

}
