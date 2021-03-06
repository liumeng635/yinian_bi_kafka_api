package com.yinian.bury.util;

import com.alibaba.fastjson.JSONObject;

public class ResultsHelper {
	public final static String ERROR_CODE = "500"; 
	public final static String SUC_CODE = "200";
	
	public static JSONObject putResults(String code,Object data,String errMsg,String exception) {
		JSONObject rsJson = new JSONObject();
		rsJson.put("data", data);
		rsJson.put("code", code);
		rsJson.put("msg", errMsg);
		rsJson.put("exception", exception);
		return rsJson;
	}
}
