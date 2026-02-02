package com.example.demo.message.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            emptyToNull(messageDTO.getMessageCode()),
            emptyToNull(messageDTO.getMessageText())
        );
        
//          1.
//        findMessageEntityList JPA에서 가져온 List<MessageEntity>입니다.
//        이걸 Java Stream으로 바꿉니다.
//        Stream은 데이터를 하나씩 꺼내서 연산할 수 있는 데이터 흐름 처리 API예요.
        
//        2.
//        map()은 Stream의 각 원소를 다른 값으로 변환(transform) 하는 함수예요.
//        MessageEntity → MessageDTO로 바꿔주는 작업입니다.
//        MessageDTO::fromMessageEntity는 아래와 같은 메서드를 의미해요:
        
//        3.변환된 결과들을 다시 List<MessageDTO>로 모읍니다.

//		Stream은 일종의 데이터 흐름이라 끝에 collect()를 써서 리스트로 반환해야 실제 데이터가 됩니다.
        
        
        List<MessageDTO> result = new ArrayList<>();
        for (MessageEntity findMessageEntity : findMessageEntityList) {
            MessageDTO data = MessageDTO.fromMessageEntity(findMessageEntity);
            result.add(data);
        }
        return result;
        
        //람다식
//        List<MessageDTO> result = entities.stream()
//                .map(MessageDTO::fromMessageEntity)
//                .collect(Collectors.toList());
//        return result;
        
//        return findMessageEntityList.stream()
//                       .map(MessageDTO::fromMessageEntity)
//                       .collect(Collectors.toList());
    }
    
//    공통유틸파일 생성 필요
    private String emptyToNull(String value) {
        return (value == null || value.trim().isEmpty()) ? null : value;
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
    	// JpaRepository는 내부적으로 CrudRepository를 상속 > CrudRepository findById의 리턴값이 optional
//    	.map(...)	Optional이 있을 때 안의 값을 꺼내는 방식
//    	.orElse(...)	Optional이 비었을 때 기본값 지정
        String message = messageRepository.findById(errorCode)
            .map(MessageEntity::getMessageText)
            .orElse("정의되지 않은 메세지 입니다.");
        return message;
    }
    
//    optional 미사용
//    public String findErrorMessageText(String errorCode) {
//        MessageEntity entity = messageRepository.findById(errorCode).orElse(null);
//
//        if (entity != null) {
//            return entity.getMessageText();
//        } else {
//            return "정의되지 않은 메세지 입니다.";
//        }
//    }


//    public MessageDTO saveMessage(MessageDTO messageDTO) {
//    	
//    	// 메세지 코드 중복 확인
//        boolean messageExists = messageRepository.existsById(messageDTO.getMessageCode());
//        if(messageExists) {
//        	throw new BizException("TEST0003");	
//        }
//    	
//        MessageEntity entity = messageRepository.findById(messageDTO.getMessageCode())
//                .orElse(new MessageEntity());
//
//        entity.setMessageCode(messageDTO.getMessageCode());
//        entity.setMessageText(messageDTO.getMessageText());
//        entity.setUseYn(messageDTO.getUseYn());
//
//        return new MessageDTO(
//        		messageRepository.save(entity).getMessageCode(),
//                entity.getMessageText(),
//                entity.getUseYn()
//        );
//    }
    
//    public MessageDTO saveMessage(MessageDTO messageDTO) {
//    	
//    	
//    	// 화살표함수
////        MessageEntity messageEntity = messageRepository.findById(messageDTO.getMessageCode())
////                .orElseThrow(() -> new BizException("TEST0003"));
//    	
//    	// Optional<T>는 값이 있을 수도 있고, 없을 수도 있음을 명시적으로 표현하기 위한 Java 8 클래스입니다.
//    	// null 반환으로 인한 NullPointerException 방지
//    	Optional<MessageEntity> optionalMessage = messageRepository.findById(messageDTO.getMessageCode());
//    	System.out.println("optionalMessage ::: " + optionalMessage);
//    	
//    	// optionalMessage.isPresent() 값이 있으면 true
//    	// optionalMessage.isPresent() 값이 없으면 false
//    	if (!optionalMessage.isPresent()) {
//    	    throw new BizException("TEST0003");
//    	}
//    	
//    	// MessageEntity 꺼냄
//    	MessageEntity messageEntity = optionalMessage.get();
//    	
//    	messageEntity.setMessageCode(messageDTO.getMessageCode());
//    	messageEntity.setMessageText(messageDTO.getMessageText());
//    	messageEntity.setUseYn(messageDTO.getUseYn());
//    	
//    	// 저장 로직 실행
//    	messageRepository.save(messageEntity);
//    	
//		return messageDTO;
//    }
    
    // 예외를 던질 필요가 없음
    public MessageDTO saveMessage(MessageDTO messageDTO) {

        // DB 조회 및 검증
        Optional<MessageEntity> optionalMessage = messageRepository.findById(messageDTO.getMessageCode());
        System.out.println("optionalMessage :::" + optionalMessage);
        
        MessageEntity messageEntity;

        if (optionalMessage.isPresent()) {
            // 값이 있으면 기존 엔티티 가져와서 수정
            messageEntity = optionalMessage.get();
            messageEntity.setMessageText(messageDTO.getMessageText());
            messageEntity.setUseYn(messageDTO.getUseYn());
        } else {
            // 값이 없으면 새 엔티티 생성 후 저장
            messageEntity = new MessageEntity();
            
            if(messageDTO.getMessageCode() == null) {
            	throw new BizException("test1111");
            }
            
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
            MessageEntity messageEntity = messageRepository.findById(messageDTO.getMessageCode())
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



}
