//package com.example.demo.config.transaction;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.support.TransactionSynchronizationManager;
//
//@Aspect
//@Component
//public class ReadOnlyTxGuardAspect {
//
//    @Before("execution(* com.example.demo.user.repository.*.save*(..)) || " +
//            "execution(* com.example.demo.user.repository.*.delete*(..))")
//    public void checkReadOnly() {
//        // static 메서드 호출
//        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
//            throw new UnsupportedOperationException(
//                    "읽기 전용 트랜잭션에서 쓰기 메서드 호출 감지 → 롤백");
//        }
//    }
//}