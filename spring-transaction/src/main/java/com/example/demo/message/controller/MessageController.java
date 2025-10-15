package com.example.demo.message.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

	// 조회
	@GetMapping("/findMessage")
    public ResponseEntity<MessageDTO> getMessage(@RequestBody MessageDTO messageDTO) {
		MessageDTO response = messageService.findMessage(messageDTO);
		return ResponseEntity.ok(response);
	}

	// 생성/수정
	@PostMapping("/saveMessage")
	public ResponseEntity<MessageDTO> saveMessage(@RequestBody MessageDTO messageDTO) {
		MessageDTO saved = messageService.saveMessage(messageDTO);
		return ResponseEntity.ok(saved);
	}

}
