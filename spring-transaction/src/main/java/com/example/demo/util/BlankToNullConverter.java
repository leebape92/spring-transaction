package com.example.demo.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

// @Converter : JPA가 엔티티 속성과 DB 컬럼 간의 타입 변환 규칙을 인식하도록 등록하는 JPA 어노테이션
//autoApply = true	해당 타입의 모든 엔티티 필드에 자동 적용
//autoApply = false	직접 지정한 필드에만 적용

// null을 허용하는 컬럼에 빈값이 들어가면 null로 바꿔주기

//@Converter(autoApply = false)
//public class BlankToNullConverter implements AttributeConverter<String, String> {
//
//	// attribute : 입력한 파라미터값이 들어옴
//    @Override
//    public String convertToDatabaseColumn(String attribute) {
//    	System.out.println("attribute :::" + attribute);
//        if (attribute == null) return null;
//        String trimmed = attribute.trim();
//        return trimmed.isEmpty() ? null : trimmed;
//    }
//
//    @Override
//    public String convertToEntityAttribute(String dbData) {
//        return dbData; // DB → Entity는 그대로
//    }
//}
