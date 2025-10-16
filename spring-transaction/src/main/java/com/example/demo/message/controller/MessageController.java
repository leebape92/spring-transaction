package com.example.demo.message.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.message.dto.MessageDTO;
import com.example.demo.message.service.MessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

	private final MessageService messageService;
	
    // 메시지 목록 페이지로 이동 (단순 이동)
    @GetMapping("/page")
    public String messagePage() {
        return "messages"; // templates/messages/list.html 로 이동
    }

	// 조회
	@GetMapping("/findMessage")
    public ResponseEntity<List<MessageDTO>> findMessageList(@ModelAttribute  MessageDTO messageDTO) {
		System.out.println("messageDTO:::" + messageDTO);
		List<MessageDTO> response = messageService.findMessageList(messageDTO);
		return ResponseEntity.ok(response);
	}

	// 생성/수정
	@PostMapping("/saveMessage")
	public ResponseEntity<MessageDTO> saveMessage(@RequestBody MessageDTO messageDTO) {
		MessageDTO saved = messageService.saveMessage(messageDTO);
		return ResponseEntity.ok(saved);
	}

}
