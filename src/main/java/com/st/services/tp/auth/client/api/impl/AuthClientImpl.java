package com.st.services.tp.auth.client.api.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.st.services.oauth.domain.ThirdPartyInfoStore;
import com.st.services.oauth.domain.dao.ThirdPartyTokenStoreDAO;
import com.st.services.oauth.domain.entity.ThirdPartyInfoEntity;
import com.st.services.oauth.domain.entity.ThirdPartyTokenStoreEntity;
import com.st.services.oauth.util.AuthServiceErrors;
import com.st.services.oauth.util.GrantType;
import com.st.services.oauth.util.JsonUtil;
import com.st.services.oauth.util.KeyTypes;
import com.st.services.oauth.util.OAuthConstants;
import com.st.services.oauth.util.OAuthException;
import com.st.services.oauth.util.OAuthUtil;
import com.st.services.tp.auth.client.api.AuthClient;

@Service("authClient")
public class AuthClientImpl implements AuthClient {

	private static final Logger _logger = Logger.getLogger(AuthClientImpl.class
			.getName());

	private static final StringBuilder ACCESS_TOKEN_RESPONSE = new StringBuilder(
			"{\"access_token\" : \"").append(KeyTypes.access_token.tag())
			.append("\"}");

	private static final StringBuilder FORBIDDEN_RESPONSE = new StringBuilder(
			"{\"response:message\" : \"").append("Access Forbidden").append(
			"\"}");

	// @Autowired
	// @Qualifier("webClientProviders")
	@Resource(name = "webClientProviders")
	List<JacksonJsonProvider> webClientProviders;

	@Autowired
	@Qualifier("thirdPartyInfoStore")
	ThirdPartyInfoStore thirdPartyInfoStore;

	@Autowired
	@Qualifier("thirdPartyTokenStoreDAO")
	ThirdPartyTokenStoreDAO thirdPartyTokenStoreDAO;

	@Override
	public Response enableAccess(String customerId, String tpName,
			Map<String, String> requestMap) {

		try {

			String accessToken = callTokenService(customerId, tpName,
					requestMap);
			if (!StringUtils.isEmpty(accessToken)) {
				return Response.ok(
						ACCESS_TOKEN_RESPONSE.toString().replace(
								KeyTypes.access_token.tag(), accessToken))
						.build();
			}

			return Response.status(Status.FORBIDDEN).entity(FORBIDDEN_RESPONSE)
					.build();

		} catch (OAuthException oe) {
			return Response.status(oe.getHttpStatus()).entity(oe.getError())
					.build();
		} catch (Exception e) {
			_logger.severe("Unknown Exception occured in enabling access");
			e.printStackTrace();
			OAuthException oae = OAuthUtil.getOAuthExceptionFromUnknown(e);
			return Response.status(oae.getHttpStatus()).entity(oae.getError())
					.build();
		}

	}

	private String buildRequestBody(Map<String, String> requestMap,
			ThirdPartyInfoEntity tpInfo) {
		GrantType grantType = GrantType.valueOf(requestMap
				.get(OAuthConstants.GRANT_TYPE));
		List<String> requestKeys = tpInfo.getOutRequestKeys(grantType);
		String requestBody = tpInfo.getOutRequestBodyTemplate(grantType);
		for (String reqKey : requestKeys) {
			KeyTypes key = KeyTypes.valueOf(reqKey);
			requestBody = requestBody
					.replace(key.tag(), requestMap.get(reqKey));
		}
		return requestBody;
	}

