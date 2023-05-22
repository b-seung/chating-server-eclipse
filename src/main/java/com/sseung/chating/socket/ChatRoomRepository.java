package com.sseung.chating.socket;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatRoomRepository {
	
	Map<Integer, ChatRoom> chatRoomMap = new HashMap<>();;
	public static Collection<ChatRoom> chatRooms;
	
	public ChatRoomRepository() {
		chatRooms = chatRoomMap.values();
	}
	
	public ChatRoom getChatRoom(int roomId) {
		ChatRoom room = chatRoomMap.getOrDefault(roomId, new ChatRoom(roomId));
		chatRooms = chatRoomMap.values();
		return room;
	}
	
//	public void remove(WebSocketSession session) {
//        this.chatRooms.parallelStream().forEach(chatRoom -> chatRoom.remove(session));
//    }
}
