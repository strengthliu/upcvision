package com.surpass.vision.server;

import java.security.Principal;
import java.text.MessageFormat;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.domain.User;
import com.surpass.vision.utils.SpringContextUtils;

@Component
public class PrincipalHandshakeHandler  extends DefaultHandshakeHandler{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        System.out.println("determineUser");
    	HttpSession session = SpringContextUtils.getSession();
        User loginUser = (User) session.getAttribute(GlobalConsts.SESSION_USER);
//        request.getRemoteAddress()
        // 先不做用户处理。
        if(true) return new MyPrincipal("");
        if(loginUser != null){
            logger.debug(MessageFormat.format("WebSocket连接开始创建Principal，用户：{0}", loginUser.getName()));
            return new MyPrincipal(loginUser.getName());
        }else{
            logger.error("未登录系统，禁止连接WebSocket");
            return null;
        }
    }

}
