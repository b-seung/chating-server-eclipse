package com.sseung.chating.database.chating;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class RoomRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<RoomInfo> getAll() {
		return jdbcTemplate.query("select * from room_info", roomInfoRowMapper());
	}
	
//	public List<Integer>
	public int getOrCreate(String[] ids) {
		if (ids.length > 2) return createRoom(ids);
		
		String idList = String.join("', '", ids);
		
		String sql = "select t1.room_id from room_info right join ("
				+ "select room_id, count(*) as count "
				+ "from user_room_info "
				+ "where user_id in ('" + idList +"') "
				+ "group by room_id "
				+ "having(count(room_id) = " + ids.length + ")) as t1 "
			+ "on t1.room_id = room_info.room_id "
			+ "where user_count = count";
		
		try {
			return jdbcTemplate.queryForObject(sql, Integer.class);
		} catch (EmptyResultDataAccessException e) {
			return createRoom(ids);
		}
	}
	
	public int createRoom(String[] ids) {
		String sql = "insert into room_info (user_count) values (" + ids.length + ") returning room_id;";
		int created_room_id = jdbcTemplate.queryForObject(sql, Integer.class);
		
		for (String id : ids) {
			jdbcTemplate.update("insert into user_room_info values (" + created_room_id + ", '" + id + "')");
		}
		
		return created_room_id;
	}
	
	public RowMapper<Integer> roomIdRowMapper() {
		return (rs, rowNum) -> {
			return rs.getInt("room_id");
		};
	}
	
	public RowMapper<RoomInfo> roomInfoRowMapper() {
		return (rs, rowNum) -> {
			return new RoomInfo(rs.getInt("room_id"), rs.getInt("user_count"));
		};
	}
}
