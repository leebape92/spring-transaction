package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


public class RestTemplateConfig {

	@Bean
	ApiClient apiClient(RestTemplate restTemplate, @Value("${api.base-url}") String apiBaseUrl, @Value("${api.key}") String apiKey) {
		return new ApiClient(restTemplate, apiBaseUrl, apiKey);
	}
}
