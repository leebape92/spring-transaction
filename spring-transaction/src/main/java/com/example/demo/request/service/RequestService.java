package com.example.demo.request.service;

import org.springframework.stereotype.Service;

import com.example.demo.config.ApiClient;
import com.example.demo.config.exception.BizException;
import com.example.demo.request.dto.RequestDTO;
import com.example.demo.request.dto.ResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final ApiClient apiClient;

    public ResponseDTO callExternalApi(RequestDTO requestDTO) {

        try {
            return apiClient.request(
                    "/external/endpoint",
                    requestDTO,
                    ResponseDTO.class
            );

        } catch (BizException e) {
        	// 또는 호출한 서비스에 값 보내주기
//        	ResponseDTO res = new ResponseDTO();
//        	return res;
            throw new BizException("외부 API 호출 중 오류 발생: " + e.getMessage());
        }
    }
}
