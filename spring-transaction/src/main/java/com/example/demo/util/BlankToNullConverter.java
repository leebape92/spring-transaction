package com.example.demo.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

// @Converter : 엔티티 필드 값 ↔ DB 컬럼 값 변환 규칙을 정의하는 인터페이스
//autoApply = true	해당 타입의 모든 엔티티 필드에 자동 적용
//autoApply = false	직접 지정한 필드에만 적용

@Converter(autoApply = false)
public class BlankToNullConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) return null;
        String trimmed = attribute.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData; // DB → Entity는 그대로
    }
}
