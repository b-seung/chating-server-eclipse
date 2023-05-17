package com.sseung.chating.database.chating;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
public class RoomController {
	@Autowired
	RoomRepository roomRepository;
	
	@GetMapping
	public Object getAllRoomId() {
		return roomRepository.getAll();
	}
	
	@GetMapping(path = "/getRoomId")
	public Object getRoomId(@RequestParam String[] id) {
		
		return roomRepository.get(String.join("', '", id), id.length);
	}
}
