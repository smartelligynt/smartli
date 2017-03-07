package com.st.services.oauth;

/**
 * @author mpala
 * This is a static class having various Authentication Services related 
 * constants
 */
import java.util.Arrays;
import java.util.List;

import com.st.services.oauth.TokenServiceConstants;

public class AuthServiceConstants {

	public static final String ACCESS_TOKEN_TYPE = "ACCESS_TOKEN";
	public static final String AUTHORIZATION_CODE_TOKEN_TYPE = "AUTHORIZATION_CODE";
	public static final String OAUTH_VERSION = "2.0";

	public static final String CLIENT_ID_KEY = "client_id";
	public static final String CLIENT_SECRET_KEY = "client_secret";

	public static final List<String> GET_ACCESS_TOKEN_FROM_REDIRECT_FILTER_MAN_LIST = Arrays
			.asList(AccountServiceConstants.VERIFICATION_CODE,
					AccountServiceConstants.WM_CALLBACK_URL,
					AccountServiceConstants.CUSTOMER_ID,
					AccountServiceConstants.THIRD_PARTY_ID);
	public static final List<String> GET_SESSION_KEY_MAN_LIST = Arrays.asList(
			AccountServiceConstants.CUSTOMER_ID,
			AccountServiceConstants.THIRD_PARTY_ID);
	public static final List<String> GET_TP_INFO_MAN_LIST = Arrays
			.asList(AccountServiceConstants.THIRD_PARTY_ID);

	public static final List<String> REVOKE_ACCESS_MAN_LIST = Arrays.asList(
			AccountServiceConstants.CUSTOMER_ID,
			AccountServiceConstants.THIRD_PARTY_ID);

	public static final List<String> AUTHORIZATION_CODE_MAN_LIST = Arrays
			.asList(AccountServiceConstants.CUSTOMER_ID,
					AccountServiceConstants.CLIENT_ID,
					AccountServiceConstants.TP_CALLBACK_URL);

	public static final List<String> CLIENT_CREDENTIALS_LIST = Arrays.asList(
			OAuthConstants.CLIENT_ID, OAuthConstants.CLIENT_SECRET);

	public static final List<String> WM_KEY_SECRET_MAN_LIST = Arrays
			.asList(TokenServiceConstants.BASE);

	public static final String ACCESS_TOKEN = "ACCESS_TOKEN";

}
