package com.example.demo.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.config.exception.BizException;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.entity.UserEntity;
import com.example.demo.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
	public List<UserEntity> findUserList() {
		List<UserEntity> findUserList = userRepository.findAll();
		
//	    if (findUserList.isEmpty()) {
//	        // ⚠️ 직접 BizException 던지기
////	        throw new BizException("NO_USER_DATA", "등록된 유저가 없습니다.");
//	    	throw new BizException("CODE0001");
//	    }
	    
//	    System.out.println("111111111111");
		
//        UserEntity user = new UserEntity();
//        user.setUsername("testUser3");
//        user.setPassword("1234");
//		
//		userRepository.save(user); // ⚠️ 주석 처리 가능
		
		return findUserList;
	}
	
    public void saveUser(UserDTO userDTO) {
    	UserEntity userEntity = new UserEntity();
    	userEntity.setUsername(userDTO.getUsername());
    	userEntity.setPassword(userDTO.getPassword());
        userRepository.save(userEntity);
    }
	

}
