package com.surpass.vision.server;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Hashtable;
import java.util.List;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

import com.surpass.realkits.JGecService;
import com.surpass.realkits.exception.GecException;
import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.tools.EncodingTools;
import com.surpass.vision.tools.IDTools;

@Component
public class ServerManager {

	public static Server defaultServer;
	static Hashtable<String, Server> servers = new Hashtable<String, Server>();

	public static Hashtable<String, Server> getServers() {
		return servers;
	}

	// @Value("${gc.library}")
	private String gcLibrary = "geC.dll";

	private JGecService gec;

	@Reference
	@Autowired
	RedisService redisService;

	public String name = "s1";

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
			gec = gec();
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

	private void updateServerInfo() {
//		String[] encodeString = {"",""};
		boolean t = true;
		List<String> servs;
		try {
			//
			servs = gec.DBECEnumServerName();
			servers = new Hashtable<String, Server>();
			// 取服务器信息
			for (int iserver = 0; iserver < servs.size(); iserver++) {
				Server server = new Server();
				String serverName = servs.get(iserver);
				server.setServerName(serverName);
				defaultServer = server;
				// 取装置信息

				List<String> devices;
				try {
					devices = gec.DBECEnumDeviceName(serverName);
				} catch (Exception e) {
					e.printStackTrace();
					throw new IllegalStateException("实时数据库服务运行错误，请查看是否已经正常启动。");
				}

				for (int idevice = 0; idevice < devices.size(); idevice++) {
					String deviceName = devices.get(idevice);
					Device device = new Device();
					device.setDeviceName(deviceName);
					Long deviceId = gec.DBECGetDeviceID(serverName, deviceName);
					device.setId(deviceId);
					String deviceNote = gec.DBECGetDeviceNote(serverName, deviceName, deviceId);//transByteToString(buffer);
					deviceNote = deviceNote.trim();
					device.setDeviceNote(deviceNote);
					// 取点位信息
					List<Long> pointIds = gec.DBECEnumTagIDOfDeviceByDeviceName(serverName, deviceName);
					ByteBuffer tagbuffer = ByteBuffer.allocate(GlobalConsts.DeviceNoteLength);
					for (int ipoint = 0; ipoint < pointIds.size(); ipoint++) {
						Point point = new Point();
						Long pointId = pointIds.get(ipoint);
						//
						String tagName = gec.DBECGetTagName(serverName, pointId);
						tagName = tagName.trim();
						String tagdesc = gec.DBECGetTagStringFields(serverName, tagName, pointId, tagbuffer,"FN_TAGNOTE");
						System.out.println("pointid: "+pointId+"  tagdesc: " + tagdesc);
						if (StringUtil.isBlank(tagdesc))
							tagdesc = "未知描述";
						String enunit = gec.DBECGetTagStringFields(serverName, tagName, pointId, tagbuffer,"FN_ENUNITS");
						System.out.println(enunit);
						String tagType = gec.DBECGetTagStringFields(serverName, tagName, pointId, tagbuffer,"FN_TAGTYPE");
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
						// 使用tag做key
//						if (redisService != null)
							redisService.set(GlobalConsts.Key_Point_pre + serverName
									+ GlobalConsts.Key_splitCharServerPoint + point.tagName.toString(), point);
					}
					server.addDevice(device);
//					if (redisService != null)
						redisService.set(GlobalConsts.Key_Device_pre_ + IDTools.toString(device.id), device);
				}
				servers.put(server.getServerName(), server);
//				if (redisService != null)
					redisService.set(GlobalConsts.Key_Server_pre_ + server.serverName, server);

			}
		} catch (GecException e) {
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
		// gec.g
		// gec.DBECBatchGetTagRealField(lpszServerName, pnIDArray, lpszFieldName)
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

	public List getPointValue(String srvName, List<Long> idList, String fieldName) {
		// TODO Auto-generated method stub
		if (StringUtil.isBlank(fieldName))
			fieldName = "FN_RTVALUE";

		try {
			return gec.DBECBatchGetTagRealField(srvName, idList, fieldName);
		} catch (GecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static boolean hasServerName(String serverName) {
		if (servers.containsKey(serverName))
			return true;
		else
			return false;
	}

}
