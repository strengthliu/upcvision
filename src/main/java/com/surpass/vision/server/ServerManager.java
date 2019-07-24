package com.surpass.vision.server;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.surpass.realkits.JGecService;
import com.surpass.realkits.exception.GecException;

@Component
public class ServerManager {

	Hashtable<String,Server> servers;

	//@Value("${gc.library}")
	private String gcLibrary = "geC.dll";

	private JGecService gec;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerManager.getInstance();
//		System.out.println(System.getProperty(File.encoding));
//		ServerManager.
	}
	private static ServerManager instance;
	public static ServerManager getInstance() {
		if(instance == null) instance = new ServerManager();
		return instance;
	}
	
	public ServerManager() {
		try {
			System.out.println("gcLibrary = "+gcLibrary);
			gec = gec();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateServerInfo();
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
			for (int iserver = 0; iserver < servs.size(); iserver++) {
				Server server = new Server();
				String serverName = servs.get(iserver);
				server.setServerName(serverName);
				//
				List<String> devices = gec.DBECEnumDeviceName(serverName);
				for (int idevice = 0; idevice < devices.size(); idevice++) {
					String deviceName = devices.get(idevice);
					Device device = new Device();
					device.setDeviceName(deviceName);
					//
					List<Long> pointIds = gec.DBECEnumTagIDOfDeviceByDeviceName(serverName, deviceName);
					for(int ipoint=0;ipoint<pointIds.size();ipoint++) {
						Point point = new Point();
						Long pointId = pointIds.get(ipoint);
						//
						String tagName = gec.DBECGetTagName(serverName,pointId);
						tagName = tagName.trim();
						//二次
						point.setDeviceName(deviceName);
						point.setId(pointId);
						point.setServerName(serverName);
						point.setTagName(tagName);
//						System.out.println(serverName+" - "+deviceName+" - "+tagName);
						// 
						device.addPoint(point);
						server.addPoint(point);
					}
					server.addDevice(device);
				}
				servers.put(server.getServerName(),server);
			}
		} catch (GecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private JGecService gec() throws Exception {
		return new JGecService(gcLibrary);
	}

}
