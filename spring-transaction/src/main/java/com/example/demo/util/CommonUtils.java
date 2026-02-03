package com.example.demo.util;

public class CommonUtils {

	// 공통유틸파일 생성 필요
	public static  String emptyToNull(String value) {
		return (value == null || value.trim().isEmpty()) ? null : value;
	}
	
}
