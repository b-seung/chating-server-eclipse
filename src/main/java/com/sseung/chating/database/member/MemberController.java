package com.sseung.chating.database.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public boolean login(@RequestParam String id, @RequestParam String password) {
    	List<Member> members = memberRepository.getAllMember();
    	
    	for (Member member : members) {
    		if (member.getId().equals(id) && member.getPassword().equals(password)) {
    			return true;
    		}
    	}
    	
    	return false;
    }
}