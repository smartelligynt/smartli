package com.st.services.oauth.impl;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.st.services.oauth.AuthServiceConstants;
import com.st.services.oauth.OAuthException;
import com.st.services.oauth.TokenServiceConstants;
import com.st.services.oauth.api.TokenService;
import com.st.services.oauth.api.token.generator.KeyGeneratorFactory;
import com.st.services.oauth.biz.AuthServiceBiz;
import com.st.services.oauth.model.AccessTokenResponse;
import com.st.services.oauth.token.KeyAlgorithm;
import com.st.services.oauth.token.KeySecretPair;
import com.st.services.oauth.util.OAuthUtil;
import com.st.services.util.Util;
@Service("tokenService")
public class TokenServiceImpl implements TokenService{
	@Autowired
	private AuthServiceBiz authServiceBiz;
	

		private static final Logger _logger = Logger.getLogger(TokenService.class
				.getName());

		public Response revokeAccess(final MultivaluedMap<String, String> params) {
			try {

				

				Map<String, String> convertedMap = OAuthUtil
						.getMapWithUppercaseKeys(params);

				authServiceBiz.revokeAccess(convertedMap);

			} catch (OAuthException oe) {
				return Response.status(oe.getHttpStatus()).entity(oe.getError())
						.build();
			} catch (Exception e) {
				_logger.severe("Unknown Exception occured in TokenService.revokeAccess()");
				e.printStackTrace();
				OAuthException oae = OAuthUtil.getOAuthExceptionFromUnknown(e);
				return Response.status(oae.getHttpStatus()).entity(oae.getError())
						.build();
			}
			_logger.info("Revoke Access call Successful");
			return Response.status(Status.OK).build();

		}


		public Response getAuthorizationCode(
				final MultivaluedMap<String, String> params) {
			String verificationCode = null;
			try {

				//WMAuthService wmAuthService = new WMAuthServiceImpl();

				Map<String, String> convertedMap = OAuthUtil
						.getMapWithUppercaseKeys(params);

				verificationCode = authServiceBiz.getAuthorizationCode(convertedMap);

			} catch (OAuthException oe) {
				return Response.status(oe.getHttpStatus()).entity(oe.getError())
						.build();
			} catch (Exception e) {
				_logger.severe("Unknown Exception occured in TokenService.getAuthorizationCode()");
				e.printStackTrace();
				OAuthException oae = OAuthUtil.getOAuthExceptionFromUnknown(e);
				return Response.status(oae.getHttpStatus()).entity(oae.getError())
						.build();
			}
			_logger.info("Get Authorization Code call Successful");
			return Response.status(Status.OK).entity(verificationCode).build();

		}


		public Response getAccessToken(final MultivaluedMap<String, String> params) {
			AccessTokenResponse response = null;
			try {

				//WMAuthService wmAuthService = new WMAuthServiceImpl();

				Map<String, String> convertedMap = OAuthUtil
						.getMapWithLowercaseKeys(params);

				response = authServiceBiz.getAccessToken(convertedMap);

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
			_logger.info("Get Access Token call Successful");
			return Response.status(Status.OK).entity(response).build();

		}


		public Response getKeySecret(@Context UriInfo ui) {
			KeySecretPair ksPair = new KeySecretPair();

			try {

				Map<String, String> convertedMap = OAuthUtil
						.getMapWithLowercaseKeys(ui.getQueryParameters());
				//@TO-DO -- ST Fix
				/*ValidationUtil.validateForMandatoryParams(convertedMap,
						AuthServiceConstants.WM_KEY_SECRET_MAN_LIST);*/

				getKeySecretPair(ksPair, convertedMap);

			} catch (OAuthException oe) {
				return Response.status(oe.getHttpStatus()).entity(oe.getError())
						.build();
			} catch (Exception e) {
				_logger.severe("Unknown Exception occured in TokenService.getKeySecret()");
				e.printStackTrace();
				OAuthException oae = OAuthUtil.getOAuthExceptionFromUnknown(e);
				return Response.status(oae.getHttpStatus()).entity(oae.getError())
						.build();
			}
			_logger.info("Get Key Secret call Successful");
			return Response.status(Status.CREATED).entity(ksPair).build();

		}

		private void getKeySecretPair(KeySecretPair ksPair,
				Map<String, String> convertedMap) throws NoSuchAlgorithmException,
				InstantiationException, IllegalAccessException,
				ClassNotFoundException {
			String strAlgorithm = convertedMap.get(TokenServiceConstants.ALGORITHM);
			KeyAlgorithm algorithm = Util.isEmpty(strAlgorithm)
					|| KeyAlgorithm.valueOf(strAlgorithm.toUpperCase()) == null ? KeyAlgorithm.DH
					: KeyAlgorithm.valueOf(strAlgorithm.toUpperCase());
			KeySecretPair ksp = KeyGeneratorFactory.getInstance(algorithm)
					.generateValue(convertedMap.get(TokenServiceConstants.BASE));

			int key_size = getSize(
					convertedMap.get(TokenServiceConstants.KEY_SIZE), ksp.getKey(),
					TokenServiceConstants.PROVIDER_KEY_SIZE);

			int secret_size = getSize(
					convertedMap.get(TokenServiceConstants.SECRET_SIZE),
					ksp.getSecret(), TokenServiceConstants.PROVIDER_SECRET_SIZE);

			ksPair.setKey(ksp.getKey().substring(0, key_size));
			ksPair.setSecret(ksp.getSecret().substring(0, secret_size));
		}

		private int getSize(String size, String ks, int defaultSize) {

			if (Util.isEmpty(size) || Integer.parseInt(size) <= 0
					|| ks.length() < Integer.parseInt(size)) {
				return defaultSize;
			}

			return Integer.parseInt(size);
		}

	

}
