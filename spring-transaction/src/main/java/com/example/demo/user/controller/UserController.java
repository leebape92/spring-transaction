package com.example.demo.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.exception.BizException;
import com.example.demo.config.exception.ErrorResponse;
//import com.example.demo.config.exception.ErrorResponse;
import com.example.demo.user.entity.UserEntity;
import com.example.demo.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	
    @PostMapping("/findUserList")
    public ResponseEntity<?> findUserList() {
        ResponseEntity<?> response;
        try {
            List<UserEntity> findUserList = userService.findUserList();
            response = ResponseEntity.ok(findUserList);
        } catch (BizException e) {
            ErrorResponse error = new ErrorResponse(e.getErrorCode(), e.getMessage());
            response = ResponseEntity.badRequest().body(error);
        }
        return response;
    }
	
}
