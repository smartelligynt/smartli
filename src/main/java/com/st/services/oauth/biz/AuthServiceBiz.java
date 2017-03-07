package com.st.services.oauth.biz;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.st.services.oauth.AccountServiceConstants;
import com.st.services.oauth.AuthServiceErrors;
import com.st.services.oauth.OAuthConstants;
import com.st.services.oauth.OAuthException;
import com.st.services.oauth.TokenServiceConstants;
import com.st.services.oauth.api.token.generator.KeyGeneratorFactory;
import com.st.services.oauth.model.AccessTokenResponse;
import com.st.services.oauth.model.ThirdPartyInfo;
import com.st.services.oauth.token.GrantType;
import com.st.services.oauth.token.KeyAlgorithm;
import com.st.services.oauth.token.KeySecretPair;
import com.st.services.oauth.token.OAuthToken;
import com.st.services.oauth.token.ProviderToken;
import com.st.services.oauth.token.SignatureServiceFactory;
import com.st.services.oauth.util.OAuthUtil;
import com.st.services.oauth.util.TokenUtil;
import com.st.services.util.AppProperties;
import com.st.services.util.Util;
@Component("authZerviceBiz")
public class AuthServiceBiz {

	private static final Logger _logger = LogManager
			.getLogger(AuthServiceBiz.class.getName());
	//private OAuthTokenDAO tokenDAO;
	//private WMTokenRequestValidator validator;
	//private NonceValidator nonceValidator;




	public void revokeAccess(Map<String, String> params) {
		_logger.info("initiating revoke access...");
/*		AuthServiceRequestValidator.validate(params,
				AuthServiceConstants.REVOKE_ACCESS_MAN_LIST);

		long lngWmCustId = Long.parseLong(params
				.get(AccountServiceConstants.WM_CUSTOMER_ID));
		long lngTpId = ThirdPartyInfo.getTPInfoById(
				params.get(AccountServiceConstants.THIRD_PARTY_ID)).getId();

		((WMOAuthTokenDAOImpl) tokenDAO).expireAllTokens(lngWmCustId, lngTpId);*/

	}

	public AccessTokenResponse getAccessToken(Map<String, String> params)
			 {
		AccessTokenResponse response = null;
		OAuthToken token = null;
		try {

			_logger.info("initiating getAccessToken...");
			// Request Validation
			Map<String, String> tokenObjectValues = null ;//= validator.validate(params);
			long tpId = Long.parseLong(tokenObjectValues
					.get(AccountServiceConstants.THIRD_PARTY_ID));
			
			long wmCustomerId = Long.parseLong(tokenObjectValues
					.get(AccountServiceConstants.CUSTOMER_ID));

			GrantType grantType = GrantType.valueOf(tokenObjectValues.get(
					OAuthConstants.GRANT_TYPE).toUpperCase());

			// Check if valid token exists
			_logger.info("checking if valid token exists");
			List<OAuthToken> tokenList=null;// = tokenDAO.getToken(wmCustomerId, tpId);
			// Verify the token list and get the Latest valid token
			token = TokenUtil.validateAndGetToken(tokenList);
			if (token == null) {

				// Token Generation
				_logger.info("generating access token for customer: "
						+ wmCustomerId);
				KeySecretPair ksp = KeyGeneratorFactory
						.getInstance(
								KeyAlgorithm.valueOf(AppProperties
										.getProperty(TokenServiceConstants.ACCESS_TOKEN_GEN_METHOD_KEY)))
						.generateValue(
								Long.toString(wmCustomerId)
										+ Long.toString(Util
												.currentTimestampInMinutes())
										+ Long.toString(tpId));

				// Build Token Object and Persist
				token = TokenUtil.buildWMAccessToken(ksp, tpId, wmCustomerId,
						grantType);
				// Expiring Authorization Code before issuing Access Token by
				// this grant type
				if (grantType.equals(GrantType.AUTHORIZATION_CODE)) {
					_logger.info("expiring authorization code");
					//tokenDAO.expireToken(wmCustomerId, tpId);
					// AuthorizationCodeCache.remove(params.get(OAuthConstants.CODE));
				}
				//tokenDAO.putToken(token);

			}

			response = TokenUtil.buildAccessTokenResponse(token);

		} catch (OAuthException oae) {
			throw oae;
		} catch (Exception e) {
			_logger.error("Unknown Exception occured in WMAuthServiceImpl.getAccessToken()");
			e.printStackTrace();
			throw OAuthUtil.getOAuthExceptionFromUnknown(e);
		}

		return response;

	}


