package com.example.demo.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.message.entity.MessageEntity;

public interface MessageRepository extends JpaRepository<MessageEntity, String> {

}
