package com.sseung.chating.database.friends;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sseung.chating.web.SessionConstants;

import org.json.JSONArray;
import jakarta.servlet.http.HttpServletRequest;
import com.sseung.chating.database.member.Member;

@RestController
@RequestMapping("/friend")
public class FriendController {
	@Autowired
	FriendRepository friendRepository;
	
	@GetMapping
	public Object getFriendList(HttpServletRequest request) {
		Member member = SessionConstants.getMember(request);
		
		if (member == null) return "{\"error\": true}";
		String id = member.getId();
		
		return friendRepository.getMyFriends(id);
	}
	
	@GetMapping(path = "friendsList")
	public Object getAllList(HttpServletRequest request) {
		Member member = SessionConstants.getMember(request);
		
		if (member == null) return "{\"error\": true}";
		String id = member.getId();
		
		List<DoubleFriendInfo> list = friendRepository.getAllMyFriendList(id);

		ArrayList<FriendInfo> myFriendList = new ArrayList<>();
		ArrayList<FriendInfo> addedList = new ArrayList<>();
		
		for (DoubleFriendInfo info : list) {
			FriendInfo info1 = info.getInfo1();
			FriendInfo info2 = info.getInfo2();
			
			if (info1.getId().equals(id)) myFriendList.add(new FriendInfo(info2.getId(), info2.getNickname()));
			
			else if (info2.getId().equals(id)) addedList.add(new FriendInfo(info1.getId(), info1.getNickname()));
		}

		for (Iterator<FriendInfo> info = addedList.iterator(); info.hasNext();) {
			String addedId = info.next().getId();
			for (FriendInfo myFriendInfo : myFriendList) {
				if (myFriendInfo.getId().equals(addedId)) {
					info.remove();
					break;
				}
			}
		}
		
		return "{ \"myFriendList\": " + new JSONArray(myFriendList) + ", \"addedList\": " + new JSONArray(addedList) + "}";
	}
	
	@PostMapping(path = "addFriend")
	public Object addFriend(@RequestBody HashMap<String, String> data, HttpServletRequest request) {
		Member member = SessionConstants.getMember(request);
		
		if (member == null) return "{\"error\": true}";
		String id = member.getId();
		
		return "{\"sucess\": " + friendRepository.addFriend(id, data.get("id")) + "}";
	}
	
	@PostMapping(path = "deleteFriend")
	public Object deleteFriend(@RequestBody HashMap<String, String> data, HttpServletRequest request) {
		Member member = SessionConstants.getMember(request);
		
		if (member == null) return "{\"error\": true}";
		String id = member.getId();
		
		return "{\"sucess\": " + friendRepository.deleteFriend(id, data.get("id")) + "}";
	}
}
