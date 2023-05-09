package com.sseung.chating.database.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}