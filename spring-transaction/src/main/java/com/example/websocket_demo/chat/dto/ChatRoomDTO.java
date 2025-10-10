package com.example.websocket_demo.chat.dto;

import lombok.Getter;
import lombok.Setter;



/**
 * 채팅방 조회용
 */
@Getter
@Setter
public class ChatRoomDTO {
    private String roomId;
    private String roomName;
}