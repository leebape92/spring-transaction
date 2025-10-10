package com.example.websocket_demo.chat.model;

import java.util.UUID;

import lombok.Data;



// @Data = @Getter / @Setter, @ToString, @EqualsAndHashCode와 @RequiredArgsConstructor

//@EqualsAndHashCode는 객체의 동등성 비교를 위한 equals()와 hashCode() 메소드를 자동으로 생성해주고, 
//@RequiredArgsConstructor는 final 또는 @NonNull로 표시된 필드들을 초기화하는 생성자를 자동으로 생성해줍니다. 
@Data
public class ChatRoom {
    private String roomId;
    private String roomName;

    public static ChatRoom create(String name) {
        ChatRoom room = new ChatRoom();
        room.roomId = UUID.randomUUID().toString();
        room.roomName = name;
        return room;
    }

    // Getters & Setters
    public String getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }
}
