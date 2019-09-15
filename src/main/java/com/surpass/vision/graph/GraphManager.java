package com.surpass.vision.graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.domain.FileList;
import com.surpass.vision.domain.Graph;
import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.mapper.PointGroupDataMapper;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.tools.FileTool;
import com.surpass.vision.tools.IDTools;

@Component 
public class GraphManager extends GraphDataManager {

	@Reference
	@Autowired
	RedisService redisService;
	
	@Autowired
	PointGroupDataMapper pointGroupDataMapper;

	@Value("${upc.graphPath}")
	private String graphPath;
	
	@Value("${upc.graphUpdateTimeOut}")
	private int timeOut;
	
	private Integer version = 0;
	private String updater ="";
	private long beginUpdateTime = 0;
	private boolean updating = false;
	private FileList repo;
	
//	Hashtable<String, ArrayList<Graph>> children;
	
	private ArrayList<FileList> changed;
	private GraphManager() {
		super();
		if(instance == null) instance = this;
		if(repo == null) repo = new FileList(); 
	}
	private static GraphManager instance;
//	{
//		if(instance == null) instance = new GraphManager();
//		if(repo == null) repo = new FileList();
//	}
	
	public static synchronized GraphManager getInstance() {
		if(instance == null) { 
			instance = new GraphManager();
			if(instance.repo == null) instance.repo = new FileList();
			instance.changed = new ArrayList<FileList>();
		}

		return instance;
	}
	
	private static String interruptThread(String name) {
		ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
		int noThreads = currentGroup.activeCount();
		Thread[] lstThreads = new Thread[noThreads];
		currentGroup.enumerate(lstThreads);
		for (int i = 0; i < noThreads; i++) {
			if(lstThreads[i].getName().contentEquals(instance.updater)) {
				lstThreads[i].interrupt();
				System.out.println("线程号：" + i + " = " + lstThreads[i].getName()+
						",，已经被中断。");
				return name;
			}
		}
		return null;
	}
	
	public static synchronized boolean beginUpdate(String updater) {
		if(instance.updater != null && instance.updater.length()>0) {
			if(instance.updater.contentEquals(updater)) return true;
			long curr = System.currentTimeMillis();
			if((curr - instance.beginUpdateTime)/1000 > instance.timeOut) {
				String tname = GraphManager.interruptThread(updater);
				if(tname!=null) {
					System.out.println("线程：" + tname + 
							",因为执行超时，已经被中断。当前超时限制设置为："+instance.timeOut+
							"秒，中断线程执行了"+(curr - instance.beginUpdateTime)/1000+"秒。");
					instance.updater = updater;
					instance.beginUpdateTime = System.currentTimeMillis();
					return true;					
				} else return false;
			}else
				return false;
		} else {
			instance.updater = updater;
			instance.beginUpdateTime = System.currentTimeMillis();
			return true;
		}
	}
	
	public static synchronized void updateVersion(String updater) {
		if(instance.updater != null && instance.updater.length()>0)
			if(!instance.updater.contentEquals(updater)) {
				System.out.println("有线程跨过beginUpdateVersion直接执行updateVersion，该线程即将被中断。");
				String tname = GraphManager.interruptThread(updater);
				return;
			}
		instance.version ++;
		instance.changed = new ArrayList<FileList>();
		instance.updating = true;
	}
	
