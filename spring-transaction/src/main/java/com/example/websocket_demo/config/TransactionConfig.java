package com.example.websocket_demo.config;

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

    /**
     * 커스텀 트랜잭션 Advisor
     */
    @Bean
    public Advisor customTransactionAdvisor(
            @Qualifier("customTransactionInterceptor") TransactionInterceptor customTxInterceptor) {

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        // 적용 범위 (user.service 패키지 전체)
        pointcut.setExpression("execution(* com.example.websocket_demo.user.service..*(..))");

        return new DefaultPointcutAdvisor(pointcut, customTxInterceptor);
    }
}