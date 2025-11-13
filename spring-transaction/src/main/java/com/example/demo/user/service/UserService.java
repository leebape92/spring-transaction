package com.example.demo.user.service;

import java.util.List;
import java.util.stream.Collectors;

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
	
//	public List<UserEntity> findUserList() {
//		List<UserEntity> findUserList = userRepository.findAll();
//		
//	    if (findUserList.isEmpty()) {
//	        // ⚠️ 직접 BizException 던지기
////	        throw new BizException("NO_USER_DATA", "등록된 유저가 없습니다.");
//	    	throw new BizException("CODE0001");
//	    }
//	    
//	    System.out.println("111111111111");
//		
//        UserEntity user = new UserEntity();
//        user.setUsername("testUser3");
//        user.setPassword("1234");
//		
//		userRepository.save(user); // ⚠️ 주석 처리 가능
//		
//		return findUserList;
//	}
	

    public List<UserDTO> findUserList() {
        List<UserEntity> findUserAll = userRepository.findAll();
        
//	    if (!findUserAll.isEmpty()) {
//	    	// ⚠️ 직접 BizException 던지기
//	    	throw new BizException("TEST0001");
//	    }
	    
	    // 이셉션 오류 발생
	    UserEntity user = new UserEntity();
	    user.setUsername("testUser3");
	    user.setPassword("1234");
		userRepository.save(user); // ⚠️ 주석 처리 가능
        
        return findUserAll.stream()
            .map(UserDTO::fromUserEntity) // Entity → DTO 변환
            .collect(Collectors.toList());
    }
	
    public List<UserDTO> findUser() {
        List<UserEntity> findUserAll = userRepository.findAll();
        
        // 비지니스 오류 발생
	    if (findUserAll.isEmpty()) {
	    	// ⚠️ 직접 BizException 던지기
	    	throw new BizException("TEST0001");
	    }
	    
//	    UserEntity user = new UserEntity();
//	    user.setUsername("testUser3");
//	    user.setPassword("1234");
//		userRepository.save(user); // ⚠️ 주석 처리 가능
        
        return findUserAll.stream()
            .map(UserDTO::fromUserEntity) // Entity → DTO 변환
            .collect(Collectors.toList());
    }
    
    public void saveUser(UserDTO userDTO) {
    	UserEntity userEntity = new UserEntity();
    	userEntity.setUsername(userDTO.getUsername());
    	userEntity.setPassword(userDTO.getPassword());
        userRepository.save(userEntity);
    }
	

}
