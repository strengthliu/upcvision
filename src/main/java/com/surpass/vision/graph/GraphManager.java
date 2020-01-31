package com.surpass.vision.graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.domain.FileList;
import com.surpass.vision.domain.Graph;
import com.surpass.vision.domain.HistoryData;
import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.domain.XYGraph;
import com.surpass.vision.mapper.PointGroupDataMapper;
import com.surpass.vision.server.ServerManager;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.tools.FileTool;
import com.surpass.vision.tools.IDTools;

/**
 * ---------------------------------------
 * 
 * @Title: GraphManager.java
 * @Package:com.surpass.vision.graph ---------------------------------------
 * @Description: 图形管理器，具体图形实例的管理。 每个公司有都有自己的图形， 分布式情况： 没想好呢，暂不支持分布式部署
 *               ---------------------------------------
 * @author 刘强 2020年1月30日 下午2:15:48
 * @version: v1.0 ---------------------------------------
 */
@Component
public class GraphManager extends GraphDataManager {

	/**
	 * 单例模式
	 */
	private static GraphManager instance;

	public static synchronized GraphManager getInstance() {
		if (instance == null) {
			instance = new GraphManager();
			if (instance.repo == null)
				instance.repo = new Graph();
			instance.changed = new ArrayList<FileList>();
		}
		return instance;
	}

	private GraphManager() {
		super();
		if (instance == null)
			instance = this;
		if (repo == null)
			repo = new Graph();
	}

	/**
	 * 当前全部图形的实例
	 */
	private static Graph repo;
//	private static FileList repo;

	public static Graph getGraphTree() {
		return repo.clone().clearLeaf();
	}

	public static FileList getCurrentFileList() {
		return repo;// GraphManager.getInstance().repo;
	}

	public static Graph getRootGraph() {
		Graph ret = repo.copyRootNode();
		return ret;
	}

	public FileList getArllGraph() {
		return repo;
	}

	@Reference
	@Autowired
	RedisService redisService;

	@Autowired
	PointGroupDataMapper pointGroupDataMapper;

	@Autowired
	GraphDataManager graphDataManager;

	@Autowired
	ServerManager serverManager;

	@Value("${upc.graphPath}")
	private String graphPath;

	/****************************************************************************************
	 * 图形操作：初始化
	 */
	public void reloadFileList(String graphPath2) {
		try {
			FileTool.find(graphPath2, serverManager, graphDataManager, graphPath, repo);
			// 上传图形时，需要修改下面的这些值。再更新UserSpace
			Graph repo1 = copyGraphFromFileList(repo);
			repo = repo1;
//			graphs = graph.clone();
//			graphs.clearLeaf();
			printGraph(repo1, null);
			System.out.println("初始化图形目录树结束。");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/****************************************************************************************
	 * 图形操作：增
	 */
	public void addOrUpdateGraphToTree(Graph g) {
		// 如果库里有指定ID的这个图形，就更新
		
		// 否则就新增
		repo.addChild(g);
	}

	/****************************************************************************************
	 * 图形操作：删
	 */
	public boolean deleteGraph(Graph g) {
		// 删除数据库
		if (!super.deleteGraph(g))
			return false;

		// 删除文件
		if (!FileTool.delete(g.getWholePath()))
			return false;

		// 更新缓存
		redisService.delete(GlobalConsts.Key_Graph_pre_ + IDTools.toString(g.getId()));

		return true;
	}

	/****************************************************************************************
	 * 图形操作：改
	 */
//	@Override
	public Graph updateShareRight(Double itemId, List<String> userIdsid, List<String> depIdsid) {
		// 因为Graph与一般的XYGraph等不一样，他有一部分信息是存在FileList里，所以需要先取出来，再更新PointGroup的部分。
		Graph g = this.getGraphByKeys(itemId);
		// TODO: 修改全图 graph\graphs
		Graph _g = (Graph) updateShareRight(g, GlobalConsts.Key_Graph_pre_, itemId, userIdsid, depIdsid);
		if(_g!=null) {
			g = _g;
			return _g;
		}else
			return null;
	}

	@Override
	public Graph updateGraph(Graph _g) {
		Graph g = this.getGraphByKeys(_g.getId());
		if(g!=null) {
			g = _g;
		}
		return super.updateGraph(_g);
	}
	
//	public Graph updateShareRightDepartment(Double itemId, List<String> depIdsid) {
//		PointGroupData pgd = pointGroupService.getPointGroupDataByID(itemId);
//		if (pgd == null) {
//			throw new IllegalStateException("没有id为" + itemId + "这个数据");
//		}
//		String sharedDepIDs = "";
//		if (depIdsid != null) {
//			sharedDepIDs = IDTools.merge(depIdsid.toArray());
//		}
//		pgd.setShareddepart(sharedDepIDs);
//		// 更新数据库
//		pointGroupService.updatePointGroupItem(pgd);
//
//		// 更新缓存
//		Graph rtd = this.copyFromPointGroupData(pgd);
//		// 写缓存HistoryData，返回
//		redisService.set(GlobalConsts.Key_Graph_pre_ + IDTools.toString(rtd.getId()), rtd);
//
//		return rtd;
//
//	}

	/****************************************************************************************
	 * 图形操作：查
	 */
	public Graph getGraphByKeys(Double oldRtdId) {
		Graph rtd = (Graph) redisService.get(GlobalConsts.Key_Graph_pre_ + IDTools.toString(oldRtdId));
		// TODO: 如果没有，就从GraphManager
		if (rtd == null) {
//			this.children.h
		}
		return rtd;
	}

	public Graph getAdminGraph() {
		if(repo!=null)
			return repo;
		else
			return super.getAdminGraph();
	}
	/**
	 * 根据图ID列表，生成一张从根开始的图。 用于UserSpace中的graph。
	 * 
	 * @param graphs
	 * @return
	 */
	public Graph getGraphsByKeys(String graphs) {
		Graph ret = getRootGraph();
		if (StringUtil.isBlank(graphs))
			return ret;
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
				if (g.isFile()) {
					ret.addOrUpdateChild(g);
				} else {
					g = repo.getParentByPath(g.getPath());
					ret.addOrUpdateChild(g);
				}
			}
		}
		return ret;
	}

