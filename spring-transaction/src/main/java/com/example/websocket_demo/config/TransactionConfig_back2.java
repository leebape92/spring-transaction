//package com.example.websocket_demo.config;
//
//
//import java.util.Collections;
//
//import org.springframework.aop.Advisor;
//import org.springframework.aop.aspectj.AspectJExpressionPointcut;
//import org.springframework.aop.support.DefaultPointcutAdvisor;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.TransactionDefinition;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
//import org.springframework.transaction.interceptor.RollbackRuleAttribute;
//import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
//import org.springframework.transaction.interceptor.TransactionInterceptor;
//
//import jakarta.persistence.EntityManagerFactory;
//
//@Configuration
//@EnableTransactionManagement
//public class TransactionConfig {
//
//    // -----------------------
//    // 1️⃣ 트랜잭션 매니저 선택
//    // -----------------------
//    
//    // JPA 환경
//    @Bean
//    public PlatformTransactionManager jpaTransactionManager(EntityManagerFactory emf) {
//        return new JpaTransactionManager(emf);
//    }
//
////    // JDBC 환경
////    @Bean
////    public PlatformTransactionManager jdbcTransactionManager(DataSource ds) {
////        return new DataSourceTransactionManager(ds);
////    }
//
//    // -----------------------
//    // 2️⃣ 메서드 prefix 기반 트랜잭션 속성
//    // -----------------------
//    @Bean("customTransactionAttributeSource")
//    public NameMatchTransactionAttributeSource transactionAttributeSource() {
//        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
//
//        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
//        readOnlyTx.setReadOnly(true);
//        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//
//        RuleBasedTransactionAttribute writeTx = new RuleBasedTransactionAttribute();
//        writeTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        writeTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
//
//        source.addTransactionalMethod("get*", readOnlyTx);
//        source.addTransactionalMethod("find*", readOnlyTx);
//        source.addTransactionalMethod("save*", writeTx);
//        source.addTransactionalMethod("update*", writeTx);
//        source.addTransactionalMethod("delete*", writeTx);
//
//        return source;
//    }
//
//    // -----------------------
//    // 3️⃣ TransactionInterceptor 생성
//    // -----------------------
//    @Bean("customTransactionInterceptor")
//    public TransactionInterceptor transactionInterceptor(
//            PlatformTransactionManager transactionManager,
//            @Qualifier("customTransactionAttributeSource") NameMatchTransactionAttributeSource source) {
//
//        TransactionInterceptor interceptor = new TransactionInterceptor();
//        interceptor.setTransactionManager(transactionManager);
//        interceptor.setTransactionAttributeSource(source);
//        return interceptor;
//    }
//
//    // -----------------------
//    // 4️⃣ Advisor 설정 (패키지 단위 포인트컷)
//    // -----------------------
//    @Bean
//    public Advisor transactionAdvisor(
//            @Qualifier("customTransactionInterceptor") TransactionInterceptor interceptor) {
//
//        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
//        pointcut.setExpression("execution(* com.example.websocket_demo.user.service..*(..))");
//
//        return new DefaultPointcutAdvisor(pointcut, interceptor);
//    }
//}
//
//package com;


