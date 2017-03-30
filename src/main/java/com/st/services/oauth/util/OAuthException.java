package com.st.services.oauth.util;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

public class OAuthException extends WebApplicationException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 123L;
	private AuthError error;
    private Status        httpStatus;

    
    public OAuthException(AuthError error, Status httpStatus) {
        this.error = error;
        this.httpStatus = httpStatus;
    }

    public Status getHttpStatus() {
        return httpStatus;
    }

    public AuthError getError() {
        return error;
    }

}
