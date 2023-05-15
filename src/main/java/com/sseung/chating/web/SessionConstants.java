package com.sseung.chating.web;

import com.sseung.chating.database.member.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

//참고 링크 : https://atoz-develop.tistory.com/entry/%EC%8A%A4%ED%94%84%EB%A7%81-%EB%B6%80%ED%8A%B8%EC%97%90%EC%84%9C-%EC%BF%A0%ED%82%A4-%EC%84%B8%EC%85%98%EC%9D%84-%EC%82%AC%EC%9A%A9%ED%95%9C-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0
//객체 생성 안하려고 interface로 선언
public interface SessionConstants {
	String LOGIN_MEMBER = "loginMember";
	
	public static Member getMember(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		if (session != null) return (Member) session.getAttribute(SessionConstants.LOGIN_MEMBER);
		
		return null;
	}
}
