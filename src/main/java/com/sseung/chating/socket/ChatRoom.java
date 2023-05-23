package com.sseung.chating.socket;

import lombok.Getter;

import java.util.Set;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Getter
public class ChatRoom {
	private int roomId;
	private Set<WebSocketSession> sessions = new HashSet<>();
	
	public ChatRoom(int roomId) {
		this.roomId = roomId;
	}
	
	public void handleMessage(WebSocketSession session, ChatMessage chatMessage, ObjectMapper objectMapper) throws JsonProcessingException {
		System.out.println("roomId: " + chatMessage.getChatRoomId());
        if (chatMessage.getType().equals("JOIN")) 
            join(session); 
        else if (chatMessage.getType().equals("SEND"))
            send(chatMessage, objectMapper);
        else
        	remove(session);
    }
	
	private void join(WebSocketSession session) {
        sessions.add(session);
        System.out.println("join : " + Arrays.toString(sessions.toArray()));
    }
	
	private <T> void send(T messageObject, ObjectMapper objectMapper) throws JsonProcessingException {
        TextMessage message = new TextMessage(objectMapper.writeValueAsString(messageObject));
        System.out.println(messageObject);
        System.out.println(message);
        
        sessions.parallelStream().forEach(session -> {
            try {
            	System.out.println(session);
                session.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
	
	public void remove(WebSocketSession target) {
		System.out.println("close : " + target);
		String targetId = target.getId();
	    sessions.removeIf(session -> session.getId().equals(targetId));
	}
}
