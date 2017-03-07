package com.st.services.event.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EventUtil {

	private static ApplicationContext context = new ClassPathXmlApplicationContext(
			"META-INF/st-esclient-context.xml");

	public static String convertESResponseToString(Response response)
			throws IOException {
		if (response == null || response.getEntity() == null) {
			return null;
		}

		InputStream in = (InputStream) response.getEntity();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder result = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			result.append(line);
		}

		return result.toString();
	}

	public static WebClient getESWebClient() {

		return (WebClient) context.getBean("esWebClient");

	}

}
