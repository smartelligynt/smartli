package com.st.services.oauth.util;
/**
 * @author mpala
 * This is a static class having OAuth related constants
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;


public class OAuthConstants {
	
	public static final String GRANT_TYPE = "grant_type";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String CLIENT_ID = "client_id";
	public static final String CLIENT_SECRET = "client_secret";
	public static final String CODE = "code";
	public static final String REDIRECT_URI = "redirect_uri";
	public static final String WM_CUSTOMER_ID = "wm_customer_id";
	public static final String TP_CUSTOMER_ID = "tp_customer_id";
	
	//authorize third party request - parameters
	public static final String OAUTH_TOKEN = "oauth_token";
	public static final String OAUTH_CONSUMER_KEY = "oauth_consumer_key";
	public static final String OAUTH_TIMESTAMP = "oauth_timestamp";
	public static final String OAUTH_SIGNATURE_METHOD = "oauth_signature_method";
	public static final String OAUTH_SIGNATURE = "oauth_signature";
	public static final String OAUTH_VERSION = "oauth_version";
	public static final String BASE_STRING = "base_string";
	
	public static final List<String> QUALIFIED_AUTHORIZATION_HEADER_PARAMS = new ArrayList<String>(Arrays.asList(OAUTH_TOKEN, WM_CUSTOMER_ID, OAUTH_TIMESTAMP, OAUTH_SIGNATURE_METHOD, OAUTH_CONSUMER_KEY, OAUTH_VERSION));
	
	public static final String OAUTH_AUTHORIZE_HEADER = "Authorization";
	public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
	
	public static final String OAUTH_HEADER_NAME = "OAuth";
	
	public static final String ASSERTION_GRANT_TYPE = "assertion";
	public static final String CODE_GRANT_TYPE = "authorization_code";
	public static final String ACCEPT_TYPE = "Accept";
	
	public static final String XML_ACCEPT_TYPE = "text/xml";
	public static final String JSON_ACCEPT_TYPE = "application/json";
	
	
	public static final String ACCESS_TOKEN_EXPIRE_TIME = "access_token_expire_time";
	public static final Object REFRESH_TOKEN_EXPIRE_TIME = "refresh_token_expire_time";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String HOST = "Host";
	
	
	public static TypeReference<HashMap<String, String>> TYPE_REFERENCE = new TypeReference<HashMap<String, String>>() {
	};
	
}
