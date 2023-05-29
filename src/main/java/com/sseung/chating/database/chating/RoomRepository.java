package com.sseung.chating.database.chating;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sseung.chating.database.chating.dto.RoomInfo;
import com.sseung.chating.database.chating.dto.Message;

@Repository
public class RoomRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<RoomInfo> getAll() {
		return jdbcTemplate.query("select * from room_info", roomInfoRowMapper());
	}
	
	public List<Integer> getAllRoomId() {
		String sql = "select room_id from room_info";
		List<Integer> roomIds = jdbcTemplate.query(sql, roomIdRowMapper());
		return roomIds;
	}
	
	public RoomInfo getOrCreate(String myId, String[] ids) {
		if (ids.length > 2) return createRoom(myId, ids);
				
		String idList = String.join("', '", ids);
				
		String sql = "select room_info.room_id, title"
				+ 	" from room_info"
				+ 	"	right join ("
				+ 	"		select room_id, count(*) as count"
				+ 	"		from user_room_info"
				+ 	"		where user_id in ('" + idList + "')"
				+ 	"		group by room_id"
				+ 	"		having count(room_id) = " + ids.length
				+ 	"	) as userRoomInfo"
				+ 	"	on userRoomInfo.room_id = room_info.room_id"
				+ 	"	left join user_room_info"
				+ 	"	on user_room_info.room_id = room_info.room_id"
				+ 	" where user_count = count and user_id = '" + myId + "';";
		
		try {
			return jdbcTemplate.queryForObject(sql, roomTitleRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return createRoom(myId, ids);
		}
	}
	
	public RoomInfo createRoom(String myId, String[] ids) {
		String sql = "insert into room_info (user_count) values (" + ids.length + ") returning room_id;";
		int created_room_id = jdbcTemplate.queryForObject(sql, Integer.class);
		String created_title = "";
		
		for (String id : ids) {
			Object[] list = Arrays.stream(ids).filter(data -> !data.equals(id)).toArray();
			String title = String.join(", ", Arrays.copyOf(list, list.length, String[].class));
			
			if (id.equals(myId)) created_title = title;
			
			jdbcTemplate.update("insert into user_room_info values (" + created_room_id + ", '" + id + "', '" + title + "', '" + LocalDateTime.now() + "')");
		}
		
		return new RoomInfo(created_room_id, created_title);
	}
	
	public List<Message> getList(String id) {
		String sql = "select info.room_id, title, content, send_time"
				+ 	" from user_room_info as info"
				+ 	"	left join messages"
				+ 	"	on info.room_id = messages.room_id"
				+ 	"	left join (select max(num) from messages group by room_id) as msg"
				+ 	"	on messages.num = msg.max"
				+ 	" where user_id = '" + id + "' and (messages.num is null or messages.num = msg.max and send_time >= created)"
				+ 	" order by send_time";
		
		return jdbcTemplate.query(sql, roomMsgMapper());
	}
	
	public List<Message> getMessages(String myId, String roomId) {
		String sql = "select * from messages"
				+ 	" where room_id = " + roomId 
				+ 	" and send_time >= (select created from user_room_info where room_id = " + roomId + " and user_id = '" + myId + "')"
				+ 	" order by send_time asc;";
		return jdbcTemplate.query(sql, messageMapper());
	}
	
	public Object addMessage(String roomId, String fromId, String message, String sendTime) {
		String sql = "insert into messages (room_id, from_id, content, send_time)"
				+ 	" values ('" + roomId
				+ 	"', '" + fromId
				+   "', '" + message
				+   "', '" + sendTime + "');";
		
		return jdbcTemplate.update(sql);
	}
	
	public Object deleteMessage(String myId, String friendId) {
		String sql = "select room_info.room_id"
				+ 	" from room_info"
				+ 	"	right join ("
				+ 	"		select room_id, count(*) as count"
				+ 	"		from user_room_info"
				+ 	"		where user_id in ('" + myId + "', '" + friendId + "')"
				+ 	"		group by room_id"
				+ 	"		having count(room_id) = 2"
				+ 	"	) as userRoomInfo"
				+ 	"	on userRoomInfo.room_id = room_info.room_id"
				+ 	"	left join user_room_info"
				+ 	"	on user_room_info.room_id = room_info.room_id"
				+ 	" where user_count = count and user_id = '" + myId + "';";
		
		int roomId = jdbcTemplate.queryForObject(sql, Integer.class);
		System.out.println(roomId);
		String updateSql = "update user_room_info set created = '" + LocalDateTime.now() + "' where room_id = " + roomId + " and user_id = '" + myId + "'";
		
		return jdbcTemplate.update(updateSql);
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
	
	public RowMapper<RoomInfo> roomTitleRowMapper() {
		return (rs, rowNum) -> {
			return new RoomInfo(rs.getInt("room_id"), rs.getString("title"));
		};
	}
	
	public RowMapper<Message> roomMsgMapper() {
		return (rs, rowNum) -> {
			return new Message(rs.getInt("room_id"), rs.getString("title"), rs.getString("content"), rs.getTimestamp("send_time"));
		};
	}
	
	public RowMapper<Message> messageMapper() {
		return (rs, rowNum) -> {
			return new Message(rs.getInt("num"), rs.getInt("room_id"), rs.getString("from_id"), rs.getString("content"), rs.getTimestamp("send_time"));
		};
	}
}
