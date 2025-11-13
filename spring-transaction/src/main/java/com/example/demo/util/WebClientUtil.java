package com.example.demo.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;

public class WebClientUtil {
	
	//POST 요청
	
	// static 선언 비선언 차이점 확인
	public static String call(String reqUrl, String body) {
		StringBuilder response = new StringBuilder();
		
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setRequestProperty("Accept", "application/json");
			conn.setDoOutput(true);
			conn.setConnectTimeout(1000 * 5);
			conn.setReadTimeout(1000 * 60 * 5);
			
			try (OutputStream os = conn.getOutputStream()) {
				byte[] input = body.getBytes("UTF-8");
				os.write(input, 0, input.length);
			}
			
			int responseCde = conn.getResponseCode();
			
			try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
				String line;
				while ((line = in.readLine()) != null) {
					response.append(line);
				}
			}
			
			conn.disconnect();
			
		} catch(Exception e) {
			System.out.println("e" + e);
		}
		
		return response.toString();
	}
}
