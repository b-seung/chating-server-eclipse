package com.sseung.chating.database.chating;

import java.awt.SystemColor;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sseung.chating.socket.ChatRoom;
import com.sseung.chating.socket.ChatRoomRepository;
import com.sseung.chating.web.SessionConstants;

import jakarta.servlet.http.HttpServletRequest;

import org.json.JSONArray;

import com.sseung.chating.database.chating.dto.Message;

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
		
		return roomRepository.getOrCreate(myId, newIdList);
	}
	
	@GetMapping(path = "/openChat")
	public Object openChat() {
		Collection<ChatRoom> rooms = ChatRoomRepository.chatRooms;
		
		for (ChatRoom room : rooms) {
			System.out.println(room.getRoomId() + "");
		}
		
		return rooms;
	}
	
	@GetMapping(path = "/getMessages")
	public Object getMessages(@RequestParam String roomId, HttpServletRequest request) {
		String myId = SessionConstants.getId(request);
		if (myId == null) return "{\"error\": true}";
		
		List<Message> messageList = roomRepository.getMessages(myId, roomId);

		return "{\"id\": \"" + myId + "\", \"messages\": " + new JSONArray(messageList) + "}";
	}
	
	@PostMapping(path = "/addMessage")
	public Object addMessage(@RequestBody HashMap<String, String> data) {
		return roomRepository.addMessage(data.get("roomId"), data.get("fromId"), data.get("message"), data.get("sendTime"));
	}
	
	@PostMapping(path = "/deleteMessage")
	public Object deleteMessage(@RequestBody HashMap<String, String> data, HttpServletRequest request) {
		String myId = SessionConstants.getId(request);
		if (myId == null) return "{\"error\": true}";
		
		return "{\"success\": " + roomRepository.deleteMessage(myId, data.get("id")) + "}";
	}
}
