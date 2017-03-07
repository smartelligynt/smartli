package com.st.services.oauth.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("token/")
public interface TokenService {

	@POST
	@Path("revoke")
	@Produces("application/xml")
	@Consumes("application/x-www-form-urlencoded")
	public Response revokeAccess(final MultivaluedMap<String, String> params);

	@POST
	@Path("code")
	@Produces({ "application/xml", "text/plain" })
	@Consumes("application/x-www-form-urlencoded")
	public Response getAuthorizationCode(final MultivaluedMap<String, String> params);
	
	@POST
	@Path("accesstoken")
	@Produces("application/xml")
	@Consumes("application/x-www-form-urlencoded")
	public Response getAccessToken(final MultivaluedMap<String, String> params);
	
	@GET
	@Path("key_secret")
	@Produces("application/xml")
	public Response getKeySecret(@Context UriInfo ui);

}
