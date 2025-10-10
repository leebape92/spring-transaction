package com.example.websocket_demo.chat.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.example.websocket_demo.chat.dto.ChatMessageDTO;

@Controller
public class ChatController {

//	  index.html_back 예제
	
	
//    @MessageMapping("/chat.addUser")
//    @SendToUser("/queue/init")  // 사용자 개인에게만 응답
//    public ChatMessageDTO addUser(@Payload ChatMessageDTO chatMessageDTO, SimpMessageHeaderAccessor headerAccessor) {
//        // 세션에 사용자 이름 저장
//        headerAccessor.getSessionAttributes().put("username", chatMessageDTO.getSender());
//
//        // 클라이언트에게 초기 상태 메시지 전달 (개인용)
//        chatMessageDTO.setChatType(ChatMessageDTO.ChatType.INIT);
//        chatMessageDTO.setSender(chatMessageDTO.getSender());
//        chatMessageDTO.setContent("안녕하세요, " + chatMessageDTO.getSender() + "님!");
//
//        return chatMessageDTO;
//    }
//
//    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/public")  // 모든 구독자에게 브로드캐스트
//    public ChatMessageDTO sendMessage(@Payload ChatMessageDTO chatMessageDTO) {
//        return chatMessageDTO;
//    }
//
//    @MessageMapping("/chat.join")
//    @SendTo("/topic/public")
//    public ChatMessageDTO joinMessage(@Payload ChatMessageDTO chatMessageDTO) {
//    	chatMessageDTO.setContent(chatMessageDTO.getSender() + "님이 입장했습니다.");
//    	chatMessageDTO.setChatType(ChatMessageDTO.ChatType.JOIN);
//        return chatMessageDTO;
//    }
	
	
	
	//@DestinationVariable이 작동하려면 메시지 경로 변수 이름이 컴파일에 보존되어야 합니다.
	//Java는 기본적으로 컴파일 시 메서드 파라미터 이름(roomId)을 지워버립니다.
	//그래서 Spring이 해당 파라미터에 어떤 값을 넣어야 할지 모르게 됩니다.
	
	//해결방법 @DestinationVariable("roomId") 처럼 명시적으로 이름 지정하기
	
    @MessageMapping("/chat.sendMessage/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ChatMessageDTO sendMessage(@DestinationVariable("roomId") String roomId, @Payload ChatMessageDTO chatMessageDTO) {
        // roomId로 분기 가능, 여기선 그대로 리턴하여 구독자에게 전달
    	
    	System.out.println("chatMessageDTO : " + chatMessageDTO);
        return chatMessageDTO;
    }

    @MessageMapping("/chat.addUser/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ChatMessageDTO addUser(@DestinationVariable("roomId") String roomId,
                           @Payload ChatMessageDTO chatMessageDTO,
                           SimpMessageHeaderAccessor headerAccessor) {
        // WebSocket 세션에 username 저장
        headerAccessor.getSessionAttributes().put("username", chatMessageDTO.getSender());
        return chatMessageDTO;
    }
	
}