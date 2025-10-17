package com.example.demo.config.exception;

//RuntimeException 이란?
//자바에서 예외(Exception)는 크게 두 가지:
//Checked Exception : 반드시 try-catch 하거나 throws 선언해야 함 (IOException, SQLException 등)
//Unchecked Exception (RuntimeException 계열) : 명시적으로 처리 안 해도 됨. 컴파일러가 강제하지 않음 (NullPointerException, IllegalArgumentException 등)
//즉, RuntimeException 을 상속한 예외는 개발자가 try-catch로 꼭 잡지 않아도 되는 예외


//보통 스프링/비즈니스 로직에서 비즈니스 규칙 위반 상황을 나타낼 때, Checked Exception 으로 강제하면 코드가 불필요하게 지저분해져요.
//그래서 RuntimeException을 상속해서 커스텀 예외를 정의하는 게 일반적입니다.
public class BizException extends RuntimeException {
    private final String errorCode;

    public BizException(String errorCode) {
        super(errorCode); // super()를 호출하지 않아서, 부모 클래스인 RuntimeException에 메시지가 전달되지 않습니다.
        this.errorCode = errorCode;
    }

    
    public BizException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BizException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}