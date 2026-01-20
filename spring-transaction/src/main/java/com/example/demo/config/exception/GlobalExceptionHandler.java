package com.example.demo.config.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.message.service.MessageService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;



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
@RequiredArgsConstructor
public class GlobalExceptionHandler {
	
	private final MessageService messageService;
	
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //오류를 명확하게 클라이언트오류 서버오류를 나누기 위해 
	//HttpStatus.BAD_REQUEST, HttpStatus.INTERNAL_SERVER_ERROR 사용
	//log.error(ex) : 추가이유 NullPointerException 같은 에러가 발생하면 @ExceptionHandler(Exception.class) 안으로 들어오지만
	//에러로그 메세지가 보이지 않아서 개발중에는 보이도록 처리
//    @ExceptionHandler(BizException.class)
//    public ResponseEntity<ErrorResponse> handleBizException(HttpServletRequest req, BizException ex) {
//    	log.error("Unexpected error occurred at URI: {}", req.getRequestURI(), ex);
//    	
//        String errorCode = ex.getErrorCode();
//        System.out.println("errorCode:::" + errorCode);
//        
//        // ✅ MessageDTO 자체를 요청으로 재사용
////        MessageDTO messageDTO = new MessageDTO();
////        messageDTO.setMessageCode(errorCode);
//
////        MessageDTO errorMessage = messageService.findMessage(messageDTO);   
////    	System.out.println("errorMessage ::: " + errorMessage);
//        
//        String errorMessage = messageService.findErrorMessageText(errorCode);
//        
//    	ErrorResponse errorResponse = new ErrorResponse();
//    	errorResponse.setErrorCode(errorCode);
//    	errorResponse.setMessage(errorMessage);
//    	
//        
//        //클라이언트 오류 400 에러
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
//    }
	
    @ExceptionHandler(BizException.class)
    public ResponseEntity<ApiResponse<String>> handleBizException(HttpServletRequest req, BizException ex) {
    	log.error("400 Unexpected error occurred at URI: {}", req.getRequestURI(), ex);
    	
        String errorCode = ex.getErrorCode();
        System.out.println("errorCode:::" + errorCode);
        
        String errorMessage = messageService.findErrorMessageText(errorCode);
        ApiResponse<String> apiResponse = ApiResponse.fail(errorCode, errorMessage);
        System.out.println("apiResponse ::: " + apiResponse);
        
//    	ErrorResponse errorResponse = new ErrorResponse();
//    	errorResponse.setErrorCode(c);
//    	errorResponse.setMessage(errorMessage);
    	
        
        //클라이언트 오류 400 에러
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(HttpServletRequest req, Exception ex) {
    	log.error("500 Unexpected error occurred at URI: {}", req.getRequestURI());
    	log.error("500 Unexpected error occurred at Exception : {}", ex);
//        ErrorResponse errorResponse = new ErrorResponse();
//        errorResponse.setErrorCode("INTERNAL_ERROR");
//        errorResponse.setMessage("예상치 못한 오류가 발생했습니다. 관리자에게 문의하세요.");
        ApiResponse<String> apiResponse = ApiResponse.fail("INTERNAL_ERROR", "예상치 못한 오류가 발생했습니다. 관리자에게 문의하세요.");

        //서버 오류 500 에러
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }
}

