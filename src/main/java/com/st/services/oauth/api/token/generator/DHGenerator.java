package com.st.services.oauth.api.token.generator;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Logger;

import com.st.services.oauth.OAuthException;
import com.st.services.oauth.TokenServiceConstants;
import com.st.services.oauth.token.KeyAlgorithm;
import com.st.services.oauth.token.KeySecretPair;
import com.st.services.oauth.util.TokenUtil;

public class DHGenerator implements ValueGenerator {

	private static final Logger _logger = Logger.getLogger(DHGenerator.class
			.getName());

	protected DHGenerator() {

	}

	@Override
	public KeySecretPair generateValue() throws OAuthException {

		return null;
	}

	@Override
	public KeySecretPair generateValue(String base)
			throws NoSuchAlgorithmException {

		if (base == null) {
			return null;
		}
		_logger.info("Generating Key/Secret by DH algorithm...");
		KeySecretPair ksPair = new KeySecretPair();

		SecureRandom sr = new SecureRandom(base.getBytes());

		KeyPairGenerator kpg = KeyPairGenerator.getInstance(KeyAlgorithm.DH
				.toString());

		kpg.initialize(TokenServiceConstants.DH_KEY_SIZE, sr);

		KeyPair keyPair = kpg.generateKeyPair();

		byte[] pub = keyPair.getPublic().getEncoded();
		byte[] priv = keyPair.getPrivate().getEncoded();

		// String str = new sun.misc.BASE64Encoder().encode(pub).replace("\r",
		// "");
		ksPair.setKey(TokenUtil.getKeyStringFromBytes(pub));
		ksPair.setSecret(TokenUtil.getKeyStringFromBytes(priv));

		return ksPair;

	}

}
