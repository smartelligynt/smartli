package com.st.services.tp.auth.client.api;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.st.services.oauth.util.StringLiterals;

@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public interface AuthClient {

	@POST
	@Path("/customer/{" + StringLiterals.CUSTOMER_ID + "}/tp/{"
			+ StringLiterals.TP_NAME + "}/enable_access")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response enableAccess(
			@PathParam(StringLiterals.CUSTOMER_ID) String customerId,
			@PathParam(StringLiterals.TP_NAME) String tpName,
			Map<String, String> requestBody);

	@GET
	@Path("/customer/{" + StringLiterals.CUSTOMER_ID + "}/tp/{"
			+ StringLiterals.TP_NAME + "}/get_access")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAccess(
			@PathParam(StringLiterals.CUSTOMER_ID) String customerId,
			@PathParam(StringLiterals.TP_NAME) String tpName);

	@PUT
	@Path("/customer/{" + StringLiterals.CUSTOMER_ID + "}/tp/{"
			+ StringLiterals.TP_NAME + "}/refresh_access")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response refreshAccess(
			@PathParam(StringLiterals.CUSTOMER_ID) String customerId,
			@PathParam(StringLiterals.TP_NAME) String tpName);

	@GET
	@Path("/tp/{" + StringLiterals.TP_NAME + "}/get_info")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getThirdPartyInfo(
			@PathParam(StringLiterals.TP_NAME) String tpName);

}
