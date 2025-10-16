package com.example.demo.message.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.message.entity.MessageEntity;

public interface MessageRepository extends JpaRepository<MessageEntity, String> {

    @Query("SELECT m FROM MessageEntity m " +
           "WHERE (:messageCode IS NULL OR m.messageCode = :messageCode) " +
           "AND (:messageText IS NULL OR m.messageText = :messageText)")
    List<MessageEntity> findMessageList(
        @Param("messageCode") String messageCode,
        @Param("messageText") String messageText
    );
}