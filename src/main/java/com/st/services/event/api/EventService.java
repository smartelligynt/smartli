package com.st.services.event.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.st.services.event.model.Event;









public interface EventService {
	
	@POST
	@Path("/events")
	@Produces({ "application/xml", "application/json" })
	@Consumes({ "application/xml", "application/json",
			"application/x-www-form-urlencoded" })
	public Response createEvent(Event event);
	
	@GET
	@Path("/events/{event_type}/{event_id}")
	@Produces({ "application/xml", "application/json" })
	@Consumes({ "application/xml", "application/json",
			"application/x-www-form-urlencoded" })
	public Response getEvent(@PathParam("event_type") String eventType, @PathParam("event_id") String eventId);

}
