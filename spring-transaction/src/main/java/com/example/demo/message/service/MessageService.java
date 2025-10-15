package com.example.demo.message.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.config.exception.BizException;
import com.example.demo.message.dto.MessageDTO;
import com.example.demo.message.entity.MessageEntity;
import com.example.demo.message.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageDTO findMessage(MessageDTO messageDTO) {
    	
    	System.out.println("messageDTO:::" + messageDTO);
    	
    	String messageCode = messageDTO.getMessageCode();
    	
    	// 붙이고 안붙이고 차이 더 불필요한 코드 없어짐 가독성 좋게 표현 가능한듯
//    	Optional<MessageEntity> findByMessageEntity = messageRepository.findById(messageCode)
//    	MessageEntity messageEntity = findByMessageEntity.get();
    	 
    	MessageEntity findByMessageEntity = messageRepository.findById(messageCode)
    			// 메세지 코드가 없는 경우 비지이셉션에서 제대로 처리하지 못함으로 해당 코드 입력
    			// 근데 왜 500 에러로 떨어지지? 비지이셉션에서 처리하였지만 그 내부에서 또 예외가 발생하였기 대문에
    			// ExceptionHandlerExceptionResolver 발생 하였음  처리필요 
    			.orElseThrow(() -> new BizException("MSG_NOT_FOUND", "해당 메시지 코드가 존재하지 않습니다."));
    	
    	return MessageDTO.fromMessageEntity(findByMessageEntity);
    	
    }

    public MessageDTO saveMessage(MessageDTO messageDTO) {
    	
    	System.out.println("messageDTO:::" + messageDTO);
    	
        MessageEntity entity = messageRepository.findById(messageDTO.getMessageCode())
                .orElse(new MessageEntity());

        entity.setMessageCode(messageDTO.getMessageCode());
        entity.setMessageText(messageDTO.getMessageText());
        entity.setUseYn(messageDTO.getUseYn());

        return new MessageDTO(
        		messageRepository.save(entity).getMessageCode(),
                entity.getMessageText(),
                entity.getUseYn()
        );
    }

//    public void deleteMessageCode(String code) {
//        repository.deleteById(code);
//    }
}
