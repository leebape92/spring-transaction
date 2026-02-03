package com.example.demo.message.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;



@Entity
@Table(name = "tb_message")
@Data
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // nullable = false == Not Null
    @Column(name = "messageCode", nullable = false, length = 8)
    private String messageCode;

    @Column(name = "messageText", nullable = false, length = 255)
    private String messageText;

    @Column(name = "messageDes", columnDefinition = "TEXT")
    private String messageDes;

    @Column(name = "useYn", length = 1)
    private String useYn;

    @Column(name = "createdDate", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "updatedDate")
    private LocalDateTime updatedDate;
    
    // @Transient : 컬럼 생성 무시 및 인서트 및 업데이트 반영 안됨
    @Transient
    LocalDateTime now = LocalDateTime.now();
    
    @PrePersist
    public void prePersist() {
        this.createdDate = now;       // 생성일은 최초 한 번만
        this.updatedDate = now;       // 생성 시에도 업데이트일 같이 기록
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedDate = now;  // 수정 시에만 업데이트 날짜 갱신
    }
}