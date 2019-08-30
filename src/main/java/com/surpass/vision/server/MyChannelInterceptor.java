package com.surpass.vision.server;

import java.security.Principal;
import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MyChannelInterceptor implements ChannelInterceptor{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();

        //用户已经断开连接
        if(StompCommand.DISCONNECT.equals(command)){
        	// 从订阅中去掉一个
        	
            String user = "";
            Principal principal = accessor.getUser();
            if(principal != null && StringUtils.isEmpty(principal.getName())){
                user = principal.getName();
            }else{
                user = accessor.getSessionId();
            }

            logger.debug(MessageFormat.format("用户{0}的WebSocket连接已经断开", user));
        }
    }

}
