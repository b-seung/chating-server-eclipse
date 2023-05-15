package com.sseung.chating.database.friends;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//lombok 어노테이션
@Getter
@Setter
@NoArgsConstructor
//JPA 어노테이션
@Entity
@Table(name="friends")
public class Friend {
	@Id
	@Column
	private String userId;
	
	@Id
	@Column
	private String friendId;
	
	@Builder
	public Friend(String user_id, String friend_id) {
		this.userId = user_id;
		this.friendId = friend_id;
	}
	
}
