package com.surpass.vision.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.surpass.vision.domain.Client;
import com.surpass.vision.domain.Greeting;
import com.surpass.vision.domain.PointGroup;
import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.pointGroup.PointGroupDataManager;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.stream.Collectors;

//@ServerEndpoint(value = "/socketServer/")
/**---------------------------------------
 * @Title: SocketServer.java
 * @Package:com.surpass.vision.server
 * ---------------------------------------
 * @Description:
 *     
 * ---------------------------------------
 * @author 刘强
 * 2019年8月25日 下午6:33:02
 * @version: v1.0  
 * ---------------------------------------
 */
@Component
public class SocketServer {
//
	private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);
	
//	public void convertAndSend(D destination, Object payload)：
//	给监听了路径destination的所有客户端发送消息payload
//	public void convertAndSendToUser(String user, String destination, Object payload)：
//	给监听了路径destination的用户user发送消息payload

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    @Autowired
    PointGroupDataManager pointGroupDataManager;
    
    @Autowired
    PointList pointList;
    /**
	 *
	 * 用线程安全的CopyOnWriteArraySet来存放客户端连接的信息
	 */
	private static CopyOnWriteArraySet<Client> socketServers = new CopyOnWriteArraySet<>();


	/**
	 * 当前订阅的主题，及每个主题连接的客户端数。
	 * 当连接数为0时，就移除这个主题。
	 * 跟PointList里的Viewers对应。
	 * ---------------------------------------
	 * @author 刘强 2019年8月25日 下午6:31:42 
	 */
	static Hashtable<Double,String> topics = new Hashtable<Double,String>();
	static Hashtable<Double,AtomicInteger> used = new Hashtable<Double,AtomicInteger>();
	static Hashtable<Double,String> types = new Hashtable<Double,String>();
	static Hashtable<Double,DataViewer> dvs = new Hashtable<Double,DataViewer>();
	
	
	// 解决ABA问题
	AtomicStampedReference<PointsData> reference = new AtomicStampedReference<PointsData>(new PointsData(),1);
	/**
	 * 增加一个新订阅
	 * @param topic
	 */
	public synchronized void addRequest(String type,String topic,Double id) {
		if(topics.containsKey(id)) {
			AtomicInteger ai = used.get(id);
			ai.getAndIncrement();
		}else {
			topics.put(id, topic);
			AtomicInteger ai = new AtomicInteger(0);
			ai.getAndIncrement();
			used.put(id, ai);
			types.put(id,type);
			DataViewer dv = pointGroupDataManager.buildDataViewer(id,type);
			dv = pointList.addPoints(dv);
			dvs.put(id, dv);
		}
	}
	
	/**
	 * 减少一个订阅
	 * @param topic
	 */
	public synchronized void disconnect(String topic,Double id) {
		if(topics.containsKey(id)) {
			AtomicInteger ai = used.get(id);
			int a = ai.getAndDecrement();
			if(a==1) {
				topics.remove(id);
				used.remove(id);
				types.remove(id);
				dvs.remove(id);
			}
		}else {
			
		}
		
	}
	
	@Async("postMessageExecutor")
	public void sendMessage(String topic,HashMap message) {
//		System.out.println(topic+""+message);
        messagingTemplate.convertAndSend(topic, message);
	}


	public void sendAllMessage() {
		// TODO Auto-generated method stub
		// 循环topics，取数，群发消息。
		//遍历key
		Enumeration e = topics.keys();

		while( e. hasMoreElements() ){
			Double k = (Double) e.nextElement();
			String topic = topics.get(k);
			String type = types.get(k);
			// 根据ID取数据

//			System.out.println("按主题列表群发消息.."+topic);
			
			DataViewer dv = dvs.get(k);
			if(dv!=null) {
			// 发送数据
			// tagname : value
				dv.valuesByID();
				if(dv.queryById)
					sendMessage(topic,dv.valuesByID());
				else
					sendMessage(topic,dv.valuesByTagName());
			}
		}
	}
}

//	/**
//	 * 收到数据更新完成消息，向客户端推送消息。
//	 */
//	@Override
//	public void onApplicationEvent(DataUpdateEvent event) {
//		// TODO Auto-generated method stub
//		System.out.println(">>>>>>>>>DemoListener>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//        System.out.println("收到了：" + event.getSource() + "消息;时间：" + event.getTimestamp());
//        System.out.println("消息：" + event.getId() + ":" + event.getMessage());
//
//	}
//	

