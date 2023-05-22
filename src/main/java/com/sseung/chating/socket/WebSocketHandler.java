package com.sseung.chating.socket;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class WebSocketHandler extends TextWebSocketHandler{
	
	//텍스트 형태의 JSON을 object로 변경해 주거나 object를 텍스트 형태의 JSON으로 변경해 주는 것을 의미
	ObjectMapper objectMapper = new ObjectMapper();
    ChatRoomRepository repository = new ChatRoomRepository();
	
	@Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		System.out.println(session.getId() + "로부터 메시지 수신: " + payload);
		
		ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
		ChatRoom chatRoom = repository.getChatRoom(chatMessage.getChatRoomId());
		chatRoom.handleMessage(session, chatMessage, objectMapper);
	}
	
	@Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("afterConnectionEstablished() 발생");
    }
	
	@Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("afterConnectionClosed() 발생");
    }
}
