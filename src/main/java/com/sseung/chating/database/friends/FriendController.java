package com.sseung.chating.database.friends;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sseung.chating.web.SessionConstants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import com.sseung.chating.database.member.Member;

@RestController
@RequestMapping("/friend")
public class FriendController {
	@Autowired
	FriendRepository friendRepository;
	
//	@GetMapping(path = "/temp")
//	public Object get() {
//		HashMap<String, String> temp1 = new HashMap<>();
//		temp1.put("1", "111");
//		temp1.put("2", "222");
//		
//		return "{\"my\": " + temp1+ ", \"you\": " + temp1 + "}";
//	}
	
	@GetMapping
	public Object getFriendList(HttpServletRequest request) {
		Member member = SessionConstants.getMember(request);
		
		if (member == null) return "{\"error\": true}";
		
		String id = member.getId();
		return friendRepository.getMyFriends(id);
	}
	
	@GetMapping(path = "/allFriendList")
	public Object getAddedMe(HttpServletRequest request) {
		Member member = SessionConstants.getMember(request);
		
		if (member == null) return "{\"error\": true}";
		
		String id = member.getId();
		
		List<DoubleFriendInfo> list = friendRepository.getAllMyFriendList(id);
		
		
		return list;
	}

	
//	@PostMapping(path = "/addFriend")
//	public int addFriend(@RequestBody HashMap<String, String> data, HttpServletRequest request) {
//		Member member = SessionConstants.getMember(request);
//		
//		if (member == null) return 0;
//		
//		String my_id = member.getId();
//		String friend_id = data.get("id");
//		return friendRepository.addFriend(my_id, friend_id);
//	}
}
