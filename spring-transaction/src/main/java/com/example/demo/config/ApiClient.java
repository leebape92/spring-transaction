package com.example.demo.config;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.config.exception.BizException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApiClient {
	private RestTemplate restTemplate;
	private URI apiBaseUrl;
	private String apiKey;
	// String HEADER_API_KEY = "X-API-KEY";
	// int READ_TIMEOUT = 60000;
	
	private ObjectMapper om = new ObjectMapper();
	
	public ApiClient(RestTemplate restTemplate, String apiBaseUrl, String apiKey) {
		this.restTemplate = restTemplate;
		this.apiBaseUrl = URI.create(apiBaseUrl);
		this.apiKey = apiKey;
	}
	
	public <T> T request(String path, Object reqBody, Class<T> responseType) {
		URI fullUri = apiBaseUrl.resolve(path);
		
		HttpHeaders reqHeaders = new HttpHeaders();
		reqHeaders.set("HEADER_API_KEY", apiKey);
		reqHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Object> request = new HttpEntity<Object>(reqBody, reqHeaders); // 왜 필요할까?
		
		ResponseEntity<String> res = restTemplate.exchange(fullUri, HttpMethod.POST, request, String.class);
		
		HttpStatusCode status = res.getStatusCode();
		int sc = status.value();
		
		String body = res.getBody();
		
		// 비지니스 에러 정의
		if(sc >= 550) {
			String msg = extractMessage(body);
			
			Map<String,Object> map = new HashMap<>();
			map.put("errCode", String.valueOf(sc));
			map.put("errMsg", msg);
			map.put("result", Collections.emptyList());
			
			return om.convertValue(map, responseType);
			
		}
		
		try {
			if(responseType == String.class) {
				return responseType.cast(body);
			} else {
				return (body == null || body.isEmpty()) ? null : om.readValue(body, responseType);
			}
		} catch (Exception e) {
			throw new BizException("API호출 실패");
		}
	}
	
	public String extractMessage(String body) {
		if(body == null) {
			return null;
		}
		
		try {
			JsonNode n = om.readTree(body); // json 파싱
			if(n.hasNonNull("errMsg")) {	
				return n.get("errMsg").asText();
			}
		} catch (Exception e) {
			
		}
		
		return body;
	} 
	
}
