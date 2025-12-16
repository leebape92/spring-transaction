package com.example.demo.config.service;

import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.config.exception.BizException;


// 어플리케이션 실행시 @EaiRecvService 선언된 서비스를 스캔하여 EAI수신 인터페이스 레지스트리에 등록하는 클래스

@Component
public class EaiRecvServiceInitializer implements SmartInitializingSingleton {
	
	@Autowired
	private ApplicationContext applicationcontext;
	
	@Autowired
	private EaiInterfaceRegistry registry;
	
	@Override
	public void afterSingletonsInstantiated() {
		Map<String, Object> serviceBeans = applicationcontext.getBeansWithAnnotation(Service.class);
		
		for(Map.Entry<String, Object> entry : serviceBeans.entrySet()) {
			Object bean = entry.getValue();
			Method[] methods = bean.getClass().getMethods();
			
			for(Method method : methods) {
				EaiRecvService annotation = AnnotationUtils.findAnnotation(method, EaiRecvService.class);
				if(annotation != null) {
					String interfaceId = annotation.interfaceId();
					String serviceId = annotation.serviceId();
					Class<?> reqDataClass = annotation.reqDataClass();
					
					EaiInterfaceRegistry.EaiInterface eaiIntf = registry.getEaiInterface(interfaceId, serviceId);
					if (eaiIntf == null) {
						eaiIntf = new EaiInterfaceRegistry.EaiInterface();
					} else if(eaiIntf.getServiceBean() != null || eaiIntf.getMethod() != null) {
						throw new BizException("Eai Recv Service 중복 inter face id : " + interfaceId + " service id : " + serviceId);
					}
					
					eaiIntf.setServiceBean(bean);
					eaiIntf.setMethod(method);
					eaiIntf.setRequestDataClass(reqDataClass);
					registry.putEaiInterface(interfaceId, serviceId, eaiIntf);
				}
			}
			
		}
		
	}

}
