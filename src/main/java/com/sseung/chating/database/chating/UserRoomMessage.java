
package com.sseung.chating.database.chating;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserRoomMessage {
	@Id
	@Column
	private int room_id;
	
	@Id
	@Column
	private String user_id;
	
	@Column
	private String title;
	
	@Column
	private String message;
	
	@Column
	private Date sended_time;
	
}

