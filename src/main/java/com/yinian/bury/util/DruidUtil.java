package com.yinian.bury.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DruidUtil {
	private static DataSource dataS = null;
	public static DataSource getInstance() {
		if(dataS == null) {
			dataS = getDataSource();
		}
		return dataS;
	}
	
	private static DataSource getDataSource() {
		DataSource dataSource  = null;
	    try {
	        //使用配置文件来配置文件
	        Properties pr = new Properties();
	        pr.put("driverClassName", "com.mysql.jdbc.Driver");
	        pr.put("url", "jdbc:mysql://120.77.224.114:3306/yinian_count?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&failOverReadOnly=false");
	        pr.put("username", "root");
	        pr.put("password", "YiNian1130");
//	        pr.put("initialSize", "3");
//	        pr.put("maxActive", "20");
//	        pr.put("minIdle", "3");
//	        pr.put("maxWait", "60000");
//	        pr.put("poolPreparedStatements", "true");
//	        pr.put("maxPoolPreparedStatementPerConnectionSize", "33");
//	        pr.put("timeBetweenEvictionRunsMillis", "60000");
//	        pr.put("minEvictableIdleTimeMillis", "25200000");
	        pr.put("validationQuery", "SELECT 1");
//	        pr.put("testWhileIdle", "true");
	        pr.put("testOnBorrow", "true");
	        pr.put("testOnReturn", "true");
//	        pr.put("removeAbandoned", "true");
//	        pr.put("removeAbandonedTimeout", "1800");
//	        pr.put("poolPreparedStatements", "false");
//	        pr.put("logAbandoned", "true");
//	        pr.put("maxPoolPreparedStatementPerConnectionSize", "200");
	        dataSource = DruidDataSourceFactory.createDataSource(pr);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }finally {
	    	
	    }
	    return dataSource;
}
	
	public static void main(String[] args) {
		try {
			System.out.println(DruidUtil.getInstance().getConnection());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
