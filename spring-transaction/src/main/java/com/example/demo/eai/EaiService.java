package com.example.demo.eai;

import org.springframework.stereotype.Service;

import com.example.demo.config.service.EaiMsg;
import com.example.demo.config.service.EaiRecvService;

@Service
public class EaiService {

	@EaiRecvService (
			interfaceId = "TEST0001",
			serviceId = "SERVICE0001",
			reqDataClass = TestVo.class
	)
	public EaiMsg<ResponseVO> test(EaiMsg<TestVo> testVo) {
		
//		EaiMsg<ResponseVO> resEaiMsg = EaiMsg.<TestVo>builder()
//				.systemHeader
//				.transactionHeader
//				.data
//				.build();
		return null;
	}
	
}
