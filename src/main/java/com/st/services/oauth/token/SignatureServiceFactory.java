package com.st.services.oauth.token;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.core.Response.Status;

import com.st.services.oauth.AuthServiceErrors;
import com.st.services.oauth.OAuthException;

public class SignatureServiceFactory {

	private static final Logger _logger = Logger
			.getLogger(SignatureServiceFactory.class.getName());

	private SignatureServiceFactory() {

	}

	private static boolean isInitialized = false;

	private static final Map<String, SignatureService> services = new HashMap<String, SignatureService>();

	public static synchronized void init() {
		if (isInitialized) {
			return;
		}
		_logger.info("Initializing SignatureServiceFactory...");
		services.put(HMACSha1SignatureService.METHOD,
				new HMACSha1SignatureService());

		isInitialized = true;
	}

	public static SignatureService getInstance(String signMethod)
			throws OAuthException {

		if (!isInitialized) {
			_logger.severe("Initialization Failed...");
			throw new OAuthException(
					AuthServiceErrors.PROPERTIES_NOT_INITIALIZED,
					Status.PRECONDITION_FAILED);
		}

		return services.get(signMethod);
	}

}
