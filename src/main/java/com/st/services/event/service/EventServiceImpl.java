package com.st.services.event.service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.st.services.es.client.ElasticSearchClient;
import com.st.services.event.api.EventService;
import com.st.services.event.model.Event;
import com.st.services.event.model.EventResponse;
import com.st.services.event.model.ResponseStatus;
import com.st.services.event.model.SmartliError;
import com.sun.istack.logging.Logger;

@Service("eventService")
public class EventServiceImpl implements EventService {

	private static Logger LOGGER = Logger.getLogger(EventServiceImpl.class);

	@Autowired
	ElasticSearchClient elasticSearchClient;

	@Override
	public Response createEvent(Event event) {

		try {

			LOGGER.info("create event call payload - " + event);

			elasticSearchClient.indexEvent(event);
			EventResponse response = new EventResponse();
			response.setStatus(ResponseStatus.SUCCESS);
			response.setPayload("Index Created for Event - " + event.getId());

			return Response.ok(response).build();
		} catch (Exception e) {
			LOGGER.severe("Exception Occured while Inexing Event", e);
			SmartliError error = new SmartliError();
			error.setCode("5XX");
			error.setMessage("Exception Occured while Inexing Event"
					+ e.getLocalizedMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(error)
					.build();

		}

	}

	@Override
	public Response getEvent(String eventName, String eventId) {
		try {

			LOGGER.info("get event call params - " + eventName + " :: "
					+ eventId);

			Event event = elasticSearchClient.getEvent(eventName, eventId);

			/*
			 * Event event = new Event(); event.setEventName("Event Name");
			 * event.setEventValue("Event Value"); event.setId("ID");
			 * event.setEventTime(new Date());
			 */
			return Response.ok(event).build();
		} catch (Exception e) {

			LOGGER.severe("Exception Occured while Fetching Event", e);
			SmartliError error = new SmartliError();
			error.setCode("5XX");
			error.setMessage("Exception Occured while Fetching Event - "
					+ e.getLocalizedMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(error)
					.build();

		}

	}

}
