package com.st.services.util;

import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component("appProperties")
public class AppProperties {

	//@Resource(name = "stAppProperties")
	@Resource(name = "stAppProperties")
	private Properties properties;

	public static String getProperty(String key) {
		//return properties.getProperty(key);
		return null;
	}
	


}
