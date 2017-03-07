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

public class RSAGenerator implements ValueGenerator {

	private static final Logger _logger = Logger.getLogger(RSAGenerator.class
			.getName());

	protected RSAGenerator() {

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
		_logger.info("Generating Key/Secret by RSA algorithm...");
		KeySecretPair ksPair = new KeySecretPair();

		SecureRandom sr = new SecureRandom(base.getBytes());

		KeyPairGenerator kpg = KeyPairGenerator.getInstance(KeyAlgorithm.RSA
				.toString());

		kpg.initialize(TokenServiceConstants.RSA_KEY_SIZE, sr);

		KeyPair keyPair = kpg.generateKeyPair();

		byte[] pub = keyPair.getPublic().getEncoded();
		byte[] priv = keyPair.getPrivate().getEncoded();

		/*
		 * String strPub = new
		 * sun.misc.BASE64Encoder().encode(pub).replace("\r\n", ""); String
		 * strPriv = new sun.misc.BASE64Encoder().encode(priv).replace("\r\n",
		 * ""); ksPair.setKey(strPub); ksPair.setSecret(strPriv);
		 */

		ksPair.setKey(TokenUtil.getKeyStringFromBytes(pub));
		ksPair.setSecret(TokenUtil.getKeyStringFromBytes(priv));

		return ksPair;

	}

}
