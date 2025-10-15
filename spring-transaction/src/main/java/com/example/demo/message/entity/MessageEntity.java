package com.example.demo.message.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	
	//타 테이블과 JOIN 될 가능성이 없으므로 PK값을 messageCode로
	
    @Id
    @Column(name = "message_code", length = 8)
    private String messageCode;

    @Column(name = "message_text", nullable = false, columnDefinition = "TEXT")
    private String messageText;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "use_yn", length = 1)
    private String useYn = "Y";

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
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