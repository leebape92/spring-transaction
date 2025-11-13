package com.example.demo.config.service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
public class EaiInterfaceRegistry {
	private final Map<ServiceKey, EaiInterface> registry = new HashMap<>();
	
	public EaiInterface getEaiInterface(String interfaceId, String serviceId) {
		return registry.get(new ServiceKey(interfaceId, serviceId));
	}
	
	public void putEaiInterface(String interfaceId, String serviceId, EaiInterface eaiIntf) {
		registry.put(new ServiceKey(interfaceId, serviceId), eaiIntf);
	}
	
	public Map<ServiceKey, EaiInterface> getRegistry() {
		return registry;
	}
	
	@AllArgsConstructor
	@Getter
	public static class ServiceKey {
		private final String interfaceId;
		private final String serviceId;
		
		public boolean equals(Object o) {
			if(!(o instanceof ServiceKey)) return false;
			ServiceKey other = (ServiceKey) o;
			return this.interfaceId.equals(other.interfaceId) && this.serviceId.equals(other.serviceId);
		}
		
		public int hashCode() {
			return interfaceId.hashCode() * 31 + serviceId.hashCode();
		}
	}
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Setter
	@Getter
	public static class EaiInterface {
		private Object serviceBean;
		private Method method;
		private Class<?> requestDataClass;
		private Class<?> responseDataClass;
	}
} 
