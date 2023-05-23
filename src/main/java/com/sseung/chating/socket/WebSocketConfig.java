package com.sseung.chating.socket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket //웹소켓 활성화
public class WebSocketConfig implements WebSocketConfigurer{
	
	private WebSocketHandler handler = new WebSocketHandler();
		
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(handler, "/ws/chat").setAllowedOrigins("http://localhost:8080").withSockJS();
	}
	
}