	public static synchronized void endUpdate(String updater) {
		if(instance.updater != null && instance.updater.length()>0)
			if(!instance.updater.contentEquals(updater)) {
				System.out.println("有线程跨过beginUpdateVersion直接执行endUpdate，该线程即将被中断。");
				String tname = GraphManager.interruptThread(updater);
				return;
			}
		instance.updater = "";
		instance.beginUpdateTime = 0;		
		instance.updating = false;
	}
	                  
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] aa = new String("c:\\dev").split("\\\\");
System.out.println(aa.length);
	}


	public void addChildren(String pathName, Hashtable<String, FileList> children) {
		// 如果当前库没有名字，说明是第一次更新，就初始化加入
		if(GraphManager.getInstance().repo.getName() == null) {
			GraphManager.getInstance().repo.setName(pathName);
			GraphManager.getInstance().repo.setFile(false);
			GraphManager.getInstance().repo.addChildren(children);
			System.out.println("pathName:= "+pathName+"  graphPath="+graphPath);
		} else {
			// 从孩子中找指定名字的对象
			FileList fl =repo.getChild(pathName);
			if(fl!=null && !fl.isFile()) 
				// 找到了这个孩子
				fl.addChildren(children);
		}
		
	}

	public static boolean isUpdating() {
		if(instance == null) return false;
		return instance.updating;
	}

	public static void addChange(FileList child) {
		instance.changed.add(child);
	}

	public static int getCurrentVersion() {
		if(instance==null) return 0;
		else
		return instance.version;
	}

	public static FileList getCurrentFileList() {
		return GraphManager.getInstance().repo;
//		if(instance == null) return null;
//		return new FileList(GraphManager.getInstance().repo);
	}

	public static ArrayList<FileList> getCurrentUpdate() {
		if(instance == null) return null;
		return instance.changed;
//		return new ArrayList<FileList>(instance.changed);
	}

	public FileList getArllGraph() {
		
		return this.repo;
	}

	public void reloadFileList(String graphPath2) {
		try {
			FileTool.find(graphPath2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Graph updateShareRight(Double id, List<String> userIds) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateGraph(Graph rtd) {
		// 更新数据库
		pointGroupService.updatePointGroupItem(rtd);
		// 写缓存RealTimeData，返回
		redisService.set(GlobalConsts.Key_Graph_pre_+IDTools.toString(rtd.getId()),rtd);
	}

//	public void updateGraphs() {
//		// TODO Auto-generated method stub
//		updateGraphs(repo);
//	}
//	
//	public void updateGraphs(FileList fl) {
//		// 如果是FileList一个文件，
//		if(fl.isFile()) {
//			Graph gt = null;
//			Double itemId = fl.getId();
//			if(itemId==null || itemId==Double.valueOf(0)) {
//				itemId = IDTools.newID();
//			} 
//			
//			// 如果数据库中有这条记录，就写到缓存里
//			PointGroupData pgd = this.pointGroupService.getPointGroupDataByName(fl.getName());
//			try {
//			if(pgd!=null) {
//				// TODO: 如果与数据库时的不一样，就更新数据库
//				fl.setOwner(pgd.getOwner());
//				fl.setCreater(pgd.getCreater());
//				fl.setShared(pgd.getShared());
//				fl.setOtherrule1(pgd.getOtherrule1());
//				fl.setOtherrule2(pgd.getOtherrule2());
//				fl.setDesc(pgd.getOtherrule1());
//
//				pointGroupDataMapper.updateByName(fl.getOwner(),fl.getCreater(),fl.getShared(),fl.getPoints(),fl.getOtherrule1(),fl.getOtherrule2(),fl.getName());
//			} else {
//				// 如果没有，就先写入数据库
//				pointGroupDataMapper.insert(fl);
//			}
//			gt = this.copyFromPointGroupData(fl);
//			// 再写入缓存
//			this.redisService.set(GlobalConsts.Key_Graph_pre_+ IDTools.toString(gt.getId()), gt);
//			}catch(Exception e) {
//				System.out.println();
//				e.printStackTrace();
//			}
//		} else {
//			// 如果是目录，就循环他的子
//			Hashtable<String,FileList> chs = repo.getChildren();
//			if(chs==null||chs.size()<1)
//				return;
//			Enumeration<FileList> es = chs.elements();
//			while(es.hasMoreElements()) {
//				FileList flt = es.nextElement();
//				updateGraphs(flt);
//			}
//		}
//	}


	public Graph getGraphByKeys(Double oldRtdId) {
		Graph rtd = (Graph)redisService.get(GlobalConsts.Key_Graph_pre_+IDTools.toString(oldRtdId));
		// TODO: 如果没有，就从GraphManager
		if(rtd == null) {
//			this.children.h
		}
		return rtd;

	}




}
