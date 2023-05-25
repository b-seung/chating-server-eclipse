
package com.sseung.chating.database.chating.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Message {
	@Id
	@Column
	private int room_id;
	
	@Column
	private String title;
	
	@Column
	private String content;
	
	@Column
	private String from_id;
	
	@Column
	private Date sended_time;
	
	@Builder
	public Message(int room_id, String title, String content, Date sended_time) {
		this.room_id = room_id;
		this.title = title;
		this.content = content;
		this.sended_time = sended_time;
	}
	
	@Builder
	public Message(int num, int room_id, String from_id, String content, Date sended_time) {
		this.room_id = room_id;
		this.from_id = from_id;
		this.content = content;
		this.sended_time = sended_time;
	}
}

