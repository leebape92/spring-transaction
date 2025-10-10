package com.example.websocket_demo.chat.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.websocket_demo.chat.dto.ChatRoomDTO;
import com.example.websocket_demo.chat.dto.CreateRoomDTO;
import com.example.websocket_demo.chat.model.ChatRoom;
import com.example.websocket_demo.chat.repository.ChatRoomRepository;

@RestController
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomController(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    // 채팅방 목록 조회
    @GetMapping("/rooms")
    public List<ChatRoomDTO> getAllRooms() {
        return chatRoomRepository.findAllRooms().stream().map(room -> {
            ChatRoomDTO dto = new ChatRoomDTO();
            dto.setRoomId(room.getRoomId());
            dto.setRoomName(room.getRoomName());
            return dto;
        }).collect(Collectors.toList());
    }

    // 채팅방 생성
    @PostMapping("/room")
    public ChatRoomDTO createRoom(@RequestBody CreateRoomDTO createRoomDTO) {
        ChatRoom room = chatRoomRepository.createRoom(createRoomDTO.getRoomName());
        System.out.println("room:::" + room);
        ChatRoomDTO dto = new ChatRoomDTO();
        dto.setRoomId(room.getRoomId());
        dto.setRoomName(room.getRoomName());
        return dto;
    }

    // 채팅방 단건 조회
    @GetMapping("/room/{roomId}")
    public ChatRoomDTO getRoom(@PathVariable String roomId) {
        ChatRoom room = chatRoomRepository.findRoomById(roomId);
        ChatRoomDTO dto = new ChatRoomDTO();
        dto.setRoomId(room.getRoomId());
        dto.setRoomName(room.getRoomName());
        return dto;
    }
}