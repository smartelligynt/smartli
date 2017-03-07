package com.st.services.oauth.token;

import com.st.services.oauth.OAuthException;

/**
 * Signs a base string, returning the OAuth signature
 * 
 * 
 * 
 */
public interface SignatureService {

	/**
	 * Returns the signature
	 * 
	 * @param baseString
	 *            url-encoded string to sign
	 * @param apiSecret
	 *            api secret for your app
	 * @param tokenSecret
	 *            token secret (empty string for the request token step)
	 * 
	 * @return signature
	 */
	public String getSignature(String baseString, String providerSecret,
			String tokenSecret) throws OAuthException;

	/**
	 * Returns the signature method/algorithm
	 * 
	 * @return
	 */
	public String getSignatureMethod();

}
