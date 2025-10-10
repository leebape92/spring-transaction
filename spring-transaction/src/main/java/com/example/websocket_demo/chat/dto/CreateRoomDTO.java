package com.example.websocket_demo.chat.dto;

import lombok.Getter;
import lombok.Setter;


/**
 * 채팅방 생성 요청 
 */
@Getter 
@Setter
public class CreateRoomDTO {
    private String roomName;
}