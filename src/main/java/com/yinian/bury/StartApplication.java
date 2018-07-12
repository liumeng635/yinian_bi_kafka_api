package com.yinian.bury;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RestController;
import com.yinian.bury.service.CountOperationService;
import com.yinian.bury.util.SpringContextUtils;
@EnableEurekaClient
@SpringBootApplication
@RestController
@EnableAutoConfiguration
@MapperScan("com.yinian.bury.model")
public class StartApplication {
	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class, args);
//		processMessage("countOperation", CountOperation.class);
	}
	
	/**
	 * @Function: StartApplication.java
	 * @Description: kafka消费埋点数据(此种消费方式废弃 代以监听方式来实时消费kafka数据)
	 * @param:描述1描述
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @version: v1.0.0
	 * @author: 刘猛  
	 * @date: 2018年7月11日 下午3:12:55
	 */
	@Deprecated
	public static void processMessage(String topic,Class<?> clazz) {
		CountOperationService service = (CountOperationService) SpringContextUtils.getBean(CountOperationService.class);
		service.processBuryData(topic,clazz);
	}
}