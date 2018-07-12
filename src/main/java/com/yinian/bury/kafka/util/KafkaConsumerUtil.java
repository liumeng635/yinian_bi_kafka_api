package com.yinian.bury.kafka.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import com.alibaba.fastjson.JSONObject;
import com.yinian.bury.util.ClassUtil;
import com.yinian.bury.util.PropertiesUtil;

@Deprecated
public class KafkaConsumerUtil {
     private static Consumer<String,String> consumer = null;
     private KafkaConsumerUtil(){
	        
	 }
     
    public static KafkaConsumerUtil newInstance(String msgName){
    	init(msgName);
        return new KafkaConsumerUtil();
    }
    
    /**
     * 初始化kafka客户端
     */
    private static void init(String msgName) {
   	  Properties props = new Properties();
      props.put("bootstrap.servers",PropertiesUtil.getDataParam("kafka.bootstrap.servers"));
      props.put("group.id", "bury_data");
      props.put("enable.auto.commit", "true");
      props.put("auto.commit.interval.ms", "500");
      props.put("session.timeout.ms", "30000");
      props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
      props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
      consumer = new KafkaConsumer<String,String>(props);
      consumer.subscribe(Arrays.asList(msgName));
    }
    
    /**
     * 接收数据
     */
    public List<Object> fetchData(Class<?> clz){
    	List<Map<String,String>> fs = ClassUtil.getClazzFileds(clz);
    	List<Object> beanList = null;
    	try {
			beanList = new ArrayList<Object>();
			ConsumerRecords<String,String> records = consumer.poll(10);
			 JSONObject json = null;
			 Object o = null;
			 String fName = null;
			 String type = null;
			 for(ConsumerRecord<String,String> record : records){
				 System.out.println(record.value());
				 json = (JSONObject)JSONObject.parse(record.value());
				 try {
					o = clz.newInstance();
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}//实例化对象
				 for(Map<String,String> fMap : fs){//为对象设置值
					 fName = fMap.get("name");//字段名
					 type = fMap.get("type");//值类型
					 try {
						ClassUtil.setFiledValueByType(o, type, fName,json.get(fName));
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				 }
				 beanList.add(o);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return beanList;
    }
}
