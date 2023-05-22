
package com.sseung.chating.database.chating.dto;

import java.util.Date;

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
public class RoomMessage {
	@Id
	@Column
	private int room_id;
	
	@Column
	private String title;
	
	@Column
	private String content;
	
	@Column
	private Date sended_time;
	
	@Builder
	public RoomMessage(int room_id, String title, String content, Date sended_time) {
		this.room_id = room_id;
		this.title = title;
		this.content = content;
		this.sended_time = sended_time;
	}
}

