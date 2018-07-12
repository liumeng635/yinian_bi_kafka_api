package com.yinian.bury.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;


@Configuration
@EnableAutoConfiguration
@ComponentScan("com.yinian.bury")
public class WebApplicationConfig extends SpringBootServletInitializer{
	/**
	 * 这个是用来支持跨域访问
	 * @return
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/*").allowedOrigins("*");
			}
		};
	}

	/**
	 * 这个用来支持直接返回对象
	 * @return
	 */
	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		// 1、需要先定义一个converter 转换器
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		// 2、添加fastJson 的配置信息，比如：是否要格式化返回的json数据
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		// 3、在convert 中添加配置信息
		fastConverter.setFastJsonConfig(fastJsonConfig);
		// 4、将convert 添加到converters当中
		HttpMessageConverter<?> converter = fastConverter;
		return new HttpMessageConverters(converter);
	}
}
