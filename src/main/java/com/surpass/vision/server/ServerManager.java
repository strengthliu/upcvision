package com.surpass.vision.server;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

import com.sun.jna.NativeLong;
import com.surpass.realkits.JGecService;
import com.surpass.realkits.exception.GecException;
import com.surpass.vision.domain.PointAlertData;
import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.tools.EncodingTools;
import com.surpass.vision.tools.FileTool;
import com.surpass.vision.tools.IDTools;
import com.surpass.vision.tools.TimeTools;

@Component
public class ServerManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerManager.class);

	public static Server defaultServer;
	static Hashtable<String, Server> servers = new Hashtable<String, Server>();

	public static Hashtable<String, Server> getServers() {
		return servers;
	}

	@Value("${gc.library}")
//	private String gcLibrary;
	private String gcLibrary = "./gec().dll";


	@Reference
	@Autowired
	RedisService redisService;

	public String name = "s1";

	/**
	 * 在调用读取点位名称时，有时会出虚拟机崩溃的错误，目前分析可能的原因是内存交换时出的错。
	 * 当前方案采用：查看了这么多个点后，就回收一下内存垃圾。
	 */
	int _GCTimes = 25000;
	
	List<Double> pointID;

	public static void main(String[] args) {
		String a = "看文"; // utf-8
//		String a1 = new String("看文","utf-8");
		try {
			byte[] b = a.getBytes("gbk");
			String c = new String(b, "gbk");
			System.out.println(c);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		ServerManager.getInstance().updateServerInfo();
//		System.out.println(System.getProperty(File.encoding));
//		ServerManager.
//		ServerManager s1 = new ServerManager();
//		s1.instance.name = "aaa";
//		ServerManager s2 = new ServerManager();
//		s2.instance.name = "bbb";
//		System.out.println(ServerManager.getInstance().name);

	}

	private static ServerManager instance;

	public static ServerManager getInstance() {
		if (instance == null)
			instance = new ServerManager();
		return instance;
	}

	public ServerManager() {
		super();
		try {
			// System.out.println("gcLibrary = "+gcLibrary);
//			gec = gec();
			if (instance == null)
				instance = this;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		updateServerInfo();
	}
	// 批量取数 DBECBatchGetTagRealField
//	得到某个位号的实型字段域值
//
//	public double DBECGetTagRealField(String lpszServerName, long nTagID, String lpszFieldName) throws GecException 
//
//	
//
//参数
//
//lpszServerName-----------服务器名
//
//nTagID---------------------位号 ID。
//
//lpszFieldName------------字段域,参见附录一。
//
//返回值
//
//实型字段域值
//
//
//
//（7.2）得到某个位号的实型字段域值
//
//	public double DBECGetTagRealFieldByTagName(String lpszServerName, String lpszTagName,String lpszFieldName) throws GecException 
//
//参数
//
//lpszServerName-----------服务器名
//
//lpszTagName--------------位号名。
//
//lpszFieldName------------字段域,参见附录一。
//
//返回值
//
//实型字段域值

	private synchronized void updateServerInfo() {
//		String[] encodeString = {"",""};
		int _gcTimes = 0;
		boolean t = true;
		List<String> servs = null;
		try {
			//
			LOGGER.debug(" ====================================  ");
			try {
				servs = gec().DBECEnumServerName();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			LOGGER.debug(" ====================================  ");
			servers = new Hashtable<String, Server>();
			// 取服务器信息
			for (int iserver = 0; iserver < servs.size(); iserver++) {
				Server server = new Server();
				String serverName = servs.get(iserver).toUpperCase();
				LOGGER.debug(" ====== Server : \"+serverName+\" ==================");

				server.setServerName(serverName);
				defaultServer = server;
				// 取装置信息

				List<String> devices;
				try {
					devices = gec().DBECEnumDeviceName(serverName);
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.error("实时数据库服务运行错误，请查看是否已经正常启动。");
					throw new IllegalStateException("实时数据库服务运行错误，请查看是否已经正常启动。");
				}

				for (int idevice = 0; idevice < devices.size(); idevice++) {
					String deviceName = devices.get(idevice);
					LOGGER.debug(" ====== deviceName : "+deviceName+" ==================");
					Device device = new Device();
					device.setDeviceName(deviceName);
					Long deviceId = null;
					try {
						deviceId = gec().DBECGetDeviceID(serverName, deviceName);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					device.setId(deviceId);
					String deviceNote = null;
					try {
						deviceNote = gec().DBECGetDeviceNote(serverName, deviceName, deviceId);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}//transByteToString(buffer);
					deviceNote = deviceNote.trim();
					device.setDeviceNote(deviceNote);
					//取点位信息
					List<Long> pointIds = null;
					try {
						long num  = gec().DBECGetTagCountOfDeviceByDeviceName(serverName, deviceName);
						LOGGER.debug(num+"设备个数");
						if(num > 0)
							pointIds = gec().DBECEnumTagIDOfDeviceByDeviceName(serverName, deviceName);
						//System.out.println("debug lab 1");
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(pointIds == null)
						pointIds = new ArrayList<Long>();

					//System.out.println("debug lab 2");
					ByteBuffer tagbuffer = ByteBuffer.allocate(GlobalConsts.DeviceNoteLength);
					//System.out.println("debug lab 3");
					for (int ipoint = 0; ipoint < pointIds.size(); ipoint++) {
						Point point = new Point();
						Long pointId = pointIds.get(ipoint);
						//
						String tagName = null;
						try {
							//System.out.println("debug lab 4");
							//============== 这里发生一次虚拟机崩溃退出. 次数:1+1+1+1
							// serverName=NCIC  pointId=41978
							// serverName=NCIC  pointId=41981
							// serverName=NCIC  pointId=41975
							//System.out.println("debug lab - serverName="+serverName+"  pointId="+pointId);
							tagName = gec().DBECGetTagName(serverName, pointId);
							// 怀疑是内存交换产生的问题，这里增加垃圾收集，看看效果会如何。
							if(_gcTimes > _GCTimes) {
								_gcTimes = 0;
								System.gc();
							}
							//System.out.println("debug lab 5");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						tagName = tagName.trim();
						String tagdesc = null;
						try {
							//System.out.println("debug lab 6");
							tagdesc = gec().DBECGetTagStringFields(serverName, tagName, pointId, tagbuffer,"FN_TAGNOTE");
							//System.out.println("debug lab 7");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
//						System.out.println("pointid: "+pointId+"  tagdesc: " + tagdesc);
						if (StringUtil.isBlank(tagdesc))
							tagdesc = "未知描述";
						String enunit = null;
						try {
							//System.out.println("debug lab 8");
							enunit = gec().DBECGetTagStringFields(serverName, tagName, pointId, tagbuffer,"FN_ENUNITS");
							//System.out.println("debug lab 9");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
//						System.out.println(enunit);
						String tagType = null;
						try {
							//System.out.println("debug lab 10");
							tagType = gec().DBECGetTagStringFields(serverName, tagName, pointId, tagbuffer,"FN_TAGTYPE");
							//System.out.println("debug lab 11");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						double hihiLimit;
						double hiLimit;
						double loLimit;
						double loloLimit;	
						try {
							List<Long> pnTagIDArray = new ArrayList<Long>();
							pnTagIDArray.add(pointId);
							List<Double> hihiLimit1 = gec().DBECBatchGetTagRealField(serverName, pnTagIDArray, "FN_HIHILIMIT");
							List<Double> hiLimit1 = gec().DBECBatchGetTagRealField(serverName, pnTagIDArray, "FN_HILIMIT");
							List<Double> loloLimit1 = gec().DBECBatchGetTagRealField(serverName, pnTagIDArray, "FN_LOLOLIMIT");
							List<Double> loLimit1 = gec().DBECBatchGetTagRealField(serverName, pnTagIDArray, "FN_LOLIMIT");
							hihiLimit = hihiLimit1.get(0);
							hiLimit = hiLimit1.get(0);
							loloLimit = loloLimit1.get(0);
							loLimit = loLimit1.get(0);
//							hihiLimit = gec().DBECGetTagRealFieldByTagName(serverName, tagName, "HIHILIMIT");
//							hihiLimit = gec().DBECGetTagRealField(serverName, pointId, "HIHILIMIT");
//							hiLimit = gec().DBECGetTagRealField(serverName, pointId, "HILIMIT");
//							loLimit = gec().DBECGetTagRealField(serverName, pointId, "LOLIMIT");
//							loloLimit = gec().DBECGetTagRealField(serverName, pointId, "LOLOLIMIT");
							point.setHihiLimit(hihiLimit);
							point.setHiLimit(hiLimit);
							point.setLoLimit(loLimit);
							point.setLoloLimit(loloLimit);
//							LOGGER.info("tagName="+tagName+" hihiLimit="+hihiLimit+" hiLimit="+hiLimit+" loloLimit="+loloLimit+" loLimit="+loLimit);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						tagType = tagType.trim();
						point.setEnunit(enunit);
						point.setTagType(tagType);
						// 二次
						point.setDeviceName(deviceName);
						point.setId(pointId);
						point.setServerName(serverName);
						point.setTagName(tagName);
						point.setDesc(tagdesc);
						device.addPoint(point);
						server.addPoint(point);
						//System.out.println("debug lab 12");
						// 使用tag做key
//						if (redisService != null)
							redisService.set(GlobalConsts.Key_Point_pre + serverName
									+ GlobalConsts.Key_splitCharServerPoint + point.tagName.toString(), point);
							//System.out.println("debug lab 13");
					}
					server.addDevice(device);
					//System.out.println("debug lab 14");
					if (redisService != null)
						redisService.set(GlobalConsts.Key_Device_pre_ + IDTools.toString(device.id), device);
						//System.out.println("debug lab 15");
				}
				servers.put(server.getServerName(), server);
//				if (redisService != null)
				//System.out.println("debug lab 16");
				redisService.set(GlobalConsts.Key_Server_pre_ + server.serverName, server);
				//System.out.println("debug lab 17");

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String transByteToString(ByteBuffer buffer) {
		byte[] sb = buffer.array();
		String deviceNote = null;
		try {
			deviceNote = new String(sb, "GBK");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return deviceNote = deviceNote.trim();
	}
	private JGecService gec() throws Exception {
		return new JGecService(gcLibrary);
	}

	public Point getPointByID(String serverName, String pointKey) {
		if (StringUtil.isBlank(serverName))
			serverName = defaultServer.getServerName();
		// gec().g
		// gec().DBECBatchGetTagRealField(lpszServerName, pnIDArray, lpszFieldName)
		return (Point) redisService
				.get(GlobalConsts.Key_Point_pre + serverName + GlobalConsts.Key_splitCharServerPoint + pointKey);
	}

	private static boolean inited = false;

	public static boolean inited() {
		return inited;
	}

	/**
	 * 初始化服务器、点位等信息到缓存。
	 */
	public static void init() {
		ServerManager.getInstance().updateServerInfo();
		inited = true;
		
	}

	/**
	 * 取指定点位的历史值
	 * @param srvName
	 * @param tagName
	 * @param id
	 * @param beginTime
	 * @param endTime
	 */
	public synchronized HashMap<Long, Double> getPointHistoryValue(String srvName,String tagName,long id,long beginTime,long endTime) {
		HashMap<Long, Double> ret = new HashMap<Long, Double>();
		List<Double> pValueArray = new ArrayList<Double>(); 
		int size = Math.round((endTime - beginTime) )+1;
		System.out.println("------------------------------------------");
		System.out.println(" 想要取数据个数： "+size);
		List<Long> pnValueTimeArray = new ArrayList<Long>();
		try {
//			System.out.println("DBECGetTagRealHistory => srvName="+srvName+" tagName="+tagName+" id="+IDTools.toString(id)
//			+" beginTime="+IDTools.toString(beginTime)+" endTime="+IDTools.toString(endTime)
//			+" size="+size+" "+Thread.currentThread().getName()+":"+Thread.currentThread().getId());
			LOGGER.debug("gec().DBECGetTagRealHistory beginTime= "+TimeTools.parseStr(beginTime)+" endTime= "+TimeTools.parseStr(endTime)
			+" "+Thread.currentThread().getName()+":"+Thread.currentThread().getId());
			gec().DBECGetTagRealHistory(srvName, tagName, id, beginTime, endTime, pValueArray, size, pnValueTimeArray);
		} catch (Exception e) {
			LOGGER.error("gec().DBECGetTagRealHistory error occour --------------------" );
			LOGGER.error("gec().DBECGetTagRealHistory beginTime= "+TimeTools.parseStr(beginTime)+" endTime= "+TimeTools.parseStr(endTime)
			+" "+Thread.currentThread().getName()+":"+Thread.currentThread().getId());
			LOGGER.error("gec().DBECGetTagRealHistory beginTime= "+TimeTools.parseStr(beginTime)+" endTime= "+TimeTools.parseStr(endTime));
			LOGGER.error("gec().DBECGetTagRealHistory error occour -- "+e.getMessage());
			e.printStackTrace();
			LOGGER.error("gec().DBECGetTagRealHistory error occour --------------------");
		}
		LOGGER.debug("pValueArray.size() = "+pValueArray.size());
		pValueArray.removeAll(Collections.singleton(null));
		pnValueTimeArray.removeAll(Collections.singleton(null));
		if(pValueArray.size() == pnValueTimeArray.size()) {
			for(int i=0;i<pValueArray.size();i++) {
				ret.put(pnValueTimeArray.get(i),pValueArray.get(i));
			}
		} else {
			LOGGER.info("查询历史值时，返回的时间和值不对应。");
		}
	
		return ret;
	}


	public Long getServerCurrentTime() {
		return getServerCurrentTime(defaultServer.serverName);
	}
	
	public Long getServerCurrentTime(String srvName) {
		try {
			return gec().DBECGetServerCurrentTime(srvName)*1000;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public synchronized List getPointValue(String srvName, List<Long> idList, String fieldName) {
		// TODO Auto-generated method stub
		if (StringUtil.isBlank(fieldName))
			fieldName = "FN_RTVALUE";
//		System.out.println("ServerManager.getPointValue("+srvName+",idList,fieldName)");
//gec().DBACGetCurrentAlarm(lpszServerName, pnTagIDArray, pAlarmTypeArray, nArraySize, pValueArray, pOccuredTimeArray);
		try {
			return gec().DBECBatchGetTagRealField(srvName, idList, fieldName);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList();
		}
//		return null;
	}
	
	public static boolean hasServerName(String serverName) {
		if (servers.containsKey(serverName))
			return true;
		else
			return false;
	}

	
	public synchronized List<PointAlertData> getPointAlertData(List<Point> pl) {
		List<PointAlertData> ret = new ArrayList<PointAlertData>();
		int nArraySize = pl.size();
		HashMap<Long,Point> ph = new HashMap<Long,Point>();
		Hashtable<String,List<Long>> sps = new Hashtable<String,List<Long>>();
		for(int ind=0;ind<pl.size();ind++) {
			ph.put(pl.get(ind).getId(), pl.get(ind));
			String lpszServerName = pl.get(ind).getServerName();
			if(sps.containsKey(lpszServerName)) {
				sps.get(lpszServerName).add(pl.get(ind).getId());
			} else {
				ArrayList<Long> il = new ArrayList<Long>();
				il.add(pl.get(ind).getId());
				sps.put(lpszServerName, il);
			}
		}
		for(Iterator<String> iterator=sps.keySet().iterator();iterator.hasNext();){
			String lpszServerName=iterator.next();
			
			List<Long> pnTagIDArray = sps.get(lpszServerName);
			List<Long> pAlarmTypeArray = new ArrayList<Long>();
			List<Double> pValueArray = new ArrayList<Double>();
			List<Long> pOccuredTimeArray = new ArrayList<Long>();
			try {
				gec().DBACGetCurrentAlarm(lpszServerName, pnTagIDArray, pAlarmTypeArray, nArraySize, pValueArray, pOccuredTimeArray);
				List<Double> hihiLimit = gec().DBECBatchGetTagRealField(lpszServerName, pnTagIDArray, "FN_HIHILIMIT");
				List<Double> hiLimit = gec().DBECBatchGetTagRealField(lpszServerName, pnTagIDArray, "FN_HILIMIT");
				List<Double> loloLimit = gec().DBECBatchGetTagRealField(lpszServerName, pnTagIDArray, "FN_LOLOLIMIT");
				List<Double> loLimit = gec().DBECBatchGetTagRealField(lpszServerName, pnTagIDArray, "FN_LOLIMIT");
				long serverTime = gec().DBECGetServerCurrentTime(lpszServerName);
				for(int i=0;i<pnTagIDArray.size();i++) {
					PointAlertData pad = new PointAlertData(ph.get(pnTagIDArray.get(i)));
					pad.setOccuredTime(pOccuredTimeArray.get(i));
					pad.setDuration(serverTime - pOccuredTimeArray.get(i));
					pad.setAlertValue(pValueArray.get(i));
					pad.setAlertType(pAlarmTypeArray.get(i));
					pad.setHihiLimit(hihiLimit.get(i));
					pad.setHiLimit(hiLimit.get(i));
					pad.setLoLimit(loLimit.get(i));
					pad.setLoloLimit(loloLimit.get(i));
					ret.add(pad);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	public synchronized List<PointAlertData> getHistoryPointAlertData(List<Point> pl, Long nBeginTime, Long nEndTime) {
		List<PointAlertData> ret = new ArrayList<PointAlertData>();
		int nArraySize = pl.size();
		HashMap<Long,Point> ph = new HashMap<Long,Point>();
		Hashtable<String,List<Long>> sps = new Hashtable<String,List<Long>>();
		for(int ind=0;ind<pl.size();ind++) {
			ph.put(pl.get(ind).getId(), pl.get(ind));
			String lpszServerName = pl.get(ind).getServerName();
			if(sps.containsKey(lpszServerName)) {
				sps.get(lpszServerName).add(pl.get(ind).getId());
			} else {
				ArrayList<Long> il = new ArrayList<Long>();
				il.add(pl.get(ind).getId());
				sps.put(lpszServerName, il);
			}
		}
		for(Iterator<String> iterator=sps.keySet().iterator();iterator.hasNext();){
			String lpszServerName=iterator.next();
			
			List<Long> pnTagIDArray = sps.get(lpszServerName);
			List<Long> pnAlarmTagIDArray = new ArrayList<Long>();
			List<Long> pnAlarmCount = new ArrayList<Long>();
			List<Long> pAlarmBeginTimeArray = new ArrayList<Long>();
			List<Long> pAlarmEndTimeArray = new ArrayList<Long>();
			List<Long> pAlarmTypeArray = new ArrayList<Long>();
			List<Double> pValueArray = new ArrayList<Double>();
			List<Long> pOccuredTimeArray = new ArrayList<Long>();
			try {
//				gec().DBACGetCurrentAlarm(lpszServerName, pnTagIDArray, pAlarmTypeArray, nArraySize, pValueArray, pOccuredTimeArray);
				gec().DBACGetHistoryAlarm(lpszServerName, pnTagIDArray, nBeginTime, nEndTime, pnAlarmTagIDArray, nArraySize, pnAlarmCount, pAlarmBeginTimeArray, pAlarmEndTimeArray, pAlarmTypeArray);
				
				List<Double> hihiLimit = gec().DBECBatchGetTagRealField(lpszServerName, pnTagIDArray, "FN_HIHILIMIT");
				List<Double> hiLimit = gec().DBECBatchGetTagRealField(lpszServerName, pnTagIDArray, "FN_HILIMIT");
				List<Double> loloLimit = gec().DBECBatchGetTagRealField(lpszServerName, pnTagIDArray, "FN_LOLOLIMIT");
				List<Double> loLimit = gec().DBECBatchGetTagRealField(lpszServerName, pnTagIDArray, "FN_LOLIMIT");

				for(int i=0;i<pnAlarmTagIDArray.size();i++) {
					Long lid = pnAlarmTagIDArray.get(i);
					if(lid!=null&&lid!=0) {
					PointAlertData pad = new PointAlertData(ph.get(pnTagIDArray.get(i)));
					// pad.setOccuredTime(pOccuredTimeArray.get(i));
						pad.setAlertValue(pValueArray.get(i));
						pad.setAlertType(pAlarmTypeArray.get(i));
						pad.setHihiLimit(hihiLimit.get(i));
						pad.setHiLimit(hiLimit.get(i));
						pad.setLoLimit(loLimit.get(i));
						pad.setLoloLimit(loloLimit.get(i));
						pad.setAlertBeginTime(pAlarmBeginTimeArray.get(i));
						pad.setAlertEndTime(pAlarmEndTimeArray.get(i));
						ret.add(pad);
					}
				}			
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return ret;
	}

}
