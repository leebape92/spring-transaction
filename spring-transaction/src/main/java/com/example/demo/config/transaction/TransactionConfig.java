package com.example.demo.config.transaction;

import java.security.Provider.Service;
import java.util.HashMap;
import java.util.Map;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;



@Configuration
public class TransactionConfig {

	
    /**
     * 커스텀 트랜잭션 인터셉터
     */
    @Bean
    public TransactionInterceptor customTransactionInterceptor(PlatformTransactionManager txManager) {
    	
    	// 메서드 이름 패턴과 트랜잭션 속성을 매핑하는 객체입니다.
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

        // 읽기 전용 트랜잭션 속성
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        // 쓰기 트랜잭션 속성
        RuleBasedTransactionAttribute writeTx = new RuleBasedTransactionAttribute();
        writeTx.setReadOnly(false);
        writeTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        // 메서드 패턴 매핑
        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("get*", readOnlyTx);
        txMap.put("find*", readOnlyTx);

        txMap.put("save*", writeTx);
        txMap.put("update*", writeTx);
        txMap.put("delete*", writeTx);

        source.setNameMap(txMap);

        // ⚠️ Spring 6에서는 setter 방식으로 등록해야 함
        TransactionInterceptor interceptor = new TransactionInterceptor();
        interceptor.setTransactionManager(txManager);
        interceptor.setTransactionAttributeSource(source);

        return interceptor;
    }
    
    // 
    // AOP(Aspect Oriented Programming) 관점 지향 프로그래밍
    // 핵심 비지니스 로직과 부가기능을 분리하여 모둘화하는 것
    // 효과 : 코드 중복 감소, 유지보수 향상 등등
    // 애스펙트 (Aspect): 횡단 관심사를 모듈화한 것으로, 부가 기능과 관련된 코드와 로직의 집합입니다. 
//     어드바이스 (Advice): 특정 조인 포인트(Join Point)에서 실행될 로직으로, '어떻게' 부가 기능을 수행할지를 정의합니다. 
//     조인 포인트 (Join Point): 프로그램 실행 중 AOP가 적용될 수 있는 시점입니다. 메서드 호출 등이 대표적입니다.  
    
    
    
    /**
     * Advisor = 어떤 메서드에 어떤 Advice(부가기능)를 적용할지를 결정하는 객체
     * TransactionInterceptor = 트랜잭션을 시작/커밋/롤백하는 로직을 가진 Advice
     * AspectJExpressionPointcut → AOP에서 "어떤 메서드에 부가기능을 적용할지"를 표현
     * Advisor 생성 : return new DefaultPointcutAdvisor(pointcut, customTxInterceptor);
     * -> "어떤 범위(pointcut)에서 어떤 기능(advice)을 실행할지"를 정의한 객체
     */
    @Bean
    public Advisor customTransactionAdvisor(@Qualifier("customTransactionInterceptor") TransactionInterceptor customTxInterceptor) {

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        // 적용 범위 (user.service 패키지 전체)
        pointcut.setExpression("execution(* com.example.demo.user.service..*(..))"); //정상 트랜잭션 처리
//        pointcut.setExpression("@within(" + Service.class.getName() + ")"); //비정상 
        return new DefaultPointcutAdvisor(pointcut, customTxInterceptor);
    }


}