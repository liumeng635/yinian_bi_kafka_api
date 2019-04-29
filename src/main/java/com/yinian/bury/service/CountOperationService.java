package com.yinian.bury.service;

import java.text.ParseException;

import com.yinian.bury.model.CountOperation;

public interface CountOperationService {
	int deleteByPrimaryKey(String id);

    int insert(CountOperation record);

    int insertSelective(CountOperation record);

    CountOperation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CountOperation record);

    int updateByPrimaryKey(CountOperation record);
    
    @Deprecated
    public void processBuryData(String processName,Class<?> clz);
    
    public void processBuryTransdata(String userId,String fromUserId,String createUserId,String groupId,String port,String operation,String remark,String userLastLoginTime,String extroOne,String extroTwo,String extroThree) throws ParseException;
    
    public void fillLackFiled(CountOperation operation) throws Exception;
}
