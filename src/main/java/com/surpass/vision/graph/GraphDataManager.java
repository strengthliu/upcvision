package com.surpass.vision.graph;

import java.util.List;
import java.util.Map.Entry;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.appCfg.ServerConfig;
import com.surpass.vision.domain.FileList;
import com.surpass.vision.domain.Graph;
import com.surpass.vision.domain.HistoryData;
import com.surpass.vision.domain.PointGroup;
import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.domain.RealTimeData;
import com.surpass.vision.domain.User;
import com.surpass.vision.domain.UserSpace;
import com.surpass.vision.mapper.PointGroupDataMapper;
import com.surpass.vision.pointGroup.PointGroupDataManager;
import com.surpass.vision.server.Point;
import com.surpass.vision.server.ServerManager;
import com.surpass.vision.service.PointGroupService;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.tools.IDTools;
import com.surpass.vision.user.UserManager;

@Component
public class GraphDataManager extends PointGroupDataManager {

//	@Reference
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
	 * 根据图ID列表，生成一张从根开始的图。
	 * 用于UserSpace中的graph。
	 * 
	 * @param graphs
	 * @return
	 */
	public Graph getGraphsByKeys(String graphs) {
		Graph ret = GraphManager.getRootGraph();
		if(StringUtil.isBlank(graphs))return ret;
		// 分隔key
		String[] keys = IDTools.splitID(graphs);
		// 从缓存里取数据，如果没有，再调用服务。
		for (int ik = 0; ik < keys.length; ik++) {
			// 从缓存里取图
			Graph g = this.getGraphRigidlyByKeys(keys[ik]);

			if (g == null) {
				System.out.println("没有指定ID=" + keys[ik] + "的图形。");
				// TODO: 发消息给管理员。
				// TODO: 把这个图形从userSpace中删除。
				
//				throw new IllegalStateException("没有指定ID=" + keys[ik] + "的图形。");
			} else {
				ret.addOrUpdateChild(g);
//				String path = g.getPath();
//				String splitChar = "";
//				if (File.separator.contentEquals("\\"))
//					splitChar = "\\\\";
//				else
//					splitChar = File.separator;
//				String[] folds = path.split(splitChar);
//				String curPath = "";
//				Graph curG = null;
//				for (int ifold = 0; ifold < folds.length; ifold++) {
//					String lastPath = curPath;
//					curPath = curPath + folds[ifold];
//					FileList fl = g.getChild(curPath);
//					if (fl == null) {
//						if (StringUtil.isBlank(lastPath)) {
//							ret.addChild(g);
//						}
//					}
//				}
			}
		}
		//
		return ret;
	}

	private Graph getGraphRigidlyByKeys(String idstr) {
		if (StringUtil.isBlank(idstr)) {
			throw new IllegalStateException("id不能为空。");
		}
		Double id = Double.valueOf(idstr);
		Graph ret = this.getGraphByKeys(idstr);
		if (ret == null) {
			PointGroupData pgd = pointGroupService.getPointGroupDataByID(id);
			ret = this.copyFromPointGroupData(pgd);
			this.redisService.set(GlobalConsts.Key_Graph_pre_ + IDTools.toString(id), ret);
		}
		return ret;
	}

	private Graph getGraphByKeys(String key) {
		Graph ret = (Graph) redisService.get(GlobalConsts.Key_Graph_pre_ + key);
		return ret;
	}

