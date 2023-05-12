package com.sseung.chating.database.friends;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sseung.chating.web.SessionConstants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/friend")
public class FriendController {
	
	@GetMapping
	public List<Friend> getFriendList(HttpServletRequest request) {
		HttpSession session = request.getSession();
	}
}
