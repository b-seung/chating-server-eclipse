package com.sseung.chating.database.friends;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendInfo {
//	private int index;
//	private String userId, userNickname, friendId, friendNickname;
//	
//	@Builder
//	public FriendInfo(int index, String userId, String userNickname, String friendId, String friendNickname) {
//		this.index = index;
//		this.userId = userId;
//		this.userNickname = userNickname;
//		this.friendId = friendId;
//		this.friendNickname = friendNickname;
//	}
	
	
	private String id, nickname;
	
	@Builder
	public FriendInfo(String id, String nickname) {
		this.id = id;
		this.nickname = nickname;
	}
}

@Getter
@Setter
class DoubleFriendInfo {
	private int index;
	private FriendInfo info1, info2;
	
	@Builder
	public DoubleFriendInfo(int index, FriendInfo info1, FriendInfo info2) {
		this.index = index;
		this.info1 = info1;
		this.info2 = info2;
	}
}