package com.sseung.chating.database.chating;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sseung.chating.socket.ChatRoom;
import com.sseung.chating.socket.ChatRoomRepository;
import com.sseung.chating.web.SessionConstants;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/room")
public class RoomController {
	@Autowired
	RoomRepository roomRepository;
	
	@GetMapping
	public Object getAllRoomId() {
		return roomRepository.getAll();
	}
	
	@GetMapping(path = "getRoomId")
	public Object getRoomId(HttpServletRequest request) {
		String id = SessionConstants.getId(request);
		
		if (id == null) return "{\"error\": true}";
		
		return roomRepository.getList(id);
	}
	
	@GetMapping(path = "/clickChat")
	public Object clickChat(@RequestParam String[] id, HttpServletRequest request) {
		String myId = SessionConstants.getId(request);
		String[] newIdList = Arrays.copyOf(id, id.length + 1);
		newIdList[id.length] = myId;
		
		return roomRepository.getOrCreate(newIdList);
	}
	
	@GetMapping(path = "/openChat")
	public Object openChat() {
		Collection<ChatRoom> rooms = ChatRoomRepository.chatRooms;
		
		for (ChatRoom room : rooms) {
			System.out.println(room.getRoomId() + "");
		}
		
		return rooms;
	}
}
