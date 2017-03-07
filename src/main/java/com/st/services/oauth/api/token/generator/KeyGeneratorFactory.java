package com.st.services.oauth.api.token.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.core.Response.Status;

import com.st.services.oauth.AuthServiceErrors;
import com.st.services.oauth.OAuthException;
import com.st.services.oauth.token.KeyAlgorithm;

public class KeyGeneratorFactory {

	private static final Logger _logger = Logger
			.getLogger(KeyGeneratorFactory.class.getName());

	private KeyGeneratorFactory() {

	}

	private static boolean isInitialized = false;

	private static final Map<KeyAlgorithm, ValueGenerator> keyGenerators = new HashMap<KeyAlgorithm, ValueGenerator>();

	public static synchronized void init() {
		if (isInitialized) {
			return;
		}
		_logger.info("Initializing KeyGeneratorFactory...");
		keyGenerators.put(KeyAlgorithm.DH, new DHGenerator());
		keyGenerators.put(KeyAlgorithm.RSA, new RSAGenerator());
		isInitialized = true;
	}

	public static ValueGenerator getInstance(KeyAlgorithm algorithm)
			 {
		if (!isInitialized) {
			_logger.severe("Initialization Failed...");
			throw new OAuthException(
					AuthServiceErrors.PROPERTIES_NOT_INITIALIZED,
					Status.PRECONDITION_FAILED);
		}
		return keyGenerators.get(algorithm);
	}

}
