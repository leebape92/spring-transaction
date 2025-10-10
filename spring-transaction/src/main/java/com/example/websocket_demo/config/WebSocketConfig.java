package com.example.websocket_demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration //해당 클래스가 스프링 설정 클래스임을 나타냅니다.
@EnableWebSocketMessageBroker //스프링에서 WebSocket 메시지 브로커를 활성화합니다.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	//메시지 브로커 설정 – 클라이언트가 메시지를 어디로 보내고, 어디에서 받을지 지정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue"); // 메시지 받을 경로
        
        //서버가 특정 사용자 개별에게 메시지를 보낼 때 사용하는 prefix입니다. 
        //서버는 @SendToUser("/queue/init") 처럼 지정하면 실제로는 /user/{sessionId}/queue/init로 보내게 됩니다.
        config.setUserDestinationPrefix("/user"); 
        
        //클라이언트가 메시지를 서버의 핸들러 메서드로 보낼 때 붙이는 접두사입니다.
        config.setApplicationDestinationPrefixes("/app"); 
    }
    
    //클라이언트가 웹소켓 서버에 접속할 수 있는 엔드포인트를 정의합니다.
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat") // 웹소켓 연결 엔드포인트
        		.setAllowedOriginPatterns("*")  //CORS 허용
                .withSockJS(); // SockJS 사용 (브라우저 호환성 보완)
    }
	
}