	private String saveToken(String inTokenResponseJson, String customerId,
			ThirdPartyInfoEntity tpInfo) {
		Date time = new Date();

		Map<String, Object> accessTokenResponse = (Map<String, Object>) JsonUtil
				.convertJsonToMap(inTokenResponseJson,
						OAuthConstants.TYPE_REFERENCE);
		ThirdPartyTokenStoreEntity tpTokenStoreEntity = null;
		List<ThirdPartyTokenStoreEntity> tpTokenStoreEntityList = thirdPartyTokenStoreDAO
				.getByCustomerIdAndTP(customerId, tpInfo.getShortName());
		if (tpTokenStoreEntityList == null || tpTokenStoreEntityList.isEmpty()) {
			tpTokenStoreEntity = new ThirdPartyTokenStoreEntity();
			tpTokenStoreEntity.setId(UUID.randomUUID().toString());
			tpTokenStoreEntity.setCustomerId(customerId);
			tpTokenStoreEntity.setTpName(tpInfo.getShortName());
		} else {
			tpTokenStoreEntity = tpTokenStoreEntityList.get(0);
		}

		Map<String, String> responseKeys = tpInfo.getInResponseKeysMap();

		tpTokenStoreEntity.setAccessToken((String) accessTokenResponse
				.get(responseKeys.get(KeyTypes.access_token.name())));
		tpTokenStoreEntity.setAccessTokenCreateDTM(time);

		String accessTokenExpireTimeKey = responseKeys
				.get(OAuthConstants.ACCESS_TOKEN_EXPIRE_TIME);
		if (!StringUtils.isEmpty(accessTokenExpireTimeKey)) {
			tpTokenStoreEntity.setAccessTokenExpiryDTM(new Date(time.getTime()
					+ Long.parseLong((String) accessTokenResponse
							.get(accessTokenExpireTimeKey))));
		}

		String refreshTokenResponseKey = responseKeys
				.get(KeyTypes.refresh_token.name());
		if (!StringUtils.isEmpty(refreshTokenResponseKey)) {
			tpTokenStoreEntity.setRefreshToken((String) accessTokenResponse
					.get(refreshTokenResponseKey));
			tpTokenStoreEntity.setRefreshTokenCreateDTM(time);
		}
		String refreshTokenExpireTimeKey = responseKeys
				.get(OAuthConstants.REFRESH_TOKEN_EXPIRE_TIME);
		if (!StringUtils.isEmpty(refreshTokenExpireTimeKey)) {
			tpTokenStoreEntity.setRefreshTokenExpiryDTM(new Date(time.getTime()
					+ Long.parseLong((String) accessTokenResponse
							.get(refreshTokenExpireTimeKey))));
		}

		thirdPartyTokenStoreDAO.save(tpTokenStoreEntity);

		return tpTokenStoreEntity.getAccessToken();
	}

	public Response getAccess(String customerId, String tpName) {

		try {
			String accessToken = null;
			List<ThirdPartyTokenStoreEntity> thirdPartyTokenStoreList = thirdPartyTokenStoreDAO
					.getByCustomerIdAndTP(customerId, tpName);
			if (thirdPartyTokenStoreList == null
					|| thirdPartyTokenStoreList.isEmpty()) {
				throw new OAuthException(AuthServiceErrors.NO_ACCESS_TOKEN,
						Status.NOT_FOUND);
			}
			ThirdPartyTokenStoreEntity tpTokenStore = thirdPartyTokenStoreList
					.get(0);

			if (tpTokenStore.getAccessTokenExpiryDTM() != null
					&& System.currentTimeMillis() > tpTokenStore
							.getAccessTokenExpiryDTM().getTime()) {
				if (StringUtils.isEmpty(tpTokenStore.getRefreshToken())) {
					throw new OAuthException(
							AuthServiceErrors.ACCESS_TOKEN_EXPIRED,
							Status.NOT_ACCEPTABLE);
				}

				if (System.currentTimeMillis() > tpTokenStore
						.getRefreshTokenExpiryDTM().getTime()) {
					throw new OAuthException(
							AuthServiceErrors.REFRESH_TOKEN_EXPIRED,
							Status.NOT_ACCEPTABLE);
				}

				accessToken = refreshAccess(tpName, customerId,
						tpTokenStore.getRefreshToken());
			} else {
				accessToken = tpTokenStore.getAccessToken();
			}
			if (!StringUtils.isEmpty(accessToken)) {

				return Response.ok(
						ACCESS_TOKEN_RESPONSE.toString().replace(
								KeyTypes.access_token.tag(), accessToken))
						.build();
			}

			return Response.status(Status.FORBIDDEN).entity(FORBIDDEN_RESPONSE)
					.build();

		} catch (OAuthException oe) {
			return Response.status(oe.getHttpStatus()).entity(oe.getError())
					.build();
		} catch (Exception e) {
			_logger.severe("Unknown Exception occured getting access token");
			e.printStackTrace();
			OAuthException oae = OAuthUtil.getOAuthExceptionFromUnknown(e);
			return Response.status(oae.getHttpStatus()).entity(oae.getError())
					.build();
		}

	}

