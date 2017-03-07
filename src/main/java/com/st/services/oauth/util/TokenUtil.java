package com.st.services.oauth.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
//import java.util.logging.Logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.st.services.oauth.AccountServiceConstants;
import com.st.services.oauth.AuthServiceConstants;
import com.st.services.oauth.TokenServiceConstants;
import com.st.services.oauth.model.AccessTokenResponse;
import com.st.services.oauth.model.ThirdPartyInfo;
import com.st.services.oauth.model.TokenResponse;
import com.st.services.oauth.token.GrantType;
import com.st.services.oauth.token.KeySecretPair;
import com.st.services.oauth.token.OAuthToken;
import com.st.services.oauth.token.ProviderToken;
import com.st.services.util.AppProperties;
import com.st.services.util.Util;

public class TokenUtil {

	private static final Logger _logger = LogManager.getLogger(TokenUtil.class
			.getName());
	


	public static String getKeyStringFromBytes(byte[] key) {
		_logger.debug("Getting Key String from Bytes");
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < key.length; i++) {
			hexString.append(Integer.toHexString(0xFF & key[i]));
		}
		return hexString.toString();
	}

	private static Date getAuthorizationCodeExpirationTime() {
		_logger.debug("Getting Authorization Code expiration Time");
		Date dt = new Date();
		long expireTime = Long
				.parseLong(AppProperties
						.getProperty(TokenServiceConstants.AUTHORIZATION_CODE_EXPIRE_TIME_KEY));
		return new Date(((dt.getTime()) / 1000 + expireTime * 60) * 1000);
	}

	public static ProviderToken buildTokenObjectForAuthorizationCode(
			Map<String, String> params, String authorizationCode) {

		_logger.info("Building token object for Auhtorization Code...");
		ProviderToken token = new ProviderToken();
		token.setExpirationDate(getAuthorizationCodeExpirationTime());
		token.setOauthVersion(AuthServiceConstants.OAUTH_VERSION);
		token.setThirdPartyId(ThirdPartyInfo.getTPInfoByProviderKey(
				params.get(AccountServiceConstants.CLIENT_ID)).getId());
		token.setTokenValue(authorizationCode);
		token.setTokenType(AuthServiceConstants.AUTHORIZATION_CODE_TOKEN_TYPE);
		token.setWmCustomerId(Long.parseLong(params
				.get(AccountServiceConstants.CUSTOMER_ID)));
		String redirectUri = URLUtil.decodePercent(params
				.get(AccountServiceConstants.TP_CALLBACK_URL));
		int indx = redirectUri.indexOf('?');
		if (indx >= 0) {
			redirectUri = redirectUri.substring(0, indx);
		}

		token.setRedirectUri(redirectUri);
		token.setTokenGenMethod(AppProperties
				.getProperty(TokenServiceConstants.AUTHORIZATION_CODE_GEN_METHOD_KEY));
		return token;
	}

	public static OAuthToken buildWMAccessToken(KeySecretPair ksp, long tpId,
			long wmCustomerId, GrantType grantType) {
		_logger.info("Building WM Access Token...");
		OAuthToken token = new ProviderToken();
		token.setWmCustomerId(wmCustomerId);
		token.setThirdPartyId(tpId);
		token.setGrantType(grantType);
		token.setOauthVersion(AuthServiceConstants.OAUTH_VERSION);
		token.setTokenValue(ksp
				.getKey()
				.substring(
						0,
						Integer.parseInt(AppProperties
								.getProperty(TokenServiceConstants.ACCESS_TOKEN_SIZE_KEY))));
		token.setTokenSecret(ksp
				.getSecret()
				.substring(
						0,
						Integer.parseInt(AppProperties
								.getProperty(TokenServiceConstants.ACCESS_TOKEN_SECRET_SIZE_KEY))));
		token.setExpirationDate(getTokenExpirationDate());
		token.setTokenType(AuthServiceConstants.ACCESS_TOKEN_TYPE);
		((ProviderToken) token)
				.setTokenGenMethod(AppProperties
						.getProperty(TokenServiceConstants.ACCESS_TOKEN_GEN_METHOD_KEY));

		return token;
	}

	private static Date getTokenExpirationDate() {
		_logger.debug("Getting AccessToken expiration Time");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,
				Integer.parseInt(AppProperties
						.getProperty(TokenServiceConstants.ACCESS_TOKEN_EXPIRE_TIME_KEY)));
		return cal.getTime();
	}

	public static AccessTokenResponse buildAccessTokenResponse(
			OAuthToken token) {
		_logger.info("Building WM Access Token Response...");
		AccessTokenResponse response = new AccessTokenResponse();
		/*response.setWmCustomerId(TwoFishCipher.encrypt(Long.toString(token
				.getWmCustomerId())));*/

		TokenResponse tokenResponse = new TokenResponse();
		response.setToken(tokenResponse);
		tokenResponse.setTokenId(token.getTokenValue());
		tokenResponse.setTokenSecret(token.getTokenSecret());
		tokenResponse.setExpirationTime(token.getExpirationDate());
		token.setRefreshToken(token.getRefreshToken());
		return response;
	}

	public static OAuthToken validateAndGetToken(List<OAuthToken> tokenList) {
		_logger.debug("validate and get a token from the list...");
		OAuthToken token = getValidAccessToken(tokenList);
		int timeLag = Integer
				.parseInt(AppProperties
						.getProperty(TokenServiceConstants.TOKEN_EXPIRE_TIME_LAG_IN_MINUTES_KEY));
		if (token != null) {
			if ((Util.getDateInMinutes(token.getExpirationDate()) - timeLag) < Util
					.currentTimestampInMinutes()) {
				return null;
			}
		}

		return token;
	}

	public static OAuthToken getValidAuthorizationCode(
			List<OAuthToken> tokenList) {
		_logger.debug("getting valid authorization code...");
		if (tokenList == null || tokenList.size() == 0) {
			return null;
		}

		OAuthToken token = tokenList.get(0);
		if (tokenList.size() > 1) {
			token = OAuthUtil.getLatestTokenFromList(tokenList);
		}

		return token;
	}

	public static final OAuthToken getValidAccessToken(
			List<OAuthToken> tokenList) {
		_logger.debug("getting valid access token...");
		if (tokenList == null || tokenList.size() == 0) {
			return null;
		}
		List<OAuthToken> accessTokenList = getAccessTokenList(tokenList);

		if (accessTokenList == null || accessTokenList.size() == 0) {
			return null;
		}
		OAuthToken token = accessTokenList.get(0);
		if (accessTokenList.size() > 1) {
			token = OAuthUtil.getLatestTokenFromList(accessTokenList);
		}

		return token;
	}

	public static List<OAuthToken> getAccessTokenList(List<OAuthToken> tokenList) {
		_logger.debug("getting access token list...");
		List<OAuthToken> accessTokenList = new ArrayList<OAuthToken>();
		for (OAuthToken oAuthToken : tokenList) {
			if (oAuthToken.getTokenType().equalsIgnoreCase(
					AuthServiceConstants.ACCESS_TOKEN_TYPE)) {
				accessTokenList.add(oAuthToken);
			}
		}
		return accessTokenList;
	}

}
