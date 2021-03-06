package com.surpass.vision.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import com.surpass.vision.domain.Graph;
import com.surpass.vision.graph.GraphDataManager;
import com.surpass.vision.graph.GraphManager;
import com.surpass.vision.pointGroup.PointGroupDataManager;
import com.surpass.vision.schedule.UpdateGraphDirctory;
import com.surpass.vision.server.Point;
import com.surpass.vision.server.ServerManager;
import com.surpass.vision.service.RedisService;

public class FileTool {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileTool.class);

//	private static final 
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
//		LOGGER.info("开始刷新目录：" + pathName);
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
//				fl.setName(path);
				fl.setFile(false);
				fl.setSVG(false);
				fl.setType(GlobalConsts.Type_graph_);
				fl.setId(IDTools.newID());
				fl.setImg(ServerConfig.getInstance().getDefaultGraphDirImg());

				// 目录是图，不添加到数据库、缓存。取数据时，根据图的目录，分解出结构关系。
				/**
				 * 需求修改：目录也要添加数据库，因为要做目录分享。
				 * 
				 * @since 2019.12.27
				 */
				// 同步数据库和缓存
				fl = graphDataManager.copyGraphFromFileList(fl);
				// 添加到根树里
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
					boolean fileChanged = false;

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
					if (fl.getName().contentEquals("Graphic147.svg"))
						System.out.println();
					// 在图上显示数据的DOM的ID。
					ArrayList<String> pointTextIDs = new ArrayList<String>();
					/***********************************************************************************
					 * 淮南图的数据格式
					 **********************************************************************************/
					String otherRule3 = "";
					JSONArray jsa = new JSONArray();
					// 先判断是否是g文件
					Elements docsg = doc.getElementsByTag(GlobalConsts.GPointTag);
					for (int idocs = 0; idocs < docsg.size(); idocs++) {
						Element eg = docsg.get(idocs);
						String gPointId = eg.attr(GlobalConsts.GPointID);// PBD:PtTagName
//						LOGGER.info("检查点位：gPointId="+gPointId);
						if (!StringUtil.isBlank(gPointId)) {
							String serverName = PointGroupDataManager.splitServerName1(gPointId);
							String tagName = PointGroupDataManager.splitPointName1(gPointId);
//							if (tagName.contentEquals("81_3701_01_P02_C_out"))System.out.println();}
							Point p = sm.getPointByID(serverName, tagName);
							if (p != null) {
//								 LOGGER.info("检查点位："+tagName);
								// PB:IsMultiState="True" PBD:PtTagName="\\RTDBB\LT_85_7301_10_L01B" PB:Type="7"
								// PB:NumberFormat="0.00">
								String isMultiState = eg.attr(GlobalConsts.GIsMultiState);// PB:IsMultiState
								String _type = eg.attr(GlobalConsts.GType);// PB:Type

								// 取text点
								String numberFormat = eg.attr(GlobalConsts.GNumberFormat);// PB:NumberFormat
								Elements docsText = eg.getElementsByTag(GlobalConsts.PointTag);
								for (int indDocsText = 0; indDocsText < docsText.size(); indDocsText++) {
									Element etext = docsText.get(indDocsText);
									// 取MultiState PB:IsMultiState="True"
									Elements pbMultiStates = etext.getElementsByTag(GlobalConsts.PBMultiStateTag);
									if (pbMultiStates.size() > 0) {
										JSONObject jo = new JSONObject();
										for (int indPBMultiState = 0; indPBMultiState < pbMultiStates
												.size(); indPBMultiState++) {
											Element pbMultiMSState = pbMultiStates.get(indPBMultiState);
											// 取MSState
											Elements pbMSStateTags = pbMultiMSState
													.getElementsByTag(GlobalConsts.PBMSStateTag);
											for (int indPBMSState = 0; indPBMSState < pbMSStateTags
													.size(); indPBMSState++) {
												Element pbMSState = pbMSStateTags.get(indPBMSState);
												String id = pbMSState.attr("id");
												String Blink = pbMSState.attr("Blink");
												String Color = pbMSState.attr("Color");
												String LowerValue = pbMSState.attr("LowerValue");
												String UpperValue = pbMSState.attr("UpperValue");
												jo.put("HihiLimit", p.getHihiLimit());
												jo.put("HiLimit", p.getHiLimit());
												jo.put("LoLimit", p.getLoLimit());
												jo.put("LoloLimit", p.getLoloLimit());
												jo.put("id", id);
												jo.put("Blink", Blink);
												jo.put("Color", Color);
												jo.put("LowerValue", LowerValue);
												jo.put("UpperValue", UpperValue);
											}
//											LOGGER.info(" 检查点规则： \t"+jo.toJSONString());
										}

									} else {

									}
									// 取出点的text的ID
									String textId = etext.attr("id");
									if (StringUtil.isBlank(textId)) {
										try {
											textId = "DATAPOINT" + IDTools.getUniqueID();
											etext.attr("id", textId);
											fileChanged = true;
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									pointIDs.add(p.wholeName());
									pointTextIDs.add(textId);

								}
								// 检查Bar图形
								// PB:IsMultiState="False" PB:Lower="0" PB:Orientation="0"
								// PB:CanonicalNumberFormat="General" PBD:PtTagName="\\RTDBB\LT_85_7301_10_L01A"
								// PB:Start="0" PB:Upper="100" PB:Type="12">
								Elements docsRect = eg.getElementsByTag(GlobalConsts.RectTag);
								for (int indDocsRect = 0; indDocsRect < docsRect.size(); indDocsRect++) {
									Element etext = docsRect.get(indDocsRect);
////									   <rect x="16900" y="15340" width="780" height="2920" stroke-width="0" fill="#FFFFFF" 
//									id="BARGRAPH1_pbBarBoundingRectEl" />
////									   <rect x="16900" y="15340" width="780" height="2920" stroke-width="0" fill="#00FF00" 
//									id="BARGRAPH1_pbBarTagRectEl" stroke="none" PBD:Property="VAL" />
									String _property = etext.attr(GlobalConsts.PBDProperty);
									if (!StringUtil.isBlank(_property)) {
										String textId = etext.attr("id");
										if (StringUtil.isBlank(textId)) {
											try {
												textId = "BARGRAPH" + IDTools.getUniqueID();
												etext.attr("id", textId);
												fileChanged = true;
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
										pointIDs.add(p.wholeName());
										pointTextIDs.add(textId);
									}
								}
							} else {
//								 LOGGER.info("检查不存在的点位："+tagName);
								LOGGER.error("数据不一致错误，图上的点在实时数据库中不存在。\t 图：\t " + fl.getWholePath() + "\t 点：\t 服务器：\t"
										+ serverName + "\t tagName:\t" + tagName);
							}
						}
					}

					/***********************************************************************************
					 * 普通图的数据格式
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
					if (pointTextIDs.size() > 0)
						fl.setPointTextIDs(pointTextIDs);

					/***********************************************************************************
					 * 生成缩略图
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
						String urlGraphPath = ServerConfig.getInstance()
								.getURLFromPath(physicalGraphPath + "\\" + fl.getName() + ".png");
						File fimage = new File(urlGraphPath);
						if (fimage.length() > 0)
							fl.setImg(urlGraphPath);
						else {

							fl.setImg(ServerConfig.getInstance().getDefaultGraphImg());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (fileChanged) { // 如果前面修改了文件，这里要保存文件
						saveFile(doc.html(), fl.getWholePath());
					}
//					fl = FileTool.getInstnace().loadFileListDatabaseInfo(fl);
					if(StringUtil.isBlank(fl.getPoints())) {
						LOGGER.error("图形：\t"+fl.getWholePath()+"\t没有数据点。");
					}
					// 同步数据库和缓存
					fl = graphDataManager.copyGraphFromFileList(fl);
					children1.add(fl);
				} else { // 不是svg文件
					fl.setSVG(false);
				}
			}
		}
		// 将children1添加到parent的索引中
		parent.addChildren(children1);
		for (int i = 0; i < children1.size(); i++) {
			FileList _fl = children1.get(i);
			if (!_fl.isFile()) {
				String fkey = _fl.getPath();// pathName ;//+ File.separator + fl.getName();
				find(fkey, depth + 1, _fl);
			}
		}
	}

	public static void saveFile(String html, String wholePath) {
		File writename = new File(wholePath);
		try {
			if (!writename.exists()) {
				writename.createNewFile();
			}
			if (writename.canWrite()) {
				BufferedWriter out = new BufferedWriter(new FileWriter(writename));
				out.write(html); // \r\n即为换行
				out.flush(); // 把缓存区内容压入文件
				out.close(); // 最后记得关闭文件
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 创建新文件
	}

	public static Graph parseFileToGraph(String path, String fileName, String picurl, String name2, String desc,
			String uid) {
		// 遍历文件目录
		String string = fileName;
		File file = new File(path, string);
		String name = file.getName();
		try {
			path = file.getCanonicalPath();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		FileList fl = new FileList();
		fl.setOtherrule1(path); // 把完整路径保存在otherrule1中.
		fl.setName(name);
		if (StringUtil.isBlank(picurl)) {
			picurl = ServerConfig.getInstance().getDefaultGraphImg();
		}
		if (file.isDirectory()) {
			return null;
		} else {
			path = path.substring(0, path.length() - name.length() - 1);
			fl.setPath(path);
			fl.setImg(picurl);
			fl.setDesc(desc);
			fl.setOwner(uid);
			fl.setCreater(uid);
			fl.setFile(true);
			fl.setNickName(name2);

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
				if (fl.getName().contentEquals("Graphic147.svg"))
					System.out.println();
				// 在图上显示数据的DOM的ID。
				ArrayList<String> pointTextIDs = new ArrayList<String>();
				/***********************************************************************************
				 * 淮南图的数据格式
				 **********************************************************************************/
				String otherRule3 = "";
				JSONArray jsa = new JSONArray();
				// 先判断是否是g文件
				Elements docsg = doc.getElementsByTag(GlobalConsts.GPointTag);
				for (int idocs = 0; idocs < docsg.size(); idocs++) {
					Element eg = docsg.get(idocs);
					String gPointId = eg.attr(GlobalConsts.GPointID);// PBD:PtTagName
//					LOGGER.info("检查点位：gPointId="+gPointId);
					if (!StringUtil.isBlank(gPointId)) {
						String serverName = PointGroupDataManager.splitServerName1(gPointId);
						String tagName = PointGroupDataManager.splitPointName1(gPointId);
//						if (tagName.contentEquals("81_3701_01_P02_C_out"))System.out.println();}
						Point p = sm.getPointByID(serverName, tagName);
						if (p != null) {
//							 LOGGER.info("检查点位："+tagName);
							// PB:IsMultiState="True" PBD:PtTagName="\\RTDBB\LT_85_7301_10_L01B" PB:Type="7"
							// PB:NumberFormat="0.00">
							String isMultiState = eg.attr(GlobalConsts.GIsMultiState);// PB:IsMultiState
							String _type = eg.attr(GlobalConsts.GType);// PB:Type

							// 取text点
							String numberFormat = eg.attr(GlobalConsts.GNumberFormat);// PB:NumberFormat
							Elements docsText = eg.getElementsByTag(GlobalConsts.PointTag);
							for (int indDocsText = 0; indDocsText < docsText.size(); indDocsText++) {
								Element etext = docsText.get(indDocsText);
								// 取MultiState PB:IsMultiState="True"
								Elements pbMultiStates = etext.getElementsByTag(GlobalConsts.PBMultiStateTag);
								if (pbMultiStates.size() > 0) {
									JSONObject jo = new JSONObject();
									for (int indPBMultiState = 0; indPBMultiState < pbMultiStates
											.size(); indPBMultiState++) {
										Element pbMultiMSState = pbMultiStates.get(indPBMultiState);
										// 取MSState
										Elements pbMSStateTags = pbMultiMSState
												.getElementsByTag(GlobalConsts.PBMSStateTag);
										for (int indPBMSState = 0; indPBMSState < pbMSStateTags
												.size(); indPBMSState++) {
											Element pbMSState = pbMSStateTags.get(indPBMSState);
											String id = pbMSState.attr("id");
											String Blink = pbMSState.attr("Blink");
											String Color = pbMSState.attr("Color");
											String LowerValue = pbMSState.attr("LowerValue");
											String UpperValue = pbMSState.attr("UpperValue");
											jo.put("HihiLimit", p.getHihiLimit());
											jo.put("HiLimit", p.getHiLimit());
											jo.put("LoLimit", p.getLoLimit());
											jo.put("LoloLimit", p.getLoloLimit());
											jo.put("id", id);
											jo.put("Blink", Blink);
											jo.put("Color", Color);
											jo.put("LowerValue", LowerValue);
											jo.put("UpperValue", UpperValue);
										}
//										LOGGER.info(" 检查点规则： \t"+jo.toJSONString());
									}

									// 取出点的text的ID
									String textId = etext.attr("id");
									pointIDs.add(p.wholeName());
									pointTextIDs.add(textId);
								}

							}
							// 检查Bar图形
							// PB:IsMultiState="False" PB:Lower="0" PB:Orientation="0"
							// PB:CanonicalNumberFormat="General" PBD:PtTagName="\\RTDBB\LT_85_7301_10_L01A"
							// PB:Start="0" PB:Upper="100" PB:Type="12">
							Elements docsRect = eg.getElementsByTag(GlobalConsts.RectTag);
							for (int indDocsRect = 0; indDocsRect < docsRect.size(); indDocsRect++) {
								Element etext = docsRect.get(indDocsRect);
////								   <rect x="16900" y="15340" width="780" height="2920" stroke-width="0" fill="#FFFFFF" 
//								id="BARGRAPH1_pbBarBoundingRectEl" />
////								   <rect x="16900" y="15340" width="780" height="2920" stroke-width="0" fill="#00FF00" 
//								id="BARGRAPH1_pbBarTagRectEl" stroke="none" PBD:Property="VAL" />
								String _property = etext.attr(GlobalConsts.PBDProperty);
								if (!StringUtil.isBlank(_property)) {
									String textId = etext.attr("id");
									pointIDs.add(p.wholeName());
									pointTextIDs.add(textId);
								}
							}
						} else {
//							 LOGGER.info("检查不存在的点位："+tagName);
							LOGGER.error("数据不一致错误，图上的点在实时数据库中不存在。\t 图：\t " + fl.getWholePath() + "\t 点：\t 服务器：\t"
									+ serverName + "\t tagName:\t" + tagName);
						}
					}
				}

				/***********************************************************************************
				 * 普通图的数据格式
				 **********************************************************************************/
				Elements docs = doc.getElementsByTag(GlobalConsts.PointTag);
				for (int idocs = 0; idocs < docs.size(); idocs++) {
					Element e = docs.get(idocs);
					String tag = e.attr(GlobalConsts.PointID);
					if (!StringUtil.isBlank(tag)) {
//						System.out.println("图形："+fl.getName()+"，tag="+tag);
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
				if (pointTextIDs.size() > 0)
					fl.setPointTextIDs(pointTextIDs);

				/***********************************************************************************
				 * 生成缩略图
				 **********************************************************************************/
//				// 删除所有text
//				Elements es = doc.getElementsByTag("text");
//				if (es != null && es.size() > 0) {
//					for (int indscript = es.size() - 1; indscript >= 0; indscript--) {
//						es.get(indscript).remove();
//					}
//				}

//				try {
//					// 生成图片
////					Resource resource = new ClassPathResource("");
////					String _path_ = resource.getFile().getAbsolutePath();
//					// 将SVG转成图形，写到缩略图物理目录里
//					String physicalGraphPath = ServerConfig.getInstance().getPhysicalGraphPath(fl.getPath());
//					Elements es = doc.getElementsByTag(GlobalConsts.GraphElement);
////					SVGTools.convertToPng(es.get(0).outerHtml(), physicalGraphPath + "\\" + fl.getName() + ".png");
//					String urlGraphPath = ServerConfig.getInstance().getURLFromPath(physicalGraphPath + "\\" + fl.getName() + ".png");
//					File fimage = new File(urlGraphPath);
//					if(fimage.length()>0)
//						fl.setImg(urlGraphPath);
//					else
//						fl.setImg(ServerConfig.getInstance().getDefaultGraphImg());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				fl = FileTool.getInstnace().loadFileListDatabaseInfo(fl);
				// 将这个文件添加到fileList库中及索引中。
				// 同步数据库和缓存
				Graph g = graphDataManager.copyGraphFromFileList(fl);
				// ((FileList)g).setId(g.getId());
				repo.addChild(g);
				System.out.println("11");
				return g;
			} else { // 不是svg文件
				fl.setSVG(false);
			}
		}
		return null;
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

	public static boolean delete(String wholePath) {
		File dest = new File(wholePath);
		if (dest.exists()) {
			return dest.delete();
		}
		return false;
	}
}
