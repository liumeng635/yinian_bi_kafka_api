package com.yinian.bury.model;

import java.util.Map;

public interface CountOperationMapper {
    int deleteByPrimaryKey(String id);

    int insert(CountOperation record);

    int insertSelective(CountOperation record);

    CountOperation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CountOperation record);

    int updateByPrimaryKey(CountOperation record);
    
    Map<String,Object> selectUserInfoByUserId(Integer userId);
    
    String selectCircleType(Integer groupId);
    
    String selectGroupNewType(Integer groupId);
}