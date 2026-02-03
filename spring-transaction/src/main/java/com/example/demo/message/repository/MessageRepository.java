package com.example.demo.message.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.message.entity.MessageEntity;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

	Optional<MessageEntity> findByMessageCode(String messageCode);
	
    @Query("SELECT m FROM MessageEntity m " +
    		"WHERE m.useYn = 'Y' " +
    		"AND (:messageCode IS NULL OR m.messageCode LIKE %:messageCode%) " +
    		"AND (:messageText IS NULL OR m.messageText LIKE %:messageText%)")
    List<MessageEntity> findMessageList(
        @Param("messageCode") String messageCode,
        @Param("messageText") String messageText
    );
}