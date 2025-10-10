//package com.example.websocket_demo.config;
//
//import java.util.Collections;
//
//import org.springframework.aop.Advisor;
//import org.springframework.aop.aspectj.AspectJExpressionPointcut;
//import org.springframework.aop.support.DefaultPointcutAdvisor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.TransactionDefinition;
//import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
//import org.springframework.transaction.interceptor.RollbackRuleAttribute;
//import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
//import org.springframework.transaction.interceptor.TransactionInterceptor;
//
//@Configuration
//public class TransactionConfig {
//
////    private final PlatformTransactionManager transactionManager;
////    
////    
//////    역할: 실제 DB 트랜잭션을 관리하는 핵심 객체
//////    트랜잭션 시작 (begin)
//////    커밋 (commit)
//////    롤백 (rollback)
//////    JPA 사용 시 JpaTransactionManager -> spring-boot-starter-data-jpa 의존성이 존재하면 자동으로 등록
//////    JDBC 사용 시 DataSourceTransactionManager
////    public TransactionConfig(PlatformTransactionManager transactionManager) {
////        this.transactionManager = transactionManager;
////    }
////    
////    
//////    역할: “메서드 이름별 트랜잭션 속성”을 매핑하는 객체
//////    내부적으로는 Map<String, TransactionAttribute> 형태로 관리
//////    ex)"get*"으로 시작하는 메서드는 readOnlyTx 속성을 적용
////    /**
////     * 트랜잭션 속성 정의 (메서드 prefix 기반)
////     */
////    @Bean
////    public NameMatchTransactionAttributeSource transactionAttributeSource() {
////        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
////        
////        
//////        역할: 트랜잭션 정책을 실제로 담는 객체
//////        속성:
//////        setReadOnly(true) → 조회용 최적화 (DB에게 읽기 전용임을 알림)
//////        setPropagationBehavior(...) → 전파 정책
//////        PROPAGATION_REQUIRED : 기존 트랜잭션이 있으면 참여, 없으면 새로 생성
//////        setRollbackRules(...) → 롤백 규칙 지정 (예: Exception 발생 시 롤백)
//////        writeTx는 롤백 규칙까지 포함
////        // 조회용 트랜잭션 (readOnly)
////        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
////        readOnlyTx.setReadOnly(true);
////        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
////
////        // 쓰기 트랜잭션 (롤백 포함)
////        RuleBasedTransactionAttribute writeTx = new RuleBasedTransactionAttribute();
////        writeTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
////        writeTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
////
////        // 메서드 prefix별 매핑
////        source.addTransactionalMethod("get*", readOnlyTx);
////        source.addTransactionalMethod("find*", readOnlyTx);
////        source.addTransactionalMethod("save*", writeTx);
////        source.addTransactionalMethod("update*", writeTx);
////        source.addTransactionalMethod("delete*", writeTx);
////
////        return source;
////    }
////
////    
//////    역할: 스프링 AOP의 Advice 역할
//////    서비스 메서드 실행 시:
//////    트랜잭션 시작 (transactionManager.getTransaction)
//////    실제 메서드 실행
//////    성공 시 커밋, 예외 발생 시 롤백
//////    동작 과정
//////    메서드 호출 → TransactionInterceptor intercepts
//////    transactionAttributeSource를 통해 메서드 이름에 맞는 트랜잭션 속성 확인
//////    속성 기반으로 트랜잭션 시작
//////    메서드 완료 → 성공/실패에 따라 commit/rollback
////    /**
////     * TransactionInterceptor 생성
////     */
//    @Bean
//    public TransactionInterceptor transactionInterceptor() {
//        return new TransactionInterceptor(transactionManager, transactionAttributeSource());
//    }
////
////    
////    
//////    Controller 호출
//////    ↓
//////		Service Proxy 호출
//////    ↓
//////	Advisor 체크
//////    ├─ Pointcut 만족 → TransactionInterceptor 실행
//////    │      ├─ JpaTransactionManager로 트랜잭션 시작
//////    │      ├─ Service 메서드 실행
//////    │      ├─ 정상 종료 → commit / 예외 발생 → rollback
//////    └─ Pointcut 미만족 → 메서드 그대로 실행
////    /**
////     * Advisor 설정 (패키지 단위 포인트컷 + 트랜잭션 인터셉터 연결)
////     */
//    @Bean
//    public Advisor transactionAdvisor(TransactionInterceptor interceptor) {
//    	
////    	AspectJExpressionPointcut
////    	어떤 메서드에 AOP를 적용할지 포인트컷(조건) 정의
////    	"execution(* com.example.websocket_demo.user.service..*(..))" → 해당 패키지 및 하위 클래스 모든 메서드 적용
////    	DefaultPointcutAdvisor
////    	Pointcut + Advice(여기서는 TransactionInterceptor) 연결
////    	AOP Proxy가 생성될 때 Advisor를 참고하여 트랜잭션을 적용
////    	동작 과정
////    	스프링이 DefaultPointcutAdvisor 기반으로 Proxy 생성
////    	Proxy를 통해 서비스 메서드 호출 시 Advisor 체크
////    	Pointcut 조건 만족 시 TransactionInterceptor 실행 → 트랜잭션 처리
//        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
//        // com.example.service 패키지 하위 모든 메서드 적용
//        pointcut.setExpression("execution(* com.example.websocket_demo.user.service..*(..))");
//        return new DefaultPointcutAdvisor(pointcut, interceptor);
//    }
//}
//package com;