	/**
	 * 获取给管理员的图形列表
	 * 
	 * @return
	 */
	public Graph getAdminGraph() {
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

		Graph ret = null;
		try {
			ret = copyGraphFromFileList(fl, pgdh);
		} catch (Exception e) {
			System.out.println("copyGraphFromFileList时异常");
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 根据一个FileList，生成相应的Graph数据。
	 * 
	 * @param fl
	 * @param pgdh 数据库里的权限信息
	 * @return
	 */
	public Graph copyGraphFromFileList(FileList fl, Hashtable<String, PointGroupData> pgdh) {
		boolean noCache = false;
		Graph ret = new Graph();
		// 设置基本信息
		ret.setChanged(fl.getChanged());
		ret.setFile(fl.isFile());
		ret.setSVG(fl.isSVG());
		ret.setId(fl.getId());
		// 设置文件名
		if (fl.isFile())
			ret.setFileName(fl.getName());
		else
			ret.setFileName(null);

		String name = fl.getName();
		if (name.lastIndexOf(".") > 0)
			name = name.substring(0, name.lastIndexOf("."));
		// 设置名字，名字是去掉扩展名的
		ret.setName(name);
		ret.setPath(fl.getPath());
		ret.setOtherrule1(fl.getOtherrule1());
		ret.setOtherrule2(fl.getOtherrule2());
		// 设置类型
		ret.setType(fl.getType());

		// 设置缩略图
		if (fl.getImg() == null || fl.getImg() == "")
			ret.setImg(graphDefaultImg);

		// 设置URLpath
		String urlPath = ServerConfig.getInstance().getURLFromPath(fl.getWholePath());
		if(StringUtil.isBlank(urlPath))
			urlPath ="";
		ret.setUrlPath(urlPath);

		// 如果是文件
		if (fl.isFile()) {
			Graph retg = null;

			// 设置点位信息
			if (StringUtil.isBlank(fl.getPoints())) {
				ret.setPoints(fl.getPoints());
			} else
				ret.setPoints(fl.getPoints());

			ArrayList<Point> pal = new ArrayList<>();
			String[] pids = IDTools.splitID(ret.getPoints());
			for (int ipids = 0; ipids < pids.length; ipids++) {
				String serverName = splitServerName(pids[ipids]);
				String pName = splitPointName(pids[ipids]);
				Point p = ServerManager.getInstance().getPointByID(serverName, pName);
				pal.add(p);
			}
			ret.setPointList(pal);

			// 同步数据库
			PointGroupData pgd = this.pointGroupService.getPointGroupDataByOtherRule1(GlobalConsts.Type_graph_,
					fl.getWholePath());
			if (pgd != null) {
				ret.setId(pgd.getId());
				ret.setCreater(pgd.getCreater());
				ret.setOwner(pgd.getOwner());
				ret.setShared(pgd.getShared());
			} else {
				Double id = IDTools.newID();
				ret.setId(id);
				ret.setCreater(GlobalConsts.UserAdminID);
				ret.setOwner(GlobalConsts.UserAdminID);

				// TODO: 处理数据库和缓存
				// 数据库中创建一条
				this.pointGroupService.newPointGroupData(ret);
			}
			setRightInfo(ret, pgd);

			// 更新缓存
			try {
				redisService.set(GlobalConsts.Key_Graph_pre_ + IDTools.toString(ret.getId()), ret);
			} catch (Exception e) {
				System.out.println();
				e.printStackTrace();
			}

			return ret;
		}

		// 如果是目录
		ret.setId(IDTools.newID());
		ret.setFile(false);

		// 如果是目录，递归取子
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

	public Graph copyFromPointGroupData(PointGroupData pgd) {
			if (pgd == null)
				return null;
			else if (!pgd.getType().contentEquals(GlobalConsts.Type_graph_)) {
				throw new IllegalStateException("数据类型不正确，需要图形的PointGroupData。");
			}
	
			Graph graph = null;
			FileList fl = GraphManager.getCurrentFileList();
			fl = fl.getChild(pgd.getId());
			graph = this.copyGraphFromFileList(fl, null);
			return graph;
//	
//	//		// 如果缓存里有，就取出返回
//	//		graph = (Graph) redisService.get(GlobalConsts.Key_Graph_pre_ + IDTools.toString(pgd.getId()));
//	//		if (graph != null) {
//	//			return graph;
//	//		}
//	
//			// 如果没有，就copy一份，再放到缓存里。
//			graph = new Graph();
//			graph.setCreater(pgd.getCreater());
//			graph.setCreaterUser(userManager.getUserByID(pgd.getCreater()));
//	
//			graph.setId(pgd.getId());
//			graph.setName(pgd.getName());
//			graph.setOtherrule1(pgd.getOtherrule1());
//			graph.setOtherrule2(pgd.getOtherrule2());
//			// 注释设置为Otherrule2
//			graph.setDesc(pgd.getOtherrule2());
//	
//			graph.setOwner(pgd.getOwner());
//			graph.setOwnerUser(userManager.getUserByID(pgd.getOwner()));
//	
//			// 设置FileList的相关信息
//			FileList fl = GraphManager.getCurrentFileList();
//			fl = fl.getChild(pgd.getId());
//			graph = this.copyGraphFromFileList(fl, null);
//			graph.setFile(fl.isFile());
//			graph.setSVG(fl.isSVG());
//			graph.setImg(fl.getImg());
//			graph.setPath(fl.getPath());
//			graph.setFileName(fl.getName());
//			graph.setUrlPath(ServerConfig.getInstance().getURLFromPath(fl.getWholePath()));
//	
//			graph.setPoints(pgd.getPoints());
//			ArrayList<Point> pal = new ArrayList<>();
//			String[] pids = IDTools.splitID(pgd.getPoints());
//			for (int ipids = 0; ipids < pids.length; ipids++) {
//				String serverName = splitServerName(pids[ipids]);
//				String pName = splitPointName(pids[ipids]);
//				Point p = ServerManager.getInstance().getPointByID(serverName, pName);
//				pal.add(p);
//			}
//			graph.setPointList(pal);
//	
//			graph.setShared(pgd.getShared());
//			ArrayList<User> ul = new ArrayList<User>();
//			String[] sharedIds = IDTools.splitID(pgd.getShared());
//			for (int isharedIDs = 0; isharedIDs < sharedIds.length; isharedIDs++) {
//				User u = userManager.getUserByID(sharedIds[isharedIDs]);
//				ul.add(u);
//			}
//			graph.setSharedUsers(ul);
//	
//			graph.setType(pgd.getType());
//			redisService.set(GlobalConsts.Key_Graph_pre_ + IDTools.toString(pgd.getId()), graph);
//	
//			return graph;
		}

	public void setRightInfo(Graph ret, PointGroupData pgd) {
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
			ret.setSharedUsers(shares);

			ret.setOtherrule1(pgd.getOtherrule1());
			ret.setOtherrule2(pgd.getOtherrule2());
		} else {
			// ------------------ 设置创建者 -------------------
			String createrId = ret.getCreater();
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
			String ownerId = ret.getOwner();
			User owner = userManager.getUserByID(ownerId);
			if (owner == null)
				throw new IllegalStateException("没有'" + ownerId + "'这个用户id。");
			ret.setOwnerUser(owner);

			// ------------------ 设置分享者 -------------------
			String sharedIds = ret.getShared();
			String[] ids = IDTools.splitID(sharedIds);
			ArrayList<User> shares = new ArrayList<User>();
			for (int iids = 0; iids < ids.length; iids++) {
				String sid = ids[iids];
				User share = userManager.getUserByID(sid);
				if (owner == null)
					throw new IllegalStateException("没有'" + ownerId + "'这个用户id。");
				shares.add(share);
			}
			ret.setSharedUsers(shares);
		}

	}

	public Hashtable<String, ArrayList<Graph>> rebuildGraph(Hashtable<String, Graph> graphs) {
		Hashtable<String, ArrayList<Graph>> ret = new Hashtable<String, ArrayList<Graph>>();

		for (Entry entry : graphs.entrySet()) {
			Graph graph = (Graph) entry.getValue();
			ret.putAll(rebuildGraph(graph));
		}
		return ret;
	}

	private Hashtable<String, ArrayList<Graph>> rebuildGraph(Graph g) {
		Hashtable<String, ArrayList<Graph>> ret = new Hashtable<String, ArrayList<Graph>>();

		if (g.isFile()) { // 找到了文件
			String nk = g.getPath();
			if (nk.contains(graphPath))
				nk = nk.substring(graphPath.length());
			while (nk.startsWith("\\"))
				nk = nk.substring(1);
			// if(nk.length()<=0) return ret1;

			ArrayList<Graph> gs = ret.get(nk);
			if (gs == null)
				gs = new ArrayList<Graph>();
			String name = g.getName();
			// g.setFileName(name);
			if (name.lastIndexOf(".") > 0)
				name = name.substring(0, name.lastIndexOf("."));
			g.setName(name);
			gs.add(g);
			ret.put(nk, gs);
			return ret;
		} else { // 如果是目录
			if (g.getChildren().size() > 0) {
				Hashtable<String, Graph> children = g.getChildren();
				for (Entry entry : children.entrySet()) {
					Hashtable<String, ArrayList<Graph>> r = rebuildGraph((Graph) entry.getValue());
					for (Entry entryr : r.entrySet()) {
						ArrayList<Graph> al = ret.get(entryr.getKey());
						if (al == null)
							al = new ArrayList<Graph>();
						ArrayList<Graph> rl = r.get(entryr.getKey());
//					    al.addAll(rl);
						if (rl != null)
							for (int i = 0; i < rl.size(); i++)
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
		if (pgd == null) {
			throw new IllegalStateException("没有id为" + itemId + "这个数据");
		}
		String sharedUserIDs = "";
		if (userIdsid != null) {
			sharedUserIDs = IDTools.merge(userIdsid.toArray());
		}
		pgd.setShared(sharedUserIDs);
		// 更新数据库
		pointGroupService.updatePointGroupItem(pgd);

		// 更新缓存
		Graph rtd = this.copyFromPointGroupData(pgd);
		// 写缓存HistoryData，返回
		redisService.set(GlobalConsts.Key_HistoryData_pre_ + IDTools.toString(rtd.getId()), rtd);

		return rtd;
	}

	/**
	 * 取出fileList的数据库信息
	 * 
	 * @param fl
	 * @return
	 */
	public FileList getDatabaseInfoByPath(FileList fl) {
		String wholePath = fl.getOtherrule1();
		PointGroupData pgd = pointGroupService.getPointGroupDataByOtherRule1(GlobalConsts.Type_graph_, wholePath);
		if (pgd != null) {
			fl.setId(pgd.getId());
			fl.setName(pgd.getName());
			fl.setCreater(pgd.getCreater());
			fl.setOwner(pgd.getOwner());
			fl.setShared(pgd.getShared());
			fl.setOtherrule1(pgd.getOtherrule1());
			fl.setOtherrule2(pgd.getOtherrule2());
			fl.setType(pgd.getType());
			String points = pgd.getPoints();
			if (!fl.getPoints().contentEquals(points)) {
				pgd.setPoints(fl.getPoints());
				pointGroupService.updatePointGroupItem(pgd);
			}
		}
		return fl;
	}

}
