package com.example.demo.message.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.exception.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<MessageDTO>>> findMessageList(@ModelAttribute  MessageDTO messageDTO) {
		List<MessageDTO> messageList = messageService.findMessageList(messageDTO);
		return ResponseEntity.ok(ApiResponse.success("정상적으로 조회되었습니다.", messageList));
	}

	// 생성/수정
	@PostMapping("/saveMessage")
	public ResponseEntity<ApiResponse<MessageDTO>> saveMessage(@RequestBody MessageDTO messageDTO) {
		MessageDTO saved = messageService.saveMessage(messageDTO);
		return ResponseEntity.ok(ApiResponse.success("정상적으로 저장하였습니다.", saved));
	}
	
	// 삭제
	@PostMapping("/deleteMessages")
	public ResponseEntity<ApiResponse<List<MessageDTO>>> deleteMessages(@RequestBody List<MessageDTO> messageDTO) {
		List<MessageDTO> deletedList = messageService.deleteMessages(messageDTO);
	    return ResponseEntity.ok(ApiResponse.success("정상적으로 삭제되었습니다.", deletedList));
	}


}
