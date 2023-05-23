package com.sseung.chating.socket;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.socket.WebSocketSession;

public class ChatRoomRepository {
	
	Map<Integer, ChatRoom> chatRoomMap = new HashMap<>();;
	public static Collection<ChatRoom> chatRooms;
	
	public ChatRoomRepository() {
		System.out.println("repository reset");
		chatRooms = chatRoomMap.values();
	}
	
	public ChatRoom getChatRoom(int roomId) {		
		ChatRoom room = chatRoomMap.get(roomId);
		if (room == null) {
			room = new ChatRoom(roomId);
			chatRoomMap.put(roomId, room);
		}
		
		chatRooms = chatRoomMap.values();
		return room;
	}
	
	public void remove(WebSocketSession session) {
        chatRooms.parallelStream().forEach(chatRoom -> chatRoom.remove(session));
    }
}
