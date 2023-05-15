package com.sseung.chating.database.friends;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoubleFriendInfo {
	private int index;
	private String userId, userNickname, friendId, friendNickname;
	
	@Builder
	public DoubleFriendInfo(int index, String userId, String userNickname, String friendId, String friendNickname) {
		this.index = index;
		this.userId = userId;
		this.userNickname = userNickname;
		this.friendId = friendId;
		this.friendNickname = friendNickname;
	}
}