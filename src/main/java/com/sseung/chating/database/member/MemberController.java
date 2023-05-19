package com.sseung.chating.database.member;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sseung.chating.web.SessionConstants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    MemberRepository memberRepository;
    
    @GetMapping
    public String member() {
       return "allMember";
	}

    @GetMapping(path = "/all")
    public List<Member> allMember() {
        return memberRepository.getAllMember();
    }
    
//    @PostMapping(path = "/login")
//    public String login(@RequestBody Member member) {
//    	System.out.println("id : " + member.getId());
//    	return member.getId();
//    }
    
    @GetMapping(path = "/login")
    public boolean login(@RequestParam String id, @RequestParam String password, HttpServletRequest request) {
    	List<Member> members = memberRepository.getAllMember();
    	for (Member member : members) {
    		if (member.getId().equals(id) && member.getPassword().equals(password)) {
    			HttpSession session = request.getSession();
    			session.setAttribute(SessionConstants.LOGIN_MEMBER, member);
    			
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    @GetMapping(path = "logout")
    public boolean logout(HttpServletRequest request) {
    	HttpSession session = request.getSession(false);
    	
    	if (session != null) session.removeAttribute(SessionConstants.LOGIN_MEMBER);
    	
    	return true;
    }
    
    @GetMapping(path = "isUsedId")
    public boolean isUsedId(@RequestParam String id) {
    	List<String> memberIdList = memberRepository.getMembersId();
    	
    	if (memberIdList.indexOf(id) == -1) return true;
    	
    	return false;
    }
    
    @GetMapping(path = "searchId")
    public Object searchId(@RequestParam String id, HttpServletRequest request) {
    	List<Member> resultList = memberRepository.getUserInfo(id);
    	Member result = resultList.size() == 0 ? null : resultList.get(0);
    	
    	if (result != null) {
    		return "{\"isMyId\": " + SessionConstants.isMyId(request, id) 
    				+ ", \"id\": \"" + result.getId()
    				+ "\", \"nickname\": \""
    				+ result.getNickname() + "\"}";
    	}
    	
    	return "{\"isMyId\": false, \"id\": null, \"nickname\": null}";
    }
    
    
    @GetMapping(path = "/check")
    public Object check(HttpServletRequest request) {
    	Member member = SessionConstants.getMember(request);
    	
    	if (member == null) return "{\"error\": true}";
    	
		return member;
    }
    
    @PostMapping(path = "/join")
    public Object join(@RequestBody Member member) {
    	Object result = memberRepository.addMember(member);
    	return result;
    }
    
    @PostMapping(path = "/update")
    public boolean update(@RequestBody HashMap<String, String> data, HttpServletRequest request) {
    	String id = data.get("id");
    	String nickname = data.get("nickname");
    	LocalDate birthday = LocalDate.parse(data.get("birthday"), DateTimeFormatter.ISO_DATE);

    	memberRepository.updateUserInfo(data.get("originId"), id, nickname, birthday);
    	    	
    	HttpSession session = request.getSession();
    	session.setAttribute(SessionConstants.LOGIN_MEMBER, memberRepository.getUserInfo(id).get(0));
    	
    	return true;
    }
    
    
    @GetMapping(path = "/passwordLost")
    public boolean lostCheck(@RequestParam String id, @RequestParam String nickname, @RequestParam LocalDate birthday) {
    	return memberRepository.checkMemberInfo(id, nickname, birthday);
    }
    
    @PostMapping(path = "/passwordReset")
    public Object resetPassword(@RequestBody HashMap<String, String> data, HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	session.setAttribute(SessionConstants.LOGIN_MEMBER, memberRepository.getUserInfo(data.get("id")).get(0));
    	return memberRepository.resetPassword(data.get("id"), data.get("password"));
    }
    
    @PostMapping(path = "/secession")
    public Object secessionMember(@RequestBody HashMap<String, String> data, HttpServletRequest request) {
    	Member member = SessionConstants.getMember(request);
    	
    	if (member == null) return "{\"error\": true}";
    	
    	if (member.getPassword().equals(data.get("password"))) {
    		memberRepository.secessionMember(member.getId());
    		return "{\"sucess\": true}";
    	}
    	
    	return "{\"sucess\": false}";
    }

}