package com.example.demo.config.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.config.exception.BizException;



//* @RestControllerAdvice + @ExceptionHandler의 동작 원리
//
//1.
//클라이언트 요청 → DispatcherServlet 진입
//모든 HTTP 요청은 DispatcherServlet으로 들어옵니다.
//
//2. 
//HandlerMapping → 해당 컨트롤러 메서드 실행
//요청 URL과 매칭되는 컨트롤러 메서드를 찾아 실행합니다.
//
//3.
//컨트롤러 메서드 실행 중 예외 발생
//→ throw new BizException(...) 같은 예외가 터집니다.
//
//4.
//DispatcherServlet이 예외를 잡음
//직접 try/catch 하지 않기 때문에 DispatcherServlet이 대신 잡습니다.
//
//5.
//HandlerExceptionResolver 체인 호출
//Spring은 ExceptionHandlerExceptionResolver, ResponseStatusExceptionResolver, DefaultHandlerExceptionResolver 등 여러 Resolver를 실행하며, 예외를 적절히 처리할 수 있는 곳을 찾습니다.
//
//6.
//ExceptionHandlerExceptionResolver 동작
//이 Resolver는 @ExceptionHandler가 붙은 메서드를 스캔해서,
//현재 발생한 예외 타입(BizException 등)에 맞는 메서드를 찾아 호출합니다.
//
//여기서 @RestControllerAdvice에 등록된 handleBizException()이 선택됩니다.
//
//7.
//handleBizException 실행 → ResponseEntity 반환
//예외 객체(BizException)를 받아 JSON Map으로 변환합니다.
//ResponseEntity.badRequest()를 반환하면, HTTP 응답코드 400 + JSON 바디가 클라이언트에 전달됩니다.

@RestControllerAdvice
public class GlobalExceptionHandler {

    //오류를 명확하게 클라이언트오류 서버오류를 나누기 위해 
	//HttpStatus.BAD_REQUEST, HttpStatus.INTERNAL_SERVER_ERROR 사용
	
    @ExceptionHandler(BizException.class)
    public ResponseEntity<Map<String, String>> handleBizException(BizException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("errorCode", ex.getErrorCode());
        body.put("message", ex.getMessage());
        
        System.out.println("handleBizException body ::: " + body);
        
        //클라이언트 오류 400 에러
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        Map<String, String> body = new HashMap<>();
        body.put("errorCode", "INTERNAL_ERROR");
        body.put("message", ex.getMessage());
        
        System.out.println("handleException body ::: " + body);
        
        //서버 오류 500 에러
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}

