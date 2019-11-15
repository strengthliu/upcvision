package com.surpass.vision.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.batik.transcoder.TranscoderException;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.appCfg.ServerConfig;
import com.surpass.vision.domain.FileList;
import com.surpass.vision.graph.GraphDataManager;
import com.surpass.vision.graph.GraphManager;
import com.surpass.vision.pointGroup.PointGroupDataManager;
import com.surpass.vision.schedule.UpdateGraphDirctory;
import com.surpass.vision.server.Point;
import com.surpass.vision.server.ServerManager;
import com.surpass.vision.service.RedisService;

public class FileTool {
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateGraphDirctory.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		File f = new File(".");
//		System.out.println(""+f.getAbsolutePath());
//		try {
//			System.out.println(new File(".").getCanonicalPath());
//			
//			Resource resource = new ClassPathResource("");
//			System.out.println(resource.getFile().getAbsolutePath());
//		       String path = ResourceUtils.getURL("classpath:").getPath();
//		        //=> file:/root/tmp/demo-springboot-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes!/
//		       System.out.println("ResourceUtils.getURL(\"classpath:\").getPath() -> "+path);
////			File upload = new File(path.getAbsolutePath(),"static/images/upload/");
////			if(!upload.exists()) upload.mkdirs();
////			System.out.println("upload url:"+upload.getAbsolutePath());
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		String path = "c:\\a\\b\\c";
		String[] folders = path.split("\\\\");
		for (int i = 0; i < folders.length; i++)
			System.out.println(folders[i]);

	}

	static ServerManager sm = ServerManager.getInstance();

	private static FileTool instance;

	public static FileTool getInstnace() {
		if (instance == null)
			instance = new FileTool();
		return instance;
	}

	RedisService rs;

	public RedisService getRs() {
		return rs;
	}

	public void setRs(RedisService rs) {
		this.rs = rs;
	}

	public void setSm(ServerManager sm) {
		this.sm = sm;
	}

	// 从1开始
	private static final int _depth = 1;

	private static ServerManager serverManager;
	private static GraphDataManager graphDataManager;
	private static String imgPath;
	private static FileList repo;

//	upc.graphServerPath=/images/graphImage
	public static void find(String pathName, ServerManager _serverManager, GraphDataManager _graphDataManager,
			String _imgPath, FileList _repo) throws IOException {
		serverManager = _serverManager;
		graphDataManager = _graphDataManager;
		imgPath = _imgPath;
		repo = _repo;
		if (StringUtil.isBlank(repo.getName())) {
			File dirFile = new File(pathName);
			// 这句必须加上，解决不同操作系统文件名大小写区分问题。
			pathName = dirFile.getCanonicalPath();
			// 判断该文件或目录是否存在，不存在时在控制台输出提醒
			repo.setPath(pathName);
			repo.setName(pathName);
			repo.setId(IDTools.newID());
		}
		find(pathName, _depth, repo);
	}

