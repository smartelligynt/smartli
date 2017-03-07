package com.st.services.oauth;



public class TokenServiceConstants {
	
	public static final int DH_KEY_SIZE = 512;
	
	public static final int RSA_KEY_SIZE = 1024;
	
	public static final String WM_ID = "WALMART";
	
	public static final String AUTHORIZATION_CODE_EXPIRE_TIME_KEY = "com.wm.authorization.code.expire.time";
	public static final String TIMESTAMP_NONCE_VALIDATION_TIME_KEY = "com.wm.authorize.request.timestamp.nonce.validation.time";
	public static final String ACCESS_TOKEN_EXPIRE_TIME_KEY = "com.wm.access.token.expire.time";
	
	public static final int PROVIDER_KEY_SIZE = 32;
	
	public static final int PROVIDER_SECRET_SIZE = 128;
	
	public static final String KEY_SIZE = "key_size";
	public static final String SECRET_SIZE = "secret_size";
	public static final String BASE = "base";
	public static final String ALGORITHM = "algorithm";
	
	public static final String ACCESS_TOKEN_SIZE_KEY = "com.wm.token.value.size";
	public static final String ACCESS_TOKEN_SECRET_SIZE_KEY = "com.wm.token.secret.size";
	public static final String AUTHORIZATION_CODE_SIZE_KEY = "com.wm.authorization.code.size";
	
	public static final String TOKEN_EXPIRE_TIME_LAG_IN_MINUTES_KEY = "com.wm.access.token.expire.time.lag";
	
	public static final String ACCESS_TOKEN_GEN_METHOD_KEY = "com.wm.token.gen.method";
	
	public static final String AUTHORIZATION_CODE_GEN_METHOD_KEY = "com.wm.code.gen.method";

}