	private void printGraph(Graph g, String tab) {
		if (StringUtil.isBlank(tab))
			tab = "    ";
		System.out.println(tab + " " + g.getPath() + " - " + g.getName() + " isFile:" + g.isFile());
		if (g.getChildren() != null) {
			Enumeration<?> e = g.getChildren().keys();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				Graph _g = (Graph) g.getChildren().get(key);
				String _tab = tab + tab;
				printGraph(_g, tab);
			}
		}
	}

	public static void main(String[] args) {
		String[] aa = new String("c:\\dev").split("\\\\");
	}

//============================================================================
	@Deprecated
	@Value("${upc.graphUpdateTimeOut}")
	private int timeOut;

	@Deprecated
	private Integer version = 0;

	@Deprecated
	private String updater = "";

	@Deprecated
	private long beginUpdateTime = 0;

	@Deprecated
	private boolean updating = false;

	@Deprecated
	private ArrayList<FileList> changed;

	@Deprecated
	private static String interruptThread(String name) {
		ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
		int noThreads = currentGroup.activeCount();
		Thread[] lstThreads = new Thread[noThreads];
		currentGroup.enumerate(lstThreads);
		for (int i = 0; i < noThreads; i++) {
			if (lstThreads[i].getName().contentEquals(instance.updater)) {
				lstThreads[i].interrupt();
				System.out.println("线程号：" + i + " = " + lstThreads[i].getName() + ",，已经被中断。");
				return name;
			}
		}
		return null;
	}

	@Deprecated
	public static boolean isUpdating() {
		if (instance == null)
			return false;
		return instance.updating;
	}

	@Deprecated
	public static synchronized void updateVersion(String updater) {
		if (instance.updater != null && instance.updater.length() > 0)
			if (!instance.updater.contentEquals(updater)) {
				System.out.println("有线程跨过beginUpdateVersion直接执行updateVersion，该线程即将被中断。");
				String tname = GraphManager.interruptThread(updater);
				return;
			}
		instance.version++;
		instance.changed = new ArrayList<FileList>();
		instance.updating = true;
	}

	@Deprecated
	public static void addChange(FileList child) {
		instance.changed.add(child);
	}

	@Deprecated
	public static synchronized boolean beginUpdate(String updater) {
		if (instance.updater != null && instance.updater.length() > 0) {
			if (instance.updater.contentEquals(updater))
				return true;
			long curr = System.currentTimeMillis();
			if ((curr - instance.beginUpdateTime) / 1000 > instance.timeOut) {
				String tname = GraphManager.interruptThread(updater);
				if (tname != null) {
					System.out.println("线程：" + tname + ",因为执行超时，已经被中断。当前超时限制设置为：" + instance.timeOut + "秒，中断线程执行了"
							+ (curr - instance.beginUpdateTime) / 1000 + "秒。");
					instance.updater = updater;
					instance.beginUpdateTime = System.currentTimeMillis();
					return true;
				} else
					return false;
			} else
				return false;
		} else {
			instance.updater = updater;
			instance.beginUpdateTime = System.currentTimeMillis();
			return true;
		}
	}

	@Deprecated
	public static synchronized void endUpdate(String updater) {
		if (instance.updater != null && instance.updater.length() > 0)
			if (!instance.updater.contentEquals(updater)) {
				System.out.println("有线程跨过beginUpdateVersion直接执行endUpdate，该线程即将被中断。");
				String tname = GraphManager.interruptThread(updater);
				return;
			}
		instance.updater = "";
		instance.beginUpdateTime = 0;
		instance.updating = false;
	}

	@Deprecated
	public static ArrayList<FileList> getCurrentUpdate() {
		if (instance == null)
			return null;
		return instance.changed;
	}

	@Deprecated
	public static int getCurrentVersion() {
		if (instance == null)
			return 0;
		else
			return instance.version;
	}

}