	public Response refreshAccess(String customerId, String tpName) {

		try {
			String accessToken = null;
			List<ThirdPartyTokenStoreEntity> thirdPartyTokenStoreList = thirdPartyTokenStoreDAO
					.getByCustomerIdAndTP(customerId, tpName);
			if (thirdPartyTokenStoreList == null
					|| thirdPartyTokenStoreList.isEmpty()) {
				throw new OAuthException(AuthServiceErrors.NO_ACCESS_TOKEN,
						Status.NOT_FOUND);
			}
			ThirdPartyTokenStoreEntity tpTokenStore = thirdPartyTokenStoreList
					.get(0);

			accessToken = refreshAccess(tpName, customerId,
					tpTokenStore.getRefreshToken());

			if (!StringUtils.isEmpty(accessToken)) {

				return Response.ok(
						ACCESS_TOKEN_RESPONSE.toString().replace(
								KeyTypes.access_token.tag(), accessToken))
						.build();
			}

			return Response.status(Status.FORBIDDEN).entity(FORBIDDEN_RESPONSE)
					.build();

		} catch (OAuthException oe) {
			return Response.status(oe.getHttpStatus()).entity(oe.getError())
					.build();
		} catch (Exception e) {
			_logger.severe("Unknown Exception occured in Refreshing Access Token");
			e.printStackTrace();
			OAuthException oae = OAuthUtil.getOAuthExceptionFromUnknown(e);
			return Response.status(oae.getHttpStatus()).entity(oae.getError())
					.build();
		}

	}

	private String refreshAccess(String tpName, String customerId,
			String refreshToken) {
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put(OAuthConstants.GRANT_TYPE,
				GrantType.refresh_token.name());
		requestMap.put(GrantType.refresh_token.name(), refreshToken);

		return callTokenService(customerId, tpName, requestMap);

	}

	private String callTokenService(String customerId, String tpName,
			Map<String, String> requestMap) {

		PostMethod post = null;
		try {

			ThirdPartyInfoEntity tpInfo = thirdPartyInfoStore.get(tpName);
			HttpClient httpClient = new HttpClient();
			post = new PostMethod(tpInfo.getAccessEndpoint());

			String requestBody = buildRequestBody(requestMap, tpInfo);

			Map<String, String> headersMap = tpInfo.getOutHeadersMap();
			for (Map.Entry<String, String> header : headersMap.entrySet()) {
				post.addRequestHeader(header.getKey(), header.getValue());

			}

			post.setRequestEntity(new StringRequestEntity(requestBody,
					headersMap.get(OAuthConstants.CONTENT_TYPE),
					OAuthConstants.UTF));

			int status = httpClient.executeMethod(post);
			if (status == Status.OK.getStatusCode()) {
				return saveToken(post.getResponseBodyAsString(), customerId,
						tpInfo);

			}
			throw new OAuthException(AuthServiceErrors.REFRESH_ACCESS_FAILED,
					Status.INTERNAL_SERVER_ERROR);

		} catch (OAuthException oe) {
			throw oe;

		} catch (Exception e) {
			_logger.severe("Unknown Exception occured in calling token service");
			e.printStackTrace();
			throw OAuthUtil.getOAuthExceptionFromUnknown(e);

		} finally {
			if (post != null) {
				post.releaseConnection();
			}
		}

	}

	public Response getThirdPartyInfo(String tpName) {

		try {

			ThirdPartyInfoEntity tpInfo = thirdPartyInfoStore.get(tpName);
			if (tpInfo != null) {
				return Response.ok(tpInfo.toString()).build();
			}

			return Response.status(Status.NOT_FOUND).build();

		} catch (OAuthException oe) {
			return Response.status(oe.getHttpStatus()).entity(oe.getError())
					.build();
		} catch (Exception e) {
			_logger.severe("Unknown Exception occured in getting third party info");
			e.printStackTrace();
			OAuthException oae = OAuthUtil.getOAuthExceptionFromUnknown(e);
			return Response.status(oae.getHttpStatus()).entity(oae.getError())
					.build();
		}

	}

}
