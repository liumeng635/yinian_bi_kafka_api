package com.yinian.bury.kafkaConfig;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yinian.bury.model.CountOperation;
import com.yinian.bury.service.CountOperationService;
import com.yinian.bury.util.UUIDUtil;

/**
 * 监听kakfa数据消费
 * Copyright: Copyright (c) 2018 liumeng
 * @ClassName: KafkaListeners.java
 * @Description: 该类的功能描述
 * @version: v1.0.0
 * @author: 刘猛  
 * @date: 2018年7月11日 下午3:02:59
 */
public class KafkaListeners {
	private Gson gson = new GsonBuilder().create();
	@Autowired
	CountOperationService countService;
	
	/**
	 * 埋点数据消费
	 * @param content
	 */
    @KafkaListener(topics = {"countOperation"})
    public void processMessage(String content) {
    	try {
			CountOperation bean = gson.fromJson(content, CountOperation.class);
			if(bean.getUserId() == null){return;}
			String s = UUIDUtil.get32UUIDStr();
			bean.setId(s);
			bean.setCreateTime(new Date());
			countService.fillLackFiled(bean);
			//埋点数据入库
			countService.insertSelective(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
