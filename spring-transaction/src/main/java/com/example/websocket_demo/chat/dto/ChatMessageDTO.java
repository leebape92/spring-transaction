package com.example.websocket_demo.chat.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatMessageDTO  {
    private String sender;
    private String content;
    private String roomId;
    private ChatType chatType;
    
    public enum ChatType {
    	CHAT,
    	JOIN,
    	LEAVE,
    	INIT
    }
}
