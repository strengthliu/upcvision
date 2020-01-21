package com.surpass.vision.controller;

import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * websocket
 * 消息推送(个人和广播)
 */
@Controller
public class WebSocketController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SocketServer socketServer;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    private SimpUserRegistry userRegistry;
    
    
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
        System.out.println(topic+"  "+ type);
        System.out.println(headers);
        // 添加一条主题
        if(StringUtil.isBlank(type)) type = GlobalConsts.Key_RealTimeData_pre_;
        switch(type.toLowerCase()) {
        case "realtimedata":
        	type = GlobalConsts.Key_RealTimeData_pre_;
        	break;
        case "alertdata":
        	type = GlobalConsts.Key_AlertData_pre_;
        	break;
        case "historydata":
        	type = GlobalConsts.Key_HistoryData_pre_;
        	break;
        case "linealertdata":
        	type = GlobalConsts.Key_LineAlertData_pre_;
        	break;
        case "xygraph":
        	type = GlobalConsts.Key_XYGraph_pre_;
        	break;
        case "graph":
        	type = GlobalConsts.Key_Graph_pre_;
        	break;
        }
        String topicRequest = "/topic/"+type+"/"+id;
        logger.info(topicRequest);
        socketServer.addRequest(type,topicRequest,Double.valueOf(id));
        
        
    }
 
//    socketServer
}
