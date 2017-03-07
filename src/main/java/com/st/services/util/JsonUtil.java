package com.st.services.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

public class JsonUtil {

	private JsonUtil() {

	}

	public static String convertModelToJson(Object object)
			throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();

		setMapperIntrospector(mapper);

		return mapper.writeValueAsString(object);
	}

	private static void setMapperIntrospector(ObjectMapper mapper) {
		AnnotationIntrospector introspector = new JaxbAnnotationIntrospector(
				TypeFactory.defaultInstance());

		mapper.setAnnotationIntrospector(introspector);

		AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
		mapper.setAnnotationIntrospector(AnnotationIntrospector.pair(
				introspector, secondary));
	}

	public static Object convertJsonToModel(String json, Class<?> clz)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		setMapperIntrospector(mapper);

		return mapper.readValue(json, clz);

	}

}
