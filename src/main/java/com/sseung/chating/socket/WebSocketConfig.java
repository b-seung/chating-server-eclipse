package com.sseung.chating.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import lombok.RequiredArgsConstructor;

//@Configuration
//@RequiredArgsConstructor //final 멤버들 초기값 설정하는 constructor를 자동생성해주는 어노테이션
//@EnableWebSocket //웹소켓 활성화
public class WebSocketConfig implements WebSocketConfigurer{
//	
//	@Autowired
//	private final WebSocketHandler handler;
		
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//		registry.addHandler(handler, "/chat").setAllowedOrigins("*");
	}
	
}