	public String getAuthorizationCode(Map<String, String> params)
			throws OAuthException {
		try {
			_logger.info("initiating getAuthorizationCode...");
			// Validate Request
			/*AuthServiceRequestValidator.validateAuthorizationCodeRequest(
					params, AuthServiceConstants.AUTHORIZATION_CODE_MAN_LIST);
*/
			String wmCustomerId = params
					.get(AccountServiceConstants.CUSTOMER_ID);

			// Generate Authorization Code
			_logger.info("generating authorization code for customer: "
					+ wmCustomerId);
			KeySecretPair ksPair = KeyGeneratorFactory
					.getInstance(
							KeyAlgorithm.valueOf(AppProperties
									.getProperty(TokenServiceConstants.AUTHORIZATION_CODE_GEN_METHOD_KEY)))
					.generateValue(
							wmCustomerId
									+ Util.currentTimestampStringInSeconds()
									+ TokenServiceConstants.WM_ID);
			String authorizationCode = ksPair
					.getSecret()
					.substring(
							0,
							Integer.parseInt(AppProperties
									.getProperty(TokenServiceConstants.AUTHORIZATION_CODE_SIZE_KEY)));

			ProviderToken token = TokenUtil
					.buildTokenObjectForAuthorizationCode(params,
							authorizationCode);

			// Save Authorization Code to Database
			//tokenDAO.putToken(token);

			// Add Authorization Code to memory cache
			// AuthorizationCodeCache.clean();
			//AuthorizationCodeCache.add(token);

			return authorizationCode;

		} catch (OAuthException oae) {
			throw oae;
		} catch (Exception e) {
			_logger.error("Unknown Exception occured in WMAuthServiceImpl.getAuthorizationCode()");
			e.printStackTrace();
			throw OAuthUtil.getOAuthExceptionFromUnknown(e);
		}

	}


	public void authorizeRequest(Map<String, String> params)
			throws OAuthException {
		try {

			_logger.info("initiating authorizeRequest...");
			// Validate Nonce
			//nonceValidator.validate(params.get(OAuthConstants.OAUTH_TIMESTAMP));
			_logger.info("valid nonce...");
			// Validate Token
		/*	long wmCustId = Long.parseLong(TwoFishCipher.decrypt(params
					.get(OAuthConstants.WM_CUSTOMER_ID)));*/
			ThirdPartyInfo tpInfo = ThirdPartyInfo
					.getTPInfoByProviderKey(params
							.get(OAuthConstants.OAUTH_CONSUMER_KEY));
			if (tpInfo == null) {
				_logger.warn("Authorization Failed: Invalid OAuth Consumer Key");
				throw new OAuthException(
						AuthServiceErrors.INVALID_OAUTH_CONSUMER_KEY,
						Status.UNAUTHORIZED);
			}

			long tpId = tpInfo.getId();

			List<OAuthToken> tokenList=null; //= tokenDAO.getToken(wmCustId, tpId);

			OAuthToken token = TokenUtil.getValidAccessToken(tokenList);
			if (token == null
					|| !token.getTokenValue().equals(
							params.get(OAuthConstants.OAUTH_TOKEN))) {
				_logger.warn("Authorization Failed: Invalid Token");
				throw new OAuthException(AuthServiceErrors.INVALID_TOKEN,
						Status.UNAUTHORIZED);
			}
			_logger.info("valid token...");

			// validate wmCustomerId
			/*
			if (token.getWmCustomerId() != Long.parseLong(TwoFishCipher
					.decrypt(params.get(OAuthConstants.WM_CUSTOMER_ID)))) {
				_logger.warning("Authorization Failed: Invalid WM Customer Id");
				throw new OAuthException(AuthServiceErrors.INVALID_CUSTOMER_ID,
						Status.UNAUTHORIZED);
			}*/

			// Validate Signature
			validateSignature(params, tpInfo, token);
			_logger.info("valid signature...");

			_logger.info("Request Authorization Successful");

		} catch (OAuthException oae) {
			throw oae;
		} catch (Exception e) {
			_logger.error("Unknown Exception occured in WMAuthServiceImpl.authorizeRequest()");
			e.printStackTrace();
			throw OAuthUtil.getOAuthExceptionFromUnknown(e);
		}

	}

	private void validateSignature(Map<String, String> params,
			ThirdPartyInfo tpInfo, OAuthToken token)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {

		String expectedSignatureMethod = tpInfo.getProviderSignMethod();
		if (!expectedSignatureMethod.equals(params
				.get(OAuthConstants.OAUTH_SIGNATURE_METHOD))) {
			_logger.warn("Authorization Failed: Invalid Signature Method");
			throw new OAuthException(
					AuthServiceErrors.INVALID_SIGNATURE_METHOD,
					Status.UNAUTHORIZED);
		}
		String expectedSignature = SignatureServiceFactory.getInstance(
				expectedSignatureMethod).getSignature(
				params.get(OAuthConstants.BASE_STRING),
				tpInfo.getProviderSecret(), token.getTokenSecret());
		_logger.info("Expected Signature is: " + expectedSignature);
		_logger.info("Actual Signature is: "
				+ params.get(OAuthConstants.OAUTH_SIGNATURE));

		if (params.get(OAuthConstants.OAUTH_SIGNATURE) == null
				|| !expectedSignature.equals(params
						.get(OAuthConstants.OAUTH_SIGNATURE))) {
			_logger.warn("Authorization Failed: Invalid Signature");
			throw new OAuthException(AuthServiceErrors.INVALID_SIGNATURE,
					Status.UNAUTHORIZED);
		}
	}



}
