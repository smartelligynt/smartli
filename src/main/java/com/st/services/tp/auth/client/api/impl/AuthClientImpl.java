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

import org.apache.commons.codec.EncoderException;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.st.services.oauth.util.GrantType;
import com.st.services.oauth.domain.ThirdPartyInfoStore;
import com.st.services.oauth.domain.dao.ThirdPartyTokenStoreDAO;
import com.st.services.oauth.domain.entity.ThirdPartyInfoEntity;
import com.st.services.oauth.domain.entity.ThirdPartyTokenStoreEntity;
import com.st.services.oauth.util.AuthServiceErrors;
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
			System.setProperty("https.protocols", "TLSv1.2");
			ThirdPartyInfoEntity tpInfo = thirdPartyInfoStore.get(tpName);

			WebClient client = WebClient.create(tpInfo.getAccessEndpoint(),
					webClientProviders);

			// build request body

			String requestBody = buildRequestBody(requestMap, tpInfo);

			// set headers
			Map<String, String> headersMap = tpInfo.getOutHeadersMap();
			for (Map.Entry<String, String> header : headersMap.entrySet()) {
				client.header(header.getKey(), header.getValue());

			}

			Response response = client.post(requestBody);
			if (response.getStatus() == Status.OK.getStatusCode()) {

				String accessToken = saveToken(
						response.readEntity(String.class), customerId, tpInfo);
				_logger.info("Enable Access call Successful - CustomerID/TP"
						+ customerId + "/" + tpName);
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
			_logger.severe("Unknown Exception occured in TokenService.getAccessToken()");
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

		Map<String, String> accessTokenResponse = (Map<String, String>) JsonUtil
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

		tpTokenStoreEntity.setAccessToken(accessTokenResponse.get(responseKeys
				.get(KeyTypes.access_token.name())));
		tpTokenStoreEntity.setAccessTokenCreateDTM(time);

		String accessTokenExpireTimeKey = responseKeys
				.get(OAuthConstants.ACCESS_TOKEN_EXPIRE_TIME);
		if (!StringUtils.isEmpty(accessTokenExpireTimeKey)) {
			tpTokenStoreEntity.setAccessTokenExpiryDTM(new Date(time.getTime()
					+ Long.parseLong(accessTokenResponse
							.get(accessTokenExpireTimeKey))));
		}

		String refreshTokenResponseKey = responseKeys
				.get(KeyTypes.refresh_token.name());
		if (!StringUtils.isEmpty(refreshTokenResponseKey)) {
			tpTokenStoreEntity.setRefreshToken(accessTokenResponse
					.get(refreshTokenResponseKey));
			tpTokenStoreEntity.setRefreshTokenCreateDTM(time);
		}
		String refreshTokenExpireTimeKey = responseKeys
				.get(OAuthConstants.REFRESH_TOKEN_EXPIRE_TIME);
		if (!StringUtils.isEmpty(refreshTokenExpireTimeKey)) {
			tpTokenStoreEntity.setRefreshTokenExpiryDTM(new Date(time.getTime()
					+ Long.parseLong(accessTokenResponse
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

			if (System.currentTimeMillis() > tpTokenStore
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
			_logger.severe("Unknown Exception occured in TokenService.getAccessToken()");
			e.printStackTrace();
			OAuthException oae = OAuthUtil.getOAuthExceptionFromUnknown(e);
			return Response.status(oae.getHttpStatus()).entity(oae.getError())
					.build();
		}

	}

	private String refreshAccess(String tpName, String customerId,
			String refreshToken) throws EncoderException {

		ThirdPartyInfoEntity tpInfo = thirdPartyInfoStore.get(tpName);
		WebClient client = WebClient.create(tpInfo.getAccessEndpoint(),
				webClientProviders);

		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put(OAuthConstants.GRANT_TYPE,
				GrantType.refresh_token.name());
		requestMap.put(GrantType.refresh_token.name(), refreshToken);

		String requestBody = buildRequestBody(requestMap, tpInfo);

		Map<String, String> headersMap = tpInfo.getOutHeadersMap();
		for (Map.Entry<String, String> header : headersMap.entrySet()) {
			client.header(header.getKey(), header.getValue());

		}
		// String encodedRequestBody =
		// OAuthConstants.URL_CODEC.encode(requestBody);

		Response response = client.post(requestBody);
		if (response.getStatus() == Status.OK.getStatusCode()) {
			return saveToken(response.readEntity(String.class), customerId,
					tpInfo);

		}
		throw new OAuthException(AuthServiceErrors.REFRESH_ACCESS_FAILED,
				Status.INTERNAL_SERVER_ERROR);

	}



}
