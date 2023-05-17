package com.sseung.chating.database.chating;

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
@Table(name="room_info")
public class RoomInfo {
	@Id
	@Column
	private int room_id;
	
	@Column
	private int user_count;
	
	@Builder
	public RoomInfo(int id, int count) {
		this.room_id = id;
		this.user_count = count;
	}
}
