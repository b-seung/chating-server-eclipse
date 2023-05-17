package com.sseung.chating.database.chating;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public List<Integer> get(String idList, int listLength) {
		String sql = "select room_id from user_room_info where user_id in ('" + idList + "') group by room_id having(count(room_id) = " + listLength + ");";
		System.out.println(sql);
		return jdbcTemplate.query(sql, roomIdRowMapper());
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
