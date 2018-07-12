package com.yinian.bury.kafka.util;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import com.yinian.bury.util.PropertiesUtil;

@Deprecated
public class KafkaProducerUtil {
     private static KafkaProducerUtil instance = null;
     private static Producer<String,String> producer = null;
     private KafkaProducerUtil(){
	        
	 }
     
    public static KafkaProducerUtil getInstance(){
        if(instance == null){
            synchronized(KafkaProducerUtil.class){
                if(instance == null){
                	init();
                	instance = new KafkaProducerUtil();
                }            
            }        
        }
        return instance;
    }
    
    /**
     * 初始化kafka客户端
     */
    private static void init() {
   	  Properties props = new Properties();
      props.put("bootstrap.servers",PropertiesUtil.getDataParam("kafka.bootstrap.servers"));
      props.put("acks", "all");
      props.put("retries", 0);
      props.put("batch.size", 16384);
      props.put("linger.ms", 1);
      props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
      props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
      producer = new KafkaProducer<String,String>(props);
    }
    
    public static void close() {

    }
    
    //生产数据
    public void sendData(String msgName,String jsonString) {
    	  ProducerRecord<String,String> r = new ProducerRecord<String,String>(msgName,jsonString);
          producer.send(r);
          producer.flush();
    }
    
    public static void main(String[] args) {
    	KafkaProducerUtil.getInstance().sendData("countOperation","{test:'liumeng'}");
	}
}
