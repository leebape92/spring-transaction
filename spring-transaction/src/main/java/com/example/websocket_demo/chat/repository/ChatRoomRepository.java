package com.example.websocket_demo.chat.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.websocket_demo.chat.model.ChatRoom;

@Repository
public class ChatRoomRepository {
    private final Map<String, ChatRoom> roomMap = new LinkedHashMap<>();

    public List<ChatRoom> findAllRooms() {
        return new ArrayList<>(roomMap.values());
    }

    public ChatRoom findRoomById(String roomId) {
        return roomMap.get(roomId);
    }

    public ChatRoom createRoom(String name) {
        ChatRoom room = ChatRoom.create(name);
        roomMap.put(room.getRoomId(), room);
        return room;
    }
}
