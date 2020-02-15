package com.surpass.vision.server;

import org.springframework.stereotype.Component;

//@Component
//public class STOMPConnectEventListener implements ApplicationListener<SessionConnectEvent> {

//    @Override
//    public void onApplicationEvent(SessionConnectEvent event) {
//        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
//        //判断客户端的连接状态
//        switch (sha.getCommand()) {
//            case CONNECT:
//                System.out.println("上线");
//                break;
//            case DISCONNECT:
//                System.out.println("下线");
//                break;
//            case SUBSCRIBE:
//                System.out.println("订阅");
//                break;
//            case SEND:
//                System.out.println("发送");
//                break;
//            case UNSUBSCRIBE:
//                System.out.println("取消订阅");
//                break;
//            default:
//                break;
//        }
//    }
//}