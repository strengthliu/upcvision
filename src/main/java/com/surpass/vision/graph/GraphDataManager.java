package com.surpass.vision.graph;

import java.util.List;
import java.util.Map.Entry;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.appCfg.ServerConfig;
import com.surpass.vision.domain.DepartmentInfo;
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
import com.surpass.vision.userSpace.UserSpaceManager;

@Component
public class GraphDataManager extends PointGroupDataManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(GraphDataManager.class);

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

	// 图形的URL目录
	@Value("${upc.graphDirDefaultImg}")
	private String graphDirDefaultImg;


	Graph getGraphRigidlyByKeys(String idstr) {
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
			// TODO: 这里涉及到名字了
			String key = pgd.getName();
			pgdh.put(key, pgd);
		}
		FileList fl = GraphManager.getCurrentFileList();
		if (fl == null)
			return null;// 没有初始化
		Graph ret = getGraph(GraphManager.getInstance().getArllGraph());
		return ret;
	}

	/**
	 * 
	 * @param fl   文件树型
	 * -- @param pgdh 数据库里的带权限的点组数据
	 * @return
	 */
	public Graph getGraph(FileList fl) {
		if (fl == null)
			return null;

		Graph ret = null;
		try {
			long t1 = System.currentTimeMillis();
			ret = copyGraphFromFileList(fl);
			long t2 = System.currentTimeMillis();
			long tt = (t2-t1);
			if(tt>100)
				LOGGER.debug(" ->  -> getGraph 用时："+tt+" 毫秒 "+fl.getName());
		} catch (Exception e) {
			System.out.println("copyGraphFromFileList时异常");
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 根据一个FileList，生成相应的Graph数据。同时用数据库中的信息更新fl信息，包括权限等，以保持数据一致。
	 * 
	 * @param fl
	 * -- @param pgdh 数据库里的权限等信息。这个不用了，修改为现从数据库中取信息。
	 * @return
	 */
	public Graph copyGraphFromFileList(FileList fl) {

		long t1 = System.currentTimeMillis();
		long t2 = System.currentTimeMillis();
		long tt = (t2-t1);
//		System.out.println(" ->  -> copyGraphFromFileList 用时："+tt+" 毫秒");

		boolean noCache = false;
		Graph ret = new Graph(); // 返回的图形
		
		// 设置fl中的基本信息
		ret.setChanged(fl.getChanged());
		ret.setFile(fl.isFile());
		ret.setSVG(fl.isSVG());
//		ret.setId(fl.getId());
		
		ret.setPointTextIDs(fl.getPointTextIDs());
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
		String otherrule3 = "";
		if(fl.getPointTextIDs()!=null)
		for(int indt =0;indt<fl.getPointTextIDs().size();indt++) {
			otherrule3 += fl.getPointTextIDs().get(indt)+GlobalConsts.Key_splitChar;
		}
		if(otherrule3.length()>1)
			otherrule3 = otherrule3.substring(0,otherrule3.length()-1);
		fl.setOtherrule3(otherrule3);
		ret.setOtherrule3(otherrule3);
		// 设置类型
		ret.setType(fl.getType());

		// 设置缩略图
		if (fl.getImg() == null || fl.getImg() == "") {
			if(fl.isFile())
				ret.setImg(graphDefaultImg);
			else
				ret.setImg(graphDirDefaultImg);
		}

		// 设置URLpath
		String urlPath = ServerConfig.getInstance().getURLFromPath(fl.getWholePath());
		if(StringUtil.isBlank(urlPath))
			urlPath ="";
		ret.setUrlPath(urlPath);

		// 如果是文件
		if (fl.isFile()) {
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
			//这里用到的getWholePath涉及到名字了
			PointGroupData pgd = this.pointGroupService.getPointGroupDataByOtherRule1(GlobalConsts.Type_graph_,
					fl.getWholePath());
			if (pgd != null) {
				// 用数据库的信息更新fileList
				fl.setId(pgd.getId());
				fl.setType(pgd.getType());
				fl.setName(pgd.getName());
				fl.setOwner(pgd.getOwner());
				fl.setCreater(pgd.getCreater());
				fl.setShared(pgd.getShared());
				fl.setShareddepart(pgd.getShareddepart());
//				fl.setPoints(pgd.getPoints());
				fl.setOtherrule1(pgd.getOtherrule1());
				fl.setOtherrule2(pgd.getOtherrule2());
//				fl.setOtherrule3(pgd.getOtherrule3());
				fl.setOtherrule4(pgd.getOtherrule4());
				fl.setOtherrule5(pgd.getOtherrule5());
				setRightInfo(ret, pgd);
				// 用图形文件中的点更新数据库
				pgd.setPoints(fl.getPoints());
				pgd.setOtherrule3(fl.getOtherrule3());
				// 这是个异步执行
				this.pointGroupService.updatePointGroupItem(pgd);
				
				ret.setId(pgd.getId());
				ret.setType(pgd.getType());
				ret.setName(pgd.getName());
				ret.setOwner(pgd.getOwner());
				ret.setCreater(pgd.getCreater());
				ret.setShared(pgd.getShared());
				ret.setShareddepart(pgd.getShareddepart());
				ret.setPoints(pgd.getPoints());
				ret.setOtherrule1(pgd.getOtherrule1());
				ret.setOtherrule2(pgd.getOtherrule2());
				ret.setOtherrule3(pgd.getOtherrule3());
				ret.setOtherrule4(pgd.getOtherrule4());
				ret.setOtherrule5(pgd.getOtherrule5());
				setRightInfo(ret, pgd);
				ret.setNickName(pgd.getOtherrule4());
				if(!StringUtil.isBlank(pgd.getOtherrule5()))
					ret.setImg(pgd.getOtherrule5());
				ret.setDesc(pgd.getOtherrule2());

			} else {
				Double id = IDTools.newID();
				ret.setId(id);
				ret.setCreater(GlobalConsts.UserAdminID);
				ret.setOwner(GlobalConsts.UserAdminID);
				ret.setNickName(fl.getNickName());
				ret.setOtherrule4(fl.getNickName());
				ret.setOtherrule5(fl.getImg());
				ret.setImg(fl.getImg());
				ret.setOtherrule2(fl.getDesc());

				// TODO: 处理数据库和缓存
				// 数据库中创建一条
				this.pointGroupService.newPointGroupData(ret);
			}

			// 更新缓存
			try {
				redisService.set(GlobalConsts.Key_Graph_pre_ + IDTools.toString(ret.getId()), ret);
			} catch (Exception e) {
				System.out.println();
				e.printStackTrace();
			}
			return ret;
		} else {
			// 如果是目录
			ret.setId(IDTools.newID());
			ret.setFile(false);
			// 同步数据库
			//这里用到的getWholePath涉及到名字了
			PointGroupData pgd = this.pointGroupService.getPointGroupDataByOtherRule1(GlobalConsts.Type_graph_,
					fl.getWholePath());
			if (pgd != null) { // 数据库里有了，就取出权限信息
				// 用数据库的信息更新fileList
				fl.setId(pgd.getId());
				fl.setType(pgd.getType());
				fl.setName(pgd.getName());
				fl.setOwner(pgd.getOwner());
				fl.setCreater(pgd.getCreater());
				fl.setShared(pgd.getShared());
				fl.setShareddepart(pgd.getShareddepart());
				fl.setPoints(pgd.getPoints());
				fl.setOtherrule1(pgd.getOtherrule1());
				fl.setOtherrule2(pgd.getOtherrule2());
				fl.setOtherrule3(pgd.getOtherrule3());
				fl.setOtherrule4(pgd.getOtherrule4());
				fl.setOtherrule5(pgd.getOtherrule5());
				setRightInfo(ret, pgd);
				
				ret.setId(pgd.getId());
				ret.setType(pgd.getType());
				ret.setName(pgd.getName());
				ret.setOwner(pgd.getOwner());
				ret.setCreater(pgd.getCreater());
				ret.setShared(pgd.getShared());
				ret.setShareddepart(pgd.getShareddepart());
				ret.setPoints(pgd.getPoints());
				ret.setOtherrule1(pgd.getOtherrule1());
				ret.setOtherrule2(pgd.getOtherrule2());
				ret.setOtherrule3(pgd.getOtherrule3());
				ret.setOtherrule4(pgd.getOtherrule4());
				ret.setOtherrule5(pgd.getOtherrule5());
				setRightInfo(ret, pgd);
				ret.setNickName(pgd.getOtherrule4());
				if(!StringUtil.isBlank(pgd.getOtherrule5()))
					ret.setImg(pgd.getOtherrule5());
				else
					ret.setImg(graphDefaultImg);
				ret.setDesc(pgd.getOtherrule2());

			}else { // 否则就写入数据库
				Double id = IDTools.newID();
				ret.setName(fl.getName());
				ret.setId(id);
				ret.setType(fl.getType());
				ret.setCreater(GlobalConsts.UserAdminID);
				ret.setOwner(GlobalConsts.UserAdminID);
				ret.setNickName(fl.getNickName());
				ret.setOtherrule4(fl.getNickName());
				ret.setOtherrule5(fl.getImg());
//				ret.setImg(fl.getImg());
				if(!StringUtil.isBlank(fl.getOtherrule5()))
					ret.setImg(fl.getOtherrule5());
				else
					ret.setImg(graphDefaultImg);

				ret.setOtherrule2(fl.getDesc());

				// TODO: 处理数据库和缓存
				// 数据库中创建一条
				this.pointGroupService.newPointGroupData(ret);

			}
			// 更新缓存
			try {
				Graph _g = ret.copyRootNode();
				redisService.set(GlobalConsts.Key_Graph_pre_ + IDTools.toString(ret.getId()), _g);
			} catch (Exception e) {
				System.out.println();
				e.printStackTrace();
			}

			// 如果是目录，递归取子
			Hashtable<String, FileList> flh = fl.getChildren();
			if (flh != null) {
				Hashtable<String, Graph> children = new Hashtable<String, Graph>();
				flh.forEach((k, v) -> {
					Graph graph = getGraph(v);
					children.put(k, graph);
					// System.out.println();
				});
				ret.setChildren(children);
			}
			return ret;
		}
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
			fl.copyPointGroupData(pgd);
			graph = this.copyGraphFromFileList(fl); // 去掉了pgd参数后，会在这个方法里再取一次数据库。增加了压力，但也增加了同步。
			return graph;
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

			// ------------------ 设置分享部门 -------------------
			String sharedDeps = pgd.getShareddepart();
			String[] deps = IDTools.splitID(sharedDeps);
			ArrayList<DepartmentInfo> departs = new ArrayList<DepartmentInfo>();
			for (int iids = 0; iids < deps.length; iids++) {
				String sid = deps[iids];
				DepartmentInfo share = userManager.getDepartmentInfoByID(sid);
				if (owner == null)
					throw new IllegalStateException("没有'" + ownerId + "'这个部门id。");
				departs.add(share);
			}
			ret.setSharedDepartment(departs);

			ret.setOtherrule1(pgd.getOtherrule1());
			ret.setOtherrule2(pgd.getOtherrule2());
			ret.setOtherrule3(pgd.getOtherrule3());
			ret.setOtherrule4(pgd.getOtherrule4());
			ret.setOtherrule5(pgd.getOtherrule5());
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
			
			// ------------------ 设置分享部门 -------------------
			String sharedDeps = pgd.getShareddepart();
			String[] deps = IDTools.splitID(sharedDeps);
			ArrayList<DepartmentInfo> departs = new ArrayList<DepartmentInfo>();
			for (int iids = 0; iids < deps.length; iids++) {
				String sid = deps[iids];
				DepartmentInfo share = userManager.getDepartmentInfoByID(sid);
				if (owner == null)
					throw new IllegalStateException("没有'" + ownerId + "'这个部门id。");
				departs.add(share);
			}
			ret.setSharedDepartment(departs);

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
		redisService.set(GlobalConsts.Key_Graph_pre_ + IDTools.toString(rtd.getId()), rtd);

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
			fl.setOtherrule3(pgd.getOtherrule3());
			fl.setOtherrule4(pgd.getOtherrule4());
			fl.setOtherrule5(pgd.getOtherrule5());
			fl.setType(pgd.getType());
			String points = pgd.getPoints();
			if (!fl.getPoints().contentEquals(points)) {
				pgd.setPoints(fl.getPoints());
				pointGroupService.updatePointGroupItem(pgd);
			}
		}
		return fl;
	}
	public Graph updateGraph(Graph g) {
		PointGroupData pgd = pointGroupService.getPointGroupDataByID(g.getId());
		if (pgd == null) {
			throw new IllegalStateException("没有id为" + g.getId() + "这个数据");
		}
		pgd.setCreater(g.getCreater());
		pgd.setId(g.getId());
		//pgd.setPointTextIDs(g.getPointTextIDs());
		// 设置文件名
		String name = g.getName();
		if (name.lastIndexOf(".") > 0)
			name = name.substring(0, name.lastIndexOf("."));
		// 设置名字，名字是去掉扩展名的
		pgd.setName(name);
		pgd.setOtherrule4(g.getNickName());
		pgd.setOtherrule2(g.getDesc());
		pgd.setCreater(g.getCreater());
		pgd.setOwner(g.getOwner());
		// 设置缩略图
		if (StringUtil.isBlank(g.getImg())) {
			if(g.isFile())
				pgd.setOtherrule5(graphDefaultImg);
			else
				pgd.setOtherrule5(graphDirDefaultImg);
		}
		else
			pgd.setOtherrule5(g.getImg());
		// 更新数据库，异步的。
		pointGroupService.updatePointGroupItem(pgd);
		// 在graphManager里更新repo
		
		// 这里copy一份可能会出错，因为前面是异步写数据库，这里copy里，还没有更新完成。
		// Graph rtd = this.copyFromPointGroupData(pgd);
		// 更新缓存
		redisService.set(GlobalConsts.Key_Graph_pre_ + IDTools.toString(g.getId()), g);

		return g;
	}

	public boolean deleteGraph(Graph g) {
		try {
			pointGroupService.deletePointGroupItem(g.getId());
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
