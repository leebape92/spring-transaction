package com.example.demo.message.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.config.exception.BizException;
import com.example.demo.message.dto.MessageDTO;
import com.example.demo.message.entity.MessageEntity;
import com.example.demo.message.repository.MessageRepository;
import com.example.demo.util.CommonUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    
//    public MessageDTO findMessage(MessageDTO messageDTO) {
//    	
//    	System.out.println("messageDTO:::" + messageDTO);
//    	
//    	String messageCode = messageDTO.getMessageCode();
//    	
//    	// 붙이고 안붙이고 차이 더 불필요한 코드 없어짐 가독성 좋게 표현 가능한듯
////    	Optional<MessageEntity> findByMessageEntity = messageRepository.findById(messageCode)
////    	MessageEntity messageEntity = findByMessageEntity.get();
//    	// 비지니스이셉션 재호출... 고려
//    	MessageEntity findByMessageEntity = messageRepository.findById(messageCode)
//    			// 메세지 코드가 없는 경우 비지이셉션에서 제대로 처리하지 못함으로 해당 코드 입력
//    			// 근데 왜 500 에러로 떨어지지? 비지이셉션에서 처리하였지만 그 내부에서 또 예외가 발생하였기 대문에
//    			// ExceptionHandlerExceptionResolver 발생 하였음  처리필요 
//    			.orElseThrow(() -> new BizException("MSG_NOT_FOUND", "해당 메시지 코드가 존재하지 않습니다."));
//  
//    	
//    	return MessageDTO.fromMessageEntity(findByMessageEntity);
//    }
    
    public List<MessageDTO> findMessageList(MessageDTO messageDTO) {
    	
        List<MessageEntity> findMessageEntityList = messageRepository.findMessageList(
        		CommonUtils.emptyToNull(messageDTO.getMessageCode()),
        		CommonUtils.emptyToNull(messageDTO.getMessageText())
        );
        
        List<MessageDTO> result = new ArrayList<>();
        for (MessageEntity findMessageEntity : findMessageEntityList) {
            MessageDTO data = MessageDTO.fromMessageEntity(findMessageEntity);
            result.add(data);
        }
        return result;
    }
    
    public MessageDTO saveMessage(MessageDTO messageDTO) {
    	
        // DB 조회 및 검증
        Optional<MessageEntity> optionalMessage = messageRepository.findByMessageCode(messageDTO.getMessageCode());
        
        MessageEntity messageEntity;

        if (optionalMessage.isPresent()) {
            // 값이 있으면 기존 엔티티 가져와서 수정
            messageEntity = optionalMessage.get();
            messageEntity.setMessageText(messageDTO.getMessageText());
            messageEntity.setUseYn(messageDTO.getUseYn());
        } else {
            // 값이 없으면 새 엔티티 생성 후 저장
            messageEntity = new MessageEntity();
            
            messageEntity.setMessageCode(messageDTO.getMessageCode());
            messageEntity.setMessageText(messageDTO.getMessageText());
            messageEntity.setUseYn(messageDTO.getUseYn());
        }
        
        // 저장
        messageRepository.save(messageEntity);

        return messageDTO;
    }


    public List<MessageDTO> deleteMessages(List<MessageDTO> messageDTOList) {
        List<MessageDTO> deletedList = new ArrayList<>();
        
        for (MessageDTO messageDTO : messageDTOList) {
            System.out.println("messageDTO:::" + messageDTO);

            // 메시지 존재 여부 확인
            MessageEntity messageEntity = messageRepository.findByMessageCode(messageDTO.getMessageCode())
                    .orElseThrow(() -> new BizException("TEST0004")); // 존재하지 않는 메시지 코드 예외

            // useYn을 'N'으로 변경
            messageEntity.setUseYn("N");

            // 저장
            MessageEntity savedEntity = messageRepository.save(messageEntity);

            // 결과 DTO로 변환하여 리스트에 추가
            deletedList.add(MessageDTO.fromMessageEntity(savedEntity));
        }

        return deletedList;
    }
    
    
    /**
     * 예외 처리 시 메시지 코드에 해당하는 메시지 텍스트만 안전하게 조회합니다.
     * 메시지가 없을 경우 기본 오류 메시지를 반환합니다.
     */
    public String findErrorMessageText(String errorCode) {
    	
    	// Optional 자바8부터도입
    	// null값을 명시적이고 안전하게 처리 가능
    	// NullPointerException 발생 가능성을 줄이고, null체크 코드 대체 가능
    	
    	// optional 안보이는데? 어떻게 사용되고있는걸까
    	// JpaRepository는 내부적으로 CrudRepository를 상속 > CrudRepository findByMessageCode의 리턴값이 optional
//    	.map(...)	Optional이 있을 때 안의 값을 꺼내는 방식
//    	.orElse(...)	Optional이 비었을 때 기본값 지정
    	
        String message = messageRepository.findByMessageCode(errorCode)
            .map(MessageEntity::getMessageText)
            .orElse("정의되지 않은 메세지 입니다.");
        return message;
        
    }

}
