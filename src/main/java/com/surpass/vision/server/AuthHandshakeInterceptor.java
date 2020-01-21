package com.surpass.vision.server;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.surpass.vision.domain.User;
import com.surpass.vision.userSpace.UserSpaceManager;
import com.surpass.vision.utils.SpringContextUtils;

@Component
public class AuthHandshakeInterceptor extends HttpSessionHandshakeInterceptor{ // implements HandshakeInterceptor {
    private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	UserSpaceManager userSpaceManager;

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        if(serverHttpRequest instanceof ServletServerHttpRequest)
        {
            ServletServerHttpRequest servletRequset = (ServletServerHttpRequest) serverHttpRequest;

            // 这里从request中获取session,获取不到不创建，可以根据业务处理此段
            HttpSession httpSession = servletRequset.getServletRequest().getSession(true);
            if (httpSession != null)
            {
                // 这里打印一下session id 方便等下对比和springMVC获取到httpsession是不是同一个
                logger.info("httpSession key：" + httpSession.getId());

                // 获取到httpsession后，可以根据自身业务，操作其中的信息，这里只是单纯的和websocket进行关联
                map.put("HTTP_SESSION",httpSession);

            }
            else
            {
            	logger.warn("httpSession is null");
            }
        }

        // 调用父类方法
        return super.beforeHandshake(serverHttpRequest,serverHttpResponse,webSocketHandler,map);    	// 身份验证
//    	
//    	// 加入连接池
//    	
////    	"/socketServer/352/yennxdtg/websocket"
////    	$.session.set('token', token);
////    	$.session.set('uid', user.id);
//    	HttpHeaders hhds = serverHttpRequest.getHeaders();
//    	logger.info(serverHttpRequest.getURI().getPath());
//    	HttpSession session = SpringContextUtils.getSession();
//    	Enumeration<String> ans = session.getAttributeNames();
//    	while(ans.hasMoreElements()) {
//    		System.out.println(ans.nextElement());
//    	}
//    	String token = (String) session.getAttribute("token");
//    	String uid = (String) session.getAttribute("uid");
//    	
//    	uid = hhds.getFirst("uid");
//    	token = hhds.getFirst("token");
//    	System.out.println("beforeHandshake...");
//    	// TODO: 先不做验证了，后面再补。
//    	if(true)return true;
//    	if(StringUtil.isBlank(token) || StringUtil.isBlank(uid)) {
////    		System.out.println("UID="+uid+" , token="+token);
//    		return false;
//    	}
//    	if(userSpaceManager.tokenVerification(Double.valueOf(uid), token)) {
//            logger.debug(MessageFormat.format("用户{0}请求建立WebSocket连接", uid));
//    		
//    		return true;
//    	} else {
//            logger.error("未登录系统，禁止连接WebSocket");
//    		return false;
//    	}
    	// System.out.println("beforeHandshake log point 1");
        // User loginUser = null;//(User) session.getAttribute(Constants.SESSION_USER);
        /** 先完成整体，再做验证。
        if(loginUser != null){
            logger.debug(MessageFormat.format("用户{0}请求建立WebSocket连接", loginUser.getName()));
            return true;
        }else{
            logger.error("未登录系统，禁止连接WebSocket");
            return false;
        }
         */
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
    	logger.info("websocket连接握手成功");
    }

}
