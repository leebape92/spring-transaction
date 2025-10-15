package com.example.demo.user.dto;

import com.example.demo.user.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private String password;
    	
    
    // 여러개가 생길건데 static으로 만들지 확인해보기
    
    // 엔티티객체 -> DTO 변환
    public static UserDTO fromUserEntity(UserEntity userEntity) {
    	UserDTO userDTO = new UserDTO();
    	userDTO.setId(userEntity.getId());
    	userDTO.setUsername(userEntity.getUsername());
    	userDTO.setPassword(userEntity.getPassword());
        return userDTO;
    }
}