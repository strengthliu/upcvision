package com.surpass.vision.server;

import java.nio.charset.Charset;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;

import com.surpass.realkits.JGecService;
import com.surpass.realkits.exception.GecException;
import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.service.RedisService;
import com.surpass.vision.tools.EncodingTools;

@Component
public class ServerManager {

	Hashtable<String,Server> servers;

	//@Value("${gc.library}")
	private String gcLibrary = "geC.dll";

	private JGecService gec;
	
	@Reference
	@Autowired
	RedisService redisService;

	public String name = "s1";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerManager.getInstance();
//		System.out.println(System.getProperty(File.encoding));
//		ServerManager.
		ServerManager s1 = new ServerManager();
		s1.instance.name = "aaa";
		ServerManager s2 = new ServerManager();
		s2.instance.name = "bbb";
		System.out.println(ServerManager.getInstance().name);
		
	}
	private static ServerManager instance;
	public static ServerManager getInstance() {
		if(instance == null) instance = new ServerManager();
		return instance;
	}
	
	public ServerManager() {
		super();
		try {
			System.out.println("gcLibrary = "+gcLibrary);
			gec = gec();
			if(instance == null)
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
		List<String> servs;
		try {
			//
			servs = gec.DBECEnumServerName();
			servers = new Hashtable<String,Server>();
			// 取服务器信息
			for (int iserver = 0; iserver < servs.size(); iserver++) {
				Server server = new Server();
				String serverName = servs.get(iserver);
				server.setServerName(serverName);
				// 取装置信息
				List<String> devices = gec.DBECEnumDeviceName(serverName);
				
				for (int idevice = 0; idevice < devices.size(); idevice++) {
					String deviceName = devices.get(idevice);
					Device device = new Device();
					device.setDeviceName(deviceName);
					Long deviceId = gec.DBECGetDeviceID(serverName, deviceName);
					device.setId(deviceId);
					String deviceNote = gec.DBECGetDeviceNote(serverName, deviceName, deviceId, GlobalConsts.DeviceNoteLength);
					deviceNote = deviceNote.trim();
					System.out.println("Encoding: "+EncodingTools.getEncoding(deviceNote)+"  "+Charset.defaultCharset());
					byte[] b;
					try {
						b = deviceNote.getBytes("GB2312");
//						b = deviceNote.getBytes();
						deviceNote = new String(b, Charset.defaultCharset());
						System.out.println(deviceNote);
						//b = deviceNote.getBytes(Charset.defaultCharset());
//						deviceNote = new String(b, Charset.defaultCharset());//解码:用什么字符集编码就用什么字符集解码
//						deviceNote = new String(b, "utf-8");//解码:用什么字符集编码就用什么字符集解码
//						System.out.println(deviceNote);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//编码  
					
					device.setDeviceNote(deviceNote);
					// 取点位信息
					List<Long> pointIds = gec.DBECEnumTagIDOfDeviceByDeviceName(serverName, deviceName);
					for(int ipoint=0;ipoint<pointIds.size();ipoint++) {
						Point point = new Point();
						Long pointId = pointIds.get(ipoint);
						//
						String tagName = gec.DBECGetTagName(serverName,pointId);
//						gec.DBECGetDeviceNote(lpszServerName, lpszDeviceName, nDeviceID, nBufLen)
//						String desc = gec.DBECGetTagStringField(serverName, deviceName, pointId, lpszFieldName)
						tagName = tagName.trim();
						//二次
						point.setDeviceName(deviceName);
						point.setId(pointId);
						point.setServerName(serverName);
						point.setTagName(tagName);
//						System.out.println(serverName+" - "+deviceName+" - "+deviceNote+" - "+tagName+" - "+pointId+" - "+point.id);
						// 
						device.addPoint(point);
						server.addPoint(point);
//						System.out.println(GlobalConsts.Key_Point_pre);
//						System.out.println(point.id.toString());
						redisService.set(GlobalConsts.Key_Point_pre + point.id.toString(), point);
					}
					server.addDevice(device);
					redisService.set(GlobalConsts.Key_Device_pre_+device.id.toString(), device);
				}
				servers.put(server.getServerName(),server);
				redisService.set(GlobalConsts.Key_Server_pre_+server.serverName, server);
				
			}
		} catch (GecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private JGecService gec() throws Exception {
		return new JGecService(gcLibrary);
	}

	public Point getPointByID(String pointKey) {
		return (Point)redisService.get(GlobalConsts.Key_Point_pre+pointKey);
	}

	/**
	 * 初始化服务器、点位等信息到缓存。
	 */
	public static void init() {
		ServerManager.getInstance().updateServerInfo();
	}

}