///**
//*
//* websocket封装的session,信息推送，就是通过它来信息推送
//*/
//private Session session;
//
//
//
///**
//*
//* 用户连接时触发，我们将其添加到
//* 保存客户端连接信息的socketServers中
//*
//* @param session
//* @param userName
//*/
//@OnOpen
//public void open(Session session,@PathParam(value="userName")String userName){
//	
//		this.session = session;
//		socketServers.add(new Client(userName,session));
//
//		logger.info("客户端:【{}】连接成功",userName);
//
//}
//
///**
//*
//* 收到客户端发送信息时触发
//* 我们将其推送给客户端(niezhiliang9595)
//* 其实也就是服务端本身，为了达到前端聊天效果才这么做的
//*
//* @param message
//*/
//@OnMessage
//public void onMessage(String message){
//
//	Client client = socketServers.stream().filter( cli -> cli.getSession() == session)
//			.collect(Collectors.toList()).get(0);
//	sendMessage(client.getUserName()+"<--"+message,SYS_USERNAME);
//
//	logger.info("客户端:【{}】发送信息:{}",client.getUserName(),message);
//}
//
///**
//*
//* 连接关闭触发，通过sessionId来移除
//* socketServers中客户端连接信息
//*/
//@OnClose
//public void onClose(){
//	socketServers.forEach(client ->{
//		if (client.getSession().getId().equals(session.getId())) {
//
//			logger.info("客户端:【{}】断开连接",client.getUserName());
//			socketServers.remove(client);
//
//		}
//	});
//}
//
///**
//*
//* 发生错误时触发
//* @param error
//*/
//@OnError
//public void onError(Throwable error) {
//	socketServers.forEach(client ->{
//		if (client.getSession().getId().equals(session.getId())) {
//			socketServers.remove(client);
//			logger.error("客户端:【{}】发生异常",client.getUserName());
//			error.printStackTrace();
//		}
//	});
//}
//
///**
//*
//* 信息发送的方法，通过客户端的userName
//* 拿到其对应的session，调用信息推送的方法
//* @param message
//* @param userName
//*/
//public synchronized static void sendMessage(String message,String userName) {
//
//	socketServers.forEach(client ->{
//		if (userName.equals(client.getUserName())) {
//			try {
//				client.getSession().getBasicRemote().sendText(message);
////		        messagingTemplate.convertAndSend("/topic/greeting/11", new Greeting("Hello," + topic + "!"));
//
//				logger.info("服务端推送给客户端 :【{}】",client.getUserName(),message);
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	});
//}
//
///**
// *
// * 获取服务端当前客户端的连接数量，
// * 因为服务端本身也作为客户端接受信息，
// * 所以连接总数还要减去服务端
// * 本身的一个连接数
// *
// * 这里运用三元运算符是因为客户端第一次在加载的时候
// * 客户端本身也没有进行连接，-1 就会出现总数为-1的情况，
// * 这里主要就是为了避免出现连接数为-1的情况
// *
// * @return
// */
//public synchronized static int getOnlineNum(){
//	return socketServers.stream().filter(client -> !client.getUserName().equals(SYS_USERNAME))
//			.collect(Collectors.toList()).size();
//}
//
///**
// *
// * 获取在线用户名，前端界面需要用到
// * @return
// */
//public synchronized static List<String> getOnlineUsers(){
//
//	List<String> onlineUsers = socketServers.stream()
//			.filter(client -> !client.getUserName().equals(SYS_USERNAME))
//			.map(client -> client.getUserName())
//			.collect(Collectors.toList());
//
//    return onlineUsers;
//}
//
///**
// *
// * 信息群发，我们要排除服务端自己不接收到推送信息
// * 所以我们在发送的时候将服务端排除掉
// * @param message
// */
//public synchronized static void sendAll(String message) {
//	//群发，不能发送给服务端自己
//	socketServers.stream().filter(cli -> cli.getUserName() != SYS_USERNAME)
//			.forEach(client -> {
//		try {
//			client.getSession().getBasicRemote().sendText(message);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	});
//
//	logger.info("服务端推送给所有客户端 :【{}】",message);
//}
//
///**
// *
// * 多个人发送给指定的几个用户
// * @param message
// * @param persons
// */
//public synchronized static void SendMany(String message,String [] persons) {
//	for (String userName : persons) {
//		sendMessage(message,userName);
//	}
//}
///**
//*
//* 服务端的userName,因为用的是set，每个客户端的username必须不一样，否则会被覆盖。
//* 要想完成ui界面聊天的功能，服务端也需要作为客户端来接收后台推送用户发送的信息
//*/
//private final static String SYS_USERNAME = "niezhiliang9595";

