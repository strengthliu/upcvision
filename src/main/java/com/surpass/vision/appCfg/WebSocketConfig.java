package com.surpass.vision.appCfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.surpass.vision.server.AuthHandshakeInterceptor;
import com.surpass.vision.server.MyChannelInterceptor;
import com.surpass.vision.server.PrincipalHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Autowired
	private AuthHandshakeInterceptor authHandshakeInterceptor;

	@Autowired
	private PrincipalHandshakeHandler myHandshakeHandler;

	@Autowired
	private MyChannelInterceptor myChannelInterceptor;

	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}


	/**
	 * 配置了一个简单的消息代理，如果不重载，默认情况下回自动配置一个简单的内存消息代理，用来处理以"/topic"为前缀的消息。这里重载configureMessageBroker()方法，
	 * 消息代理将会处理前缀为"/topic"和"/queue"的消息。
	 * 
	 * @param registry
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
//		// 自定义调度器，用于控制心跳线程
//		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
//		// 线程池线程数，心跳连接开线程
//		taskScheduler.setPoolSize(5);
//		// 线程名前缀
//		taskScheduler.setThreadNamePrefix("websocket-heartbeat-thread-");
//		// 初始化
//		taskScheduler.initialize();

		// config.enableSimpleBroker("/topic");
		// 这句话表示客户单向服务器端发送时的主题上面需要加"/app"作为前缀。
		config.setApplicationDestinationPrefixes("/app");
		// 这句话表示在topic和user这两个域上可以向客户端发消息。
//		config.enableSimpleBroker("/topic", "/user", "/hello").setHeartbeatValue(new long[] { 10000, 10000 }).setTaskScheduler(taskScheduler);
		config.enableSimpleBroker("/topic", "/user", "/hello","aaa");
		// 这句话表示给指定用户发送一对一的主题前缀是"/user"
		//config.setUserDestinationPrefix("/user");

	}

	/**
	 * 将"/socketServer"路径注册为STOMP端点，这个路径与发送和接收消息的目的路径有所不同，这是一个端点，客户端在订阅或发布消息到目的地址前，要连接该端点，
	 * 即用户发送请求url="/applicationName/socketServer"与STOMP server进行连接。之后再转发到订阅url；
	 * PS：端点的作用——客户端在订阅或发布消息到目的地址前，要连接该端点。
	 * 
	 * @param stompEndpointRegistry
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// registry.addEndpoint("/socketServer").withSockJS();
		// registry.addEndpoint("/socketServer").setAllowedOrigins("*").withSockJS();
		//registry.addEndpoint("/stomp-websocket").setAllowedOrigins("*").withSockJS().setHeartbeatTime(10000L);
//		registry.addEndpoint("/socketServer").setAllowedOrigins("*").withSockJS().setHeartbeatTime(10000L);
		registry.addEndpoint("/socketServer").setAllowedOrigins("*").withSockJS().setWebSocketEnabled(false);;
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS().setWebSocketEnabled(false);;

//		registry.addEndpoint("/socketServer").setAllowedOrigins("*").addInterceptors(authHandshakeInterceptor)
//				.withSockJS().setHeartbeatTime(10000L);
//		registry.addEndpoint("/socketServer").setAllowedOrigins("*").addInterceptors(authHandshakeInterceptor)
//				.setHandshakeHandler(myHandshakeHandler).withSockJS().setHeartbeatTime(10000L);

	}

//	@Override
//	public void configureClientInboundChannel(ChannelRegistration registration) {
//		registration.interceptors(myChannelInterceptor);
//	}

//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        // 这里开启sockjs协议支持
//        registry.addHandler(new MyHandler(), "/api/myHandler").setAllowedOrigins("*")
//        .addInterceptors(new HttpSessionHandshakeInterceptor())
//        .withSockJS().setClientLibraryUrl("//cdn.jsdelivr.net/sockjs/1/sockjs.min.js");
//    }
}