package com.example.demo.request.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.request.dto.RequestDTO;
import com.example.demo.request.dto.ResponseDTO;
import com.example.demo.request.service.RequestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/request")
@RequiredArgsConstructor
public class RequestController {

	private final RequestService requestService;
	
    @PostMapping("/test")
    public ResponseEntity<?> tes(@RequestBody RequestDTO requestDTO) {

    	ResponseDTO response = requestService.callExternalApi(requestDTO);

        return ResponseEntity.ok(response);
    }
}
