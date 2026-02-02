package com.example.demo.config.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String code;       // 성공 코드 (예: "SUCCESS")zz
    private String message;    // 성공 메시지 (예: "메시지가 저장되었습니다")
    private T data;            // 실제 반환 데이터 (DTO, null 등)

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>("SUCCESS", message, data);
    }

    public static <T> ApiResponse<T> fail(String code, String message) {
        return new ApiResponse<>(code, message, null);
    }

}