//	public static void find(String pathName) throws IOException {
//		find(pathName, _depth);
//	}

	public static String readHtml(String fileName) {
		// LOGGER.info("检测文件："+fileName);
		File filename = new File(fileName); // 要读取以上路径的input。txt文件
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		boolean isCompatible = false;
		if (suffix.contentEquals("txt") || suffix.contentEquals("html") || suffix.contentEquals("htm")
				|| suffix.contentEquals("svg") || suffix.contentEquals("text"))
			isCompatible = true;
		// 大于1m或不是指定文件格式，就退出。
		if ((filename.length() / 1024 / 1024) > 2 || !isCompatible)
			return "";
		InputStreamReader reader = null;
		String ret = "";
		BufferedReader br = null;
		try {
			reader = new InputStreamReader(new FileInputStream(filename));
			br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			String line = "";
			line = br.readLine();
			while (line != null) {
				ret = ret + line;
				line = br.readLine(); // 一次读入一行数据
			}
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} // 建立一个输入流对象reader
		catch (IOException e3) {
			e3.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	private static void find(String pathName, int depth, FileList parent) throws IOException {

		// 获取pathName的File对象
		File dirFile = new File(pathName);
		// 这句必须加上，解决不同操作系统文件名大小写区分问题。
		pathName = dirFile.getCanonicalPath();
		// 判断该文件或目录是否存在，不存在时在控制台输出提醒
		if (!dirFile.exists()) {
			return;
		}
		// 判断如果不是一个目录，就判断是不是一个文件，时文件则输出文件路径
		if (!dirFile.isDirectory()) {
			return;
		}
		LOGGER.info("开始刷新目录：" + pathName);
		// 获取此目录下的所有文件名与目录名
		String[] fileList = dirFile.list();
//		Hashtable<String, FileList> children = new Hashtable<String, FileList>();
		ArrayList<FileList> children1 = new ArrayList<FileList>();
//		ArrayList<FileList> dirs = new ArrayList<FileList>();
		for (int i = 0; i < fileList.length; i++) {
			// 遍历文件目录
			String string = fileList[i];
			File file = new File(dirFile.getPath(), string);
			String name = file.getName();
			String path = file.getCanonicalPath();
			FileList fl = new FileList();
			fl.setOtherrule1(path); // 把完整路径保存在otherrule1中.
			fl.setName(name);
			if (file.isDirectory()) {
				fl.setPath(path);
				fl.setName(path);
				fl.setFile(false);
				fl.setSVG(false);
				fl.setId(IDTools.newID());
				// 目录是图，不添加到数据库、缓存。取数据时，根据图的目录，分解出结构关系。
				children1.add(fl);
			} else {
				path = path.substring(0, path.length() - name.length() - 1);
				fl.setPath(path);
				fl.setFile(true);
				// 解析文件
				Document doc = Jsoup.parse(HtmlParser.readHtml(fl.getWholePath()));
				// 判断文件是否是图形文件
				ArrayList<String> pointIDs = new ArrayList<String>();
				if (doc.select(GlobalConsts.GraphElement) != null && doc.select(GlobalConsts.GraphElement).size() > 0) {
					fl.setSVG(true);
					fl.setType(GlobalConsts.Type_graph_);

					/**
					 * // // 如果是图形文件，先删除所有js Document doc1 =
					 * Jsoup.parse(HtmlParser.readHtml(fl.getWholePath())); Elements scriptdocs =
					 * HtmlParser.getScriptElement(doc1); if(scriptdocs!=null &&
					 * scriptdocs.size()>0) { for(int
					 * indscript=scriptdocs.size()-1;indscript>=0;indscript--) {
					 * scriptdocs.get(indscript).remove(); } String fileContent = doc.html();
					 * fileContent.replace(" null", ""); file.delete(); file.createNewFile();
					 * FileWriter fw = new FileWriter(file,true); fw.write(fileContent); fw.close();
					 * }
					 */
					if(fl.getName().contentEquals("Graphic147.svg"))
						System.out.println();
					// 在图上显示数据的DOM的ID。
					ArrayList<String> pointTextIDs = new ArrayList<String>();
					/***********************************************************************************
					 *      淮南图的数据格式
					 **********************************************************************************/
					String otherRule3 = "";
					JSONArray jsa = new JSONArray();
					// 先判断是否是g文件
					Elements docsg = doc.getElementsByTag(GlobalConsts.GPointTag);
					for (int idocs = 0; idocs < docsg.size(); idocs++) {
						Element eg = docsg.get(idocs);
						String gPointId = eg.attr(GlobalConsts.GPointID);//PBD:PtTagName
						if (!StringUtil.isBlank(gPointId)) {
		//					System.out.println("图形："+fl.getName()+"，tag="+tag);
							// 拆分 "\\RTDBB\81_3701_01_P02_C_out"，成服务器 点位名
							String serverName = PointGroupDataManager.splitServerName1(gPointId);
							String tagName = PointGroupDataManager.splitPointName1(gPointId);
							if(tagName.contentEquals("81_3701_01_P02_C_out")) {
								System.out.println();
							}
							Point p = sm.getPointByID(serverName, tagName);
							if (p != null) {
								// LOGGER.info("检查点位："+tag+" => "+sm.getPointByID(tag));

								// 取text点
								Elements docsText = eg.getElementsByTag(GlobalConsts.PointTag);
								for(int indDocsText=0;indDocsText<docsText.size();indDocsText++) {
									Element etext = docsText.get(indDocsText);

									// TODO: 取出规则
									/*
									 *    <text fill="#000000" font-family="Helvetica" font-size="560" font-weight="bold" text-anchor="middle" x="19930" y="4680" id="DATAPOINT30_pbTextEl" PBD:Property="VAL">
									 *    	#.##
									 *    	<PB:MultiState id="DATAPOINT30_MS" PBD:PtTagName="\\RTDBB\81_3701_01_P02_C_out" TagName="81_3701_01_P02_C_out" ServerName="RTDBB" StateCount="2">
									 *    		<PB:MSState id="DATAPOINT30_MSS1" Blink="0" Color="007800" LowerValue="" UpperValue="" />
									 *    		<PB:MSState id="DATAPOINT30_MSS2" Blink="0" Color="000000" LowerValue="" UpperValue="" />
									 *    	</PB:MultiState>
									 *    </text>
									 */
									// 取MultiState
									Elements pbMultiStates = etext.getElementsByTag(GlobalConsts.PBMultiStateTag);
									for(int indPBMultiState=0;indPBMultiState<pbMultiStates.size();indPBMultiState++){
										//eg.getElementsByTag(GlobalConsts.PointTag)
//										id="DATAPOINT32_MS" ;
//										PBD:PtTagName="\\RTDBB\1060_FI_1002";
//										TagName="1060_FI_1002"; 
//										ServerName="RTDBB"; 
//										StateCount="2";
										Element pbMultiMSState = pbMultiStates.get(indPBMultiState);
										// 取MSState
										Elements pbMSStateTags = pbMultiMSState.getElementsByTag(GlobalConsts.PBMSStateTag);
										for(int indPBMSState=0;indPBMSState<pbMSStateTags.size();indPBMSState++) {
											Element pbMSState = docsText.get(indPBMultiState);
//											id="DATAPOINT32_MSS2";
//											Blink="0";
//											Color="000000";
//											LowerValue="";
//											UpperValue="";
										}
									}

									// 取出点的text的ID
									String textId = etext.attr("id");
									pointIDs.add(p.wholeName());
									pointTextIDs.add(textId);
								}
							}
						}
					}
//					if(pointTextIDs.size()>0)
//						fl.setPointTextIDs(pointTextIDs);
					
					/***********************************************************************************
					 *      普通图的数据格式
					 **********************************************************************************/
					Elements docs = doc.getElementsByTag(GlobalConsts.PointTag);
					for (int idocs = 0; idocs < docs.size(); idocs++) {
						Element e = docs.get(idocs);
						String tag = e.attr(GlobalConsts.PointID);
						if (!StringUtil.isBlank(tag)) {
//							System.out.println("图形："+fl.getName()+"，tag="+tag);
							String serverName = PointGroupDataManager.splitServerName(tag);
							String tagName = PointGroupDataManager.splitPointName(tag);
							Point p = sm.getPointByID(serverName, tagName);
							if (p != null) {
								pointIDs.add(p.wholeName());
								pointTextIDs.add(tag);
								// LOGGER.info("检查点位："+tag+" => "+sm.getPointByID(tag));
							}
						} 
					}

					fl.setPoints(IDTools.merge(pointIDs.toArray()));
					if(pointTextIDs.size()>0)
						fl.setPointTextIDs(pointTextIDs);

					
					/***********************************************************************************
					 *      生成缩略图
					 **********************************************************************************/
//					// 删除所有text
//					Elements es = doc.getElementsByTag("text");
//					if (es != null && es.size() > 0) {
//						for (int indscript = es.size() - 1; indscript >= 0; indscript--) {
//							es.get(indscript).remove();
//						}
//					}
					
					try {
						// 生成图片
//						Resource resource = new ClassPathResource("");
//						String _path_ = resource.getFile().getAbsolutePath();
						// 将SVG转成图形，写到缩略图物理目录里
						String physicalGraphPath = ServerConfig.getInstance().getPhysicalGraphPath(fl.getPath());
						Elements es = doc.getElementsByTag(GlobalConsts.GraphElement);
//						SVGTools.convertToPng(es.get(0).outerHtml(), physicalGraphPath + "\\" + fl.getName() + ".png");
						String urlGraphPath = ServerConfig.getInstance().getURLFromPath(physicalGraphPath + "\\" + fl.getName() + ".png");
						File fimage = new File(urlGraphPath);
						if(fimage.length()>0)
							fl.setImg(urlGraphPath);
						else
							fl.setImg(ServerConfig.getInstance().getDefaultGraphImg());
					} catch (Exception e) {
						e.printStackTrace();
					}
//					fl = FileTool.getInstnace().loadFileListDatabaseInfo(fl);
					// 同步数据库和缓存
					fl = graphDataManager.copyGraphFromFileList(fl, null);
					children1.add(fl);
				} else { // 不是svg文件
					fl.setSVG(false);
				}
			}
		}
		parent.addChildren(children1);
		for (int i = 0; i < children1.size(); i++) {
			FileList _fl = children1.get(i);
			if (!_fl.isFile()) {
				String fkey = _fl.getPath();// pathName ;//+ File.separator + fl.getName();
				find(fkey, depth + 1, _fl);
			}
		}
	}

//	/**
//	 * 指导路径分成字符串数组。
//	 * @param path
//	 * @return
//	 */
//	public static String [] spliceFolder(String path) {
//		path.split(regex)
//		
//	}
	private FileList loadFileListDatabaseInfo(FileList fl) {
		FileList _fl = graphDataManager.getDatabaseInfoByPath(fl);
		return _fl;
	}
}
