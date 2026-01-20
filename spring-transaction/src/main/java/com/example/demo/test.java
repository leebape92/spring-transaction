package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class test {
	public static void main(String[] args) throws JsonProcessingException {
		
		Map<String, Object> userMap = new HashMap<>();
		userMap.put("id", "USER0004");
		userMap.put("name", "손흥민");
		userMap.put("age", 32);
		
		List<UserDto> userList = new ArrayList<>();
		UserDto user1 = new UserDto();
		user1.setId("USER0001");
		user1.setName("박지성");
		user1.setAge(30);

		UserDto user2 = new UserDto();
		user2.setId("USER0002");
		user2.setName("이청용");
		user2.setAge(25);
		
		userList.add(user1);
		userList.add(user2);
		
		UserDto userDTO = new UserDto();
		userDTO.setId("USER0003");
		userDTO.setName("기성용");
		userDTO.setAge(25);
	
		ObjectMapper objectMapper = new ObjectMapper();
		
		// DTO -> JSON
		String dtoToJson = objectMapper.writeValueAsString(userDTO);
		System.out.println("dtoToJson ::: " + dtoToJson);
		
		// JSON -> DTO
		UserDto jsonToDto = objectMapper.readValue(dtoToJson, UserDto.class);
		System.out.println("jsonToDto ::: " + jsonToDto);
		
		// JSON -> MAP
		Map<String, Object> jsonToMap = objectMapper.readValue(dtoToJson, new TypeReference<Map<String, Object>>() {});
		System.out.println("jsonToMap ::: " + jsonToMap);
		
		// Map -> JSON
		String mapToJson = objectMapper.writeValueAsString(userMap);
		System.out.println("mapToJson ::: " + mapToJson);
		
		// Map -> DTO
		UserDto mapToDto = objectMapper.convertValue(userMap, UserDto.class);
		System.out.println("mapToDto ::: " + mapToDto);
		
		// DTO -> Map
		Map<String, Object> dtoToMap = objectMapper.convertValue(mapToDto, new TypeReference<Map<String, Object>>() {});
		System.out.println("dtoToMap ::: " + dtoToMap);
		
		// List DTO -> List JSON
		String listDtoToListJson = objectMapper.writeValueAsString(userList);
		System.out.println("listDtoToJson ::: " + listDtoToListJson);
		
		// List JSON -> List DTO
		// 단일객체 UserDto.class 처럼 List.class 사용불가능한 이유
		// List.class 만 사용하면 컴파일 시점에 제네릭 정보 사라짐
		// Jackson은 List 안의 요소 타입(UserDto) 알 수 없음
		// 그래서 익명 클래스(new TypeReference<>(){}) 통해 Jackson에 타입 정보를 전달
		List<UserDto> listJsonToListDto = objectMapper.readValue(listDtoToListJson, new TypeReference<List<UserDto>>() {});
		System.out.println("listJsonToListDto ::: " + listJsonToListDto);
		
	}
}
