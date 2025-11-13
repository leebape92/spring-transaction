package com.example.demo.config.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EaiRecvService {
	String interfaceId();
	String serviceId();
	Class<?> reqDataClass();
}
