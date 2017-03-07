package com.st.services.es.client;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.common.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.st.services.event.model.Event;
import com.st.services.event.service.EventServiceImpl;
import com.st.services.event.util.EventUtil;
import com.st.services.util.JsonUtil;


@Component("elasticSearchClient")
public class ElasticSearchClient {

	private static Logger LOGGER =LogManager.getLogger(EventServiceImpl.class);

	public String indexEvent(Event event) throws Exception {

		String json = JsonUtil.convertModelToJson(event);

		Response response = EventUtil
				.getESWebClient()
				.path("event_index/" + event.getEventName() + "/"
						+ event.getId()).type(MediaType.APPLICATION_JSON_TYPE)
				.accept(MediaType.APPLICATION_JSON_TYPE).put(json);

		String result = EventUtil.convertESResponseToString(response);

		LOGGER.info("Index Response is: " + result);
		return result;

	}

	public Event getEvent(String eventName, String eventId) throws Exception {

		Response response = EventUtil.getESWebClient()
				.path("event_index/" + eventName + "/" + eventId)
				.type(MediaType.APPLICATION_JSON_TYPE).get();

		String result = EventUtil.convertESResponseToString(response);

		if (StringUtils.isEmpty(result)) {
			return null;
		}

		ObjectMapper m = new ObjectMapper();
		JsonNode rootNode = m.readTree(result);
		JsonNode nameNode = rootNode.path("_source");
		if (nameNode == null) {
			return null;
		}

		return (Event) JsonUtil.convertJsonToModel(nameNode.toString(),
				Event.class);

	}
}
