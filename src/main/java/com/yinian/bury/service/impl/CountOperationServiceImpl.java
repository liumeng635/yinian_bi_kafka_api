package com.yinian.bury.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yinian.bury.kafka.util.KafkaConsumerUtil;
import com.yinian.bury.model.CountOperation;
import com.yinian.bury.model.CountOperationMapper;
import com.yinian.bury.service.CountOperationService;
import com.yinian.bury.util.UUIDUtil;

@Service
public class CountOperationServiceImpl implements CountOperationService{
	@Autowired
	CountOperationMapper mapper;
	
	@Autowired
    KafkaTemplate<?, String> kafkaTemplate;
	
	private Gson gson = new GsonBuilder().create();

	@Override
	public int deleteByPrimaryKey(String id) {
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(CountOperation record) {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(CountOperation record) {
		return mapper.insertSelective(record);
	}

	@Override
	public CountOperation selectByPrimaryKey(String id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(CountOperation record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CountOperation record) {
		return mapper.updateByPrimaryKey(record);
	}
	
	
	
	@Override
	public void processBuryTransdata(String userId, String fromUserId, String createUserId, String groupId, String port,
			String operation, String remark, String userLastLoginTime) throws ParseException {
		CountOperation bean = new CountOperation();
		bean.setUserId(userId);
		bean.setFromUserId(fromUserId);
		bean.setCreateUserId(createUserId);
		bean.setGroupId(groupId);
		bean.setPort(port);
		bean.setOperation(operation);
		bean.setRemark(remark);
		bean.setUserLastLoginTime(DateUtils.parseDate(userLastLoginTime, "yyyy-MM-dd HH:mm:ss"));
		kafkaTemplate.send("countOperation", gson.toJson(bean));
	}
	
	

	@Override
	public void fillLackFiled(CountOperation operation) throws Exception {
		//查询user注册时间
		Map<String,Object> userMap = mapper.selectUserInfoByUserId(Integer.valueOf(operation.getUserId()));
		String userRegisTime = (String)userMap.get("utime");
		operation.setUserRegisterTime(DateUtils.parseDate(userRegisTime, "yyyy-MM-dd HH:mm:ss"));
		//查询groupNewType
		String groupNewType = null;
		String groupId = operation.getGroupId();
		if(StringUtils.equals("groupId", groupId) || groupId == null){
			return;
		}
		if(groupId.startsWith("cirle_")){//圈子
			groupId = StringUtils.remove("cirle_", groupId);
			groupNewType = mapper.selectCircleType(Integer.valueOf(groupId));
		}else {//相册
			groupNewType = mapper.selectGroupNewType(Integer.valueOf(groupId));
		}
		operation.setGroupNewType(groupNewType);
	}

	@Override
	@Deprecated
	public void processBuryData(String processName,Class<?> clz) {
		KafkaConsumerUtil instance = KafkaConsumerUtil.newInstance(processName);
		List<Object> olist = null;
		while(true){
			try {
				olist = instance.fetchData(clz);//埋点数据
				//入库
				CountOperation bean = null;
				for(Object o : olist) {
					try {
						bean = (CountOperation)o;
						String s = UUIDUtil.get32UUIDStr();
						bean.setId(s);
						bean.setCreateTime(new Date());
						this.insertSelective(bean);//埋点数据入库
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
