package com.yinian.bury.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.yinian.bury.kafka.util.KafkaProducerUtil;
import com.yinian.bury.service.CountOperationService;
import com.yinian.bury.util.ExceptionHandle;
import com.yinian.bury.util.ResultsHelper;

@RestController
@RequestMapping(value="/count")
@CrossOrigin
public class BuryDataController {
	@Autowired
	CountOperationService countOperationService;
	
	/**
	 * 使用springboot集成kafka的方式来处理
	 * @Function: BuryDataController.java
	 * @Description: 该函数的功能描述
	 * @param:描述1描述
	 * @return：返回结果描述
	 * @throws：异常描述
	 * @version: v1.0.0
	 * @author: 刘猛  
	 * @date: 2018年7月11日 下午3:07:31
	 */
	@RequestMapping(value = "/countOperation",method = RequestMethod.GET,produces="text/plain;charset=UTF-8")
	@ResponseBody
	@CrossOrigin
    public JSONObject produceBuryData(
    		 @RequestParam(value="userId",required=false) String userId
    		,@RequestParam(value="fromUserId",required=false) String fromUserId
    		,@RequestParam(value="createUserId",required=false) String createUserId
    		,@RequestParam(value="groupId",required=false) String groupId
    		,@RequestParam(value="port",required=false) String port
    		,@RequestParam(value="operation",required=false) String operation
    		,@RequestParam(value="remark",required=false) String remark
    		,@RequestParam(value="userLastLoginTime",required=false) String userLastLoginTime
    	){
		try {
			countOperationService.processBuryTransdata(userId, fromUserId, createUserId, groupId, port, operation, remark, userLastLoginTime);
		} catch (Exception e) {
			return ResultsHelper.putResults("500", "","接口调用异常",ExceptionHandle.getErrorInfoFromException(e));
		}
        return ResultsHelper.putResults("200","","请求成功！","");
    }
	
	@Deprecated
	@RequestMapping(value = "/produceBuryDataOld",method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
    public JSONObject produceBuryDataOld(@RequestParam String processData){
		try {
			JSONObject.parseObject(processData);
		} catch (Exception e) {
			return ResultsHelper.putResults("500", "","JSON数据格式不正确",ExceptionHandle.getErrorInfoFromException(e));
		}
		try {
			KafkaProducerUtil.getInstance().sendData("countOperation", processData);
		} catch (Exception e) {
			return ResultsHelper.putResults("500", "","kafka生产数据异常",ExceptionHandle.getErrorInfoFromException(e));
		}
        return ResultsHelper.putResults("200","","请求成功！","");
    }
}

