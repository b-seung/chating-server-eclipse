package com.sseung.chating.database.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.annotation.Async;
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
        List<Member> list = jdbcTemplate.query("select * from member;", memberRowMapper());
        return list;
    }
    
    public List<String> getMembersId() {
    	List<String> list = jdbcTemplate.query("select id from member;", idRowMapper());
    	return list;
    }
    
    public List<Member> getUserInfo(String id) {
    	List<Member> list = jdbcTemplate.query("select * from member where id = '" + id + "'", memberRowMapper());
    	return list;
    }
    
    public Object addMember(Member member) {
    	String sql = "insert into member (id, password, nickname, birthday) values ('" 
    			+ member.getId() + "', '"
    			+ member.getPassword() + "', '"
    			+ member.getNickname() + "', '" 
    			+ member.getBirthday() + "')";
    	
    	return jdbcTemplate.update(sql);
    }

    public boolean checkMemberInfo(String id, String nickname, LocalDate birthday) {
    	String sql = "select id from member where (id, nickname, birthday) = ('"
    					+ id + "', '"
    					+ nickname + "', '"
    					+ birthday + "')";
    	
    	List<String> list = jdbcTemplate.query(sql, idRowMapper());
    	
    	return list.size() == 1 ? true : false;
    }
    
    public Object resetPassword(String id, String password) {
    	String sql = "update member set password = '" + password + "' where id = '" + id + "'";
    	
    	return jdbcTemplate.update(sql);
    }
    
    public Object updateUserInfo(String originId, String id, String nickname, LocalDate birthday) {
    	String sql = "update member set id = '" + id + "', nickname = '" + nickname + "', birthday = '" + birthday + "' where id = '" + originId + "'";
    	
    	return jdbcTemplate.update(sql);
    }

    public Object secessionMember(String id) {
    	String sql = "delete from member where id = '" + id + "';";
    	return jdbcTemplate.update(sql);
    }
    
    public Object deleteData(String id) {
    	int friend = jdbcTemplate.update("delete from friends where user_id = '" + id + "';");
    	int rooms = jdbcTemplate.update("update user_room_info set created = '" + LocalDateTime.now() + "' where user_id = '" + id + "';");
    	
    	return "{\"friend\": " + friend + ", \"rooms\": " + rooms + "}";
    }
    public RowMapper<Member> memberRowMapper() {
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
