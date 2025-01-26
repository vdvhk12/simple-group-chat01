package com.chat.backend.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// 메세지 브로커 설정
		// 클라이언트가 메세지를 받을 경로 (대부분 주제(topic)에 대한 경로)
		registry.enableSimpleBroker("/topic", "/queue");

		// 클라이언트가 서버로 메세지를 보낼 경로 (서버에서 처리할 경로)
		registry.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// 클라이언트가 연결할 엔드포인트 설정
		// 여기서 "/ws/chat" 엔드포인트로 클라이언트가 WebSocket 연결을 시도할 수 있게 된다.
		registry.addEndpoint("ws/chat")	// 연결 엔드포인트
			.setAllowedOriginPatterns("*")	// CORS 설정
			.withSockJS();	// SockJs를 사용하여 WebSocket 연결을 대신할 수 있도록 설정
	}
}
