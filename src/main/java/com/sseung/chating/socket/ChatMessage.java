package com.sseung.chating.socket;

import java.util.Date;
import lombok.Getter;

@Getter
public class ChatMessage {
	private int chatRoomId;
	private String message;
	private String type;
	private Date sendTime;
}
