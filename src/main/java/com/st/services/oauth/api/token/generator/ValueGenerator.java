/**
 *       
 */

package com.st.services.oauth.api.token.generator;

import java.security.NoSuchAlgorithmException;

import com.st.services.oauth.token.KeySecretPair;




public interface ValueGenerator {
	
	KeySecretPair generateValue() ;

    KeySecretPair generateValue(String param) throws NoSuchAlgorithmException;
}
