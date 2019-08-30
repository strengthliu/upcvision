package com.surpass.vision.controller;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.domain.Greeting;
import com.surpass.vision.server.SocketServer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * websocket
 * 消息推送(个人和广播)
 */
@Controller
public class WebSocketController {

    @Autowired
    private SocketServer socketServer;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    private SimpUserRegistry userRegistry;
    
//    /**
//     *
//     * 客户端页面
//     * @return
//     */
//    @RequestMapping(value = "/index")
//    public String idnex() {
//
//        return "index";
//    }

//    /**
//     * 个人信息推送
//     * @return
//     */
//    @RequestMapping("sendmsg")
//    @ResponseBody
//    public String sendmsg(String msg, String username){
//        //第一个参数 :msg 发送的信息内容
//        //第二个参数为用户长连接传的用户人数
//        String [] persons = username.split(",");
//        SocketServer.SendMany(msg,persons);
//        return "success";
//    }

//    /**
//     * 推送给所有在线用户
//     * @return
//     */
//    @RequestMapping("sendAll")
//    @ResponseBody
//    public String sendAll(String msg){
//        SocketServer.sendAll(msg);
//        return "success";
//    }
    
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;
 
    /**
     * 表示服务端可以接收客户端通过主题“/app/hello”发送过来的消息，客户端需要在主题"/topic/hello"上监听并接收服务端发回的消息
     * @param topic
     * @param headers
     */
    @MessageMapping("/aaa") //"/hello"为WebSocketConfig类中registerStompEndpoints()方法配置的
    @SendTo("/topic/realTimeData")
    public void greeting(@Header("atytopic") String topic,@Header("id") String id,@Header("type") String type, @Headers Map<String, Object> headers) {
        System.out.println("connected successfully....");
        System.out.println(topic);
        System.out.println(headers);
        // 添加一条主题
        if(StringUtil.isBlank(type)) type = GlobalConsts.Key_RealTimeData_pre_;
        switch(type) {
        case "realtimeData":
        	type = GlobalConsts.Key_RealTimeData_pre_;
        	break;
        case "alertData":
        	type = GlobalConsts.key_AlertData_pre;
        	break;
        }
        String topicRequest = "/topic/"+type+"/"+id;
        socketServer.addRequest(type,topicRequest,Double.valueOf(id));
        this.messagingTemplate.convertAndSend("/topic/realTimeData/"+topic, new Greeting("Hello," + topic + "!"));
        try {
			Thread.sleep(500);
	        this.messagingTemplate.convertAndSend("/topic/realTimeData/"+topic, new Greeting("Hello," + topic + "!"));
			Thread.sleep(500);
	        this.messagingTemplate.convertAndSend("/topic/realTimeData/"+topic, new Greeting("Hello," + topic + "!"));
			Thread.sleep(500);
	        this.messagingTemplate.convertAndSend("/topic/realTimeData/"+topic, new Greeting("Hello," + topic + "!"));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        this.messagingTemplate.convertAndSend("/topic/realTimeData/"+topic, new Greeting("Hello," + topic + "!"));
        this.messagingTemplate.convertAndSend("/topic/greeting/11", new Greeting("Hello1," + topic + "!"));
        
    }
 
    /**
     * 这里用的是@SendToUser，这就是发送给单一客户端的标志。本例中，
     * 客户端接收一对一消息的主题应该是“/user/” + 用户Id + “/message” ,这里的用户id可以是一个普通的字符串，只要每个用户端都使用自己的id并且服务端知道每个用户的id就行。
     * @return
     */
    @MessageMapping("/message")
    @SendToUser("/message")
    public Greeting handleSubscribe() {
        System.out.println("this is the @SubscribeMapping('/marco')");
        return new Greeting("I am a msg from SubscribeMapping('/macro').");
    }
 
    /**
     * 测试对指定用户发送消息方法
     * @return
     */
    @RequestMapping(path = "/send", method = RequestMethod.GET)
    public Greeting send() {
        simpMessageSendingOperations.convertAndSendToUser("1", "/message", new Greeting("I am a msg from SubscribeMapping('/macro')."));
        return new Greeting("I am a msg from SubscribeMapping('/macro').");
    }

}
