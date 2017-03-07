package com.st.services.oauth.token;

import java.util.logging.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.st.services.oauth.util.OAuthUtil;
import com.st.services.oauth.util.URLUtil;

/**
 * HMAC-SHA1 implementation of {@SignatureService}
 * 
 * 
 */
public class HMACSha1SignatureService implements SignatureService {

	private static final Logger _logger = Logger
			.getLogger(HMACSha1SignatureService.class.getName());

	private static final String EMPTY_STRING = "";
	private static final String CARRIAGE_RETURN = "\r\n";
	private static final String UTF8 = "UTF-8";
	private static final String HMAC_SHA1 = "HmacSHA1";
	public static final String METHOD = "HMAC-SHA1";

	protected HMACSha1SignatureService() {

	}

	/**
	 * {@inheritDoc}
	 */
	public String getSignature(String baseString, String providerSecret,
			String tokenSecret) {
		try {
			_logger.info("Signing the base string...");
			return doSign(baseString, URLUtil.percentEncode(providerSecret)
					+ '&' + URLUtil.percentEncode(tokenSecret));
		} catch (Exception e) {
			_logger.severe("Unknown Exception occured in HMACSha1SignatureService.getSignature()"
					+ e);
			e.printStackTrace();
			throw OAuthUtil.getOAuthExceptionFromUnknown(e);
		}
	}

	private String doSign(String toSign, String keyString) throws Exception {
		SecretKeySpec key = new SecretKeySpec((keyString).getBytes(UTF8),
				HMAC_SHA1);
		Mac mac = Mac.getInstance(HMAC_SHA1);
		mac.init(key);
		byte[] bytes = mac.doFinal(toSign.getBytes(UTF8));
		return new String(Base64.encodeBase64(bytes)).replace(CARRIAGE_RETURN,
				EMPTY_STRING);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getSignatureMethod() {
		return METHOD;
	}

}
