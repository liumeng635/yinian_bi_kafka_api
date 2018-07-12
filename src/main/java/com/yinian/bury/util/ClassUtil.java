package com.yinian.bury.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class ClassUtil {
	public static List<Map<String,String>> getClazzFileds(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		List<Map<String,String>> rs = new ArrayList<Map<String,String>>();
		Map<String,String> map = null;
		for(Field f : fields) {
			map = new HashMap<String,String>();
			map.put("name",f.getName());
			map.put("type",f.getGenericType().toString());
			rs.add(map);
		}
		return rs;
	}
	
	public static void setFieldValue(Object o,String fieldName,Object value) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		  Field f = o.getClass().getDeclaredField(fieldName);
		  f.setAccessible(true);
		  f.set(o, value);
	}
	
	/**
	 * 单个对象的某个键的值
	 * @param obj  对象
	 * @param key  属性
	 * @return
	 */
	public static Object getValueByKey(Object obj, String key) {
        // 得到类对象
        Class<?> userCla = (Class<?>) obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); // 设置些属性是可以访问的
            try {
            
                if (f.getName().endsWith(key)) {
                    return f.get(obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // 没有查到时返回空字符串
        return "";
    }
	
	/**
	 * 单个对象的所有键值
	 * @param obj 单个对象
	 * @return
	 */
	public static Map<String, Object> getKeyAndValue(Object obj) {
	        Map<String, Object> map = new HashMap<String, Object>();
	        // 得到类对象
	        Class<?> userCla = (Class<?>) obj.getClass();
	        /* 得到类中的所有属性集合 */
	        Field[] fs = userCla.getDeclaredFields();
	        for (int i = 0; i < fs.length; i++) {
	            Field f = fs[i];
	            f.setAccessible(true); // 设置些属性是可以访问的
	            Object val = new Object();
	            try {
	                val = f.get(obj);
	                // 得到此属性的值
	                map.put(f.getName(), val);// 设置键值
	            } catch (IllegalArgumentException e) {
	                e.printStackTrace();
	            } catch (IllegalAccessException e) {
	                e.printStackTrace();
	            }
	        }
	        return map;
	    }
	
	public static void setFiledValueByType(Object o,String type,String fName,Object val) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		if(val == null) {return;}
		if(StringUtils.contains(type, "java.lang.String")) {//存放String类型的值
			 setFieldValue(o, fName, String.valueOf(val));
		 }else if(StringUtils.contains(type, "java.lang.Integer") || StringUtils.equals(type, "int")){//int数值
			 setFieldValue(o, fName, Integer.valueOf(String.valueOf(val)));
		 }else if(StringUtils.contains(type, "java.lang.Boolean") || StringUtils.equals(type, "boolean")){//boolean
			 setFieldValue(o, fName, Boolean.valueOf(String.valueOf(val)));
		 }else if(StringUtils.contains(type, "java.lang.Double") || StringUtils.equals(type, "double")){//double数值
			 setFieldValue(o, fName, Double.valueOf(String.valueOf(val)));
		 }else if(StringUtils.contains(type, "java.lang.Float") || StringUtils.equals(type, "float")){//float数值
			 setFieldValue(o, fName, Float.valueOf(String.valueOf(val)));
		 }else if(StringUtils.contains(type, "java.lang.Long") || StringUtils.equals(type, "long")){//float数值
			 setFieldValue(o, fName, Long.valueOf(String.valueOf(val)));
		 }else if(StringUtils.contains(type, "java.lang.Short") || StringUtils.equals(type, "short")){//float数值
			 setFieldValue(o, fName, Short.valueOf(String.valueOf(val)));
		 }else if(StringUtils.contains(type, "java.util.Date")){//日期类型
			 setFieldValue(o, fName, com.yinian.bury.util.DateUtils.stringToDate(String.valueOf(val)));
		 }else if(StringUtils.contains(type, "java.sql.Timestamp")){//Timestamp类型
			 setFieldValue(o, fName, new Timestamp(Long.valueOf(String.valueOf(val))));
		 }else if(StringUtils.contains(type, "java.math.BigDecimal")){//BigDecimal类型
			 setFieldValue(o, fName, new BigDecimal(String.valueOf(val)));
		 }else if(StringUtils.contains(type, "java.math.BigInteger")){//BigInteger类型
			 setFieldValue(o, fName, new BigInteger(String.valueOf(val)));
		 }
	}
	
	public static void main(String[] args) {
		System.out.println(String.valueOf(null) == null);
		
		/*try {
			Object o = CountOperation.class.newInstance();
			try {
				setFieldValue(o, "userId", "4343534534534");
				System.out.println(getValueByKey(o, "userId"));
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}	
