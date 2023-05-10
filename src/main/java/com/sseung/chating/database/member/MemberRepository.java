package com.sseung.chating.database.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Member> getAllMember() {
        List<Member> list = jdbcTemplate.query("select * from member;", allMemberRowMapper());
        return list;
    }
    
    public List<String> getMembersId() {
    	List<String> list = jdbcTemplate.query("select id from member;", idRowMapper());
    	return list;
    }
    
    public Object addMember(Member member) {
    	String sql = "insert into member (id, password, nickname, birthday) values ('" 
    			+ member.getId() + "', '"
    			+ member.getPassword() + "', '"
    			+ member.getNickname() + "', '" 
    			+ member.getBirthday() + "')";
    	
    	System.out.println(sql);
    	return jdbcTemplate.update(sql);
    }

    public RowMapper<Member> allMemberRowMapper() {
        return (rs, rowNum) -> {
            return new Member(rs.getString("id"), rs.getString("password"),
                    rs.getString("nickname"), rs.getObject("birthday", LocalDate.class));
        };
    }
    
    public RowMapper<String> idRowMapper() {
        return (rs, rowNum) -> {
            return rs.getString("id");
        };
    }
}
