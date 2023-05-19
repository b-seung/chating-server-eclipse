package com.sseung.chating.database.friends;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Repository
public class FriendRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<FriendInfo> getMyFriends(String id) {
		String sql = "select friend_id, nickname"
				+ 	" from friends"
				+ 		" inner join member"
				+ 		" on friends.friend_id = member.id"
				+ 	" where user_id = '" + id + "';";

        return jdbcTemplate.query(sql, idRowMapper());
    }
	
	public List<DoubleFriendInfo> getAllMyFriendList(String id) {
		String sql = "select user_id, (select nickname from member where id = user_id) as user_nickname, "
				+ "friend_id, (select nickname from member where id = friend_id) as friend_nickname "
				+ "from friends "
				+ "where user_id = '" + id + "' or friend_id = '" + id + "'";
		List<DoubleFriendInfo> list = jdbcTemplate.query(sql, infoRowMapper());
		return list;
	}
	
	public int addFriend(String my_id, String friend_id) {
		String sql = "insert into friends values ('" + my_id + "', '" + friend_id + "');";
		return jdbcTemplate.update(sql); 
	}
	
	public int deleteFriend(String my_id, String friend_id) {
		String sql = "delete from friends where user_id = '" + my_id + "' and friend_id = '" + friend_id + "';";
		return jdbcTemplate.update(sql); 
	}
	
	public RowMapper<FriendInfo> idRowMapper() {
		return (rs, rowNum) -> {
			return new FriendInfo(rs.getString("friend_id"), rs.getString("nickname"));
		};
	}
	
	public RowMapper<DoubleFriendInfo> infoRowMapper() {
		return (rs, rowNum) -> {
			return new DoubleFriendInfo(rowNum, 
					new FriendInfo(rs.getString("user_id"), rs.getString("user_nickname")),
					new FriendInfo(rs.getString("friend_id"), rs.getString("friend_nickname"))
			);
		};
	}
	
	public RowMapper<Friend> friendRowMapper() {
		return (rs, rowNum) -> {
			return new Friend(rs.getString("user_id"), rs.getString("friend_id"));
		};
	}
}




