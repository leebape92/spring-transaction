package com.example.demo.message.dto;

import com.example.demo.message.entity.MessageEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    private String messageCode;
    private String messageText;
    private String messageDes;
    private String useYn;
    
    // 엔티티객체 -> DTO 변환
    public static MessageDTO fromMessageEntity(MessageEntity messageEntity) {
    	MessageDTO messageDTO = new MessageDTO();
    	messageDTO.setMessageCode(messageEntity.getMessageCode());
    	messageDTO.setMessageText(messageEntity.getMessageText());
    	messageDTO.setMessageDes(messageEntity.getMessageDes());
    	messageDTO.setUseYn(messageEntity.getUseYn());
        return messageDTO;
    }
}