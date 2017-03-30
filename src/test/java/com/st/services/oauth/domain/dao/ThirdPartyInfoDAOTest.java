package com.st.services.oauth.domain.dao;

import static org.testng.AssertJUnit.assertTrue;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.st.services.oauth.domain.ThirdPartyInfoStore;
import com.st.services.oauth.domain.entity.ThirdPartyInfoEntity;
import com.st.services.oauth.util.GrantType;
import com.st.services.oauth.util.KeyTypes;
import com.st.services.oauth.util.OAuthConstants;
import com.st.services.oauth.util.StatusType;

@ContextConfiguration(locations = { "classpath:META-INF/st-domain-context.xml" })
public class ThirdPartyInfoDAOTest extends AbstractTestNGSpringContextTests {
	@Autowired
	ThirdPartyInfoDAO thirdPartyInfoDAO;

	@Autowired
	ThirdPartyInfoStore thirdPartyInfoStore;

	private static final String SMART_THINGS = "SMART_THINGS";
	private static final String WINK = "WINK";

	@Test
	public void testSave() {

		for (String tpName : new String[] { SMART_THINGS, WINK }) {
			try {
				ThirdPartyInfoEntity thirdPartyInfo = thirdPartyInfoStore
						.get(tpName);
				if (thirdPartyInfo != null) {
					continue;

				}
				ThirdPartyInfoEntity thirdParty = getTPInfo(tpName);
				if (thirdParty != null) {
					thirdPartyInfoDAO.save(thirdParty);
					thirdPartyInfoStore.add(thirdParty);

					assertTrue(thirdParty.getOutHeadersJson() != null);
					System.out.println(tpName + " HEADERS are: "
							+ thirdParty.getOutHeadersJson());
					assertTrue(thirdParty.getOutHeadersMap().size() >= 1);

					assertTrue(thirdParty.getOutRequestKeys(
							GrantType.authorization_code).size() >= 1);

					assertTrue(thirdParty
							.getOutRequestBodyTemplate(GrantType.authorization_code) != null);
					System.out
							.println("AUTHORIZATION_CODE - REQUEST TEMAPLATE IS: "
									+ thirdParty
											.getOutRequestBodyTemplate(GrantType.authorization_code));
					System.out
							.println("REFRESH_TOKEN - REQUEST TEMAPLATE IS: "
									+ thirdParty
											.getOutRequestBodyTemplate(GrantType.refresh_token));
					System.out
							.println("PASSSWORD - REQUEST TEMAPLATE IS: "
									+ thirdParty
											.getOutRequestBodyTemplate(GrantType.password));

					assertTrue(thirdParty.getInResponseKeysMap().size() == 2);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private ThirdPartyInfoEntity getTPInfo(String tpName) {

		ThirdPartyInfoEntity thirdPartyInfo = null;
		if (tpName.equals(SMART_THINGS)) {

			thirdPartyInfo = new ThirdPartyInfoEntity();
			thirdPartyInfo.setId(UUID.randomUUID().toString());
			thirdPartyInfo.setShortName(SMART_THINGS);
			thirdPartyInfo.setName(SMART_THINGS);
			thirdPartyInfo
					.setAccessEndpoint("https://graph.api.smartthings.com/oauth/token");
			thirdPartyInfo
					.setAuthorizeEndpoint("https://graph.api.smartthings.com/oauth/authorize");
			thirdPartyInfo.setClientGrants(GrantType.authorization_code.name());
			thirdPartyInfo.setClientId("f4522309-772e-445a-860a-92f5e5a7c2c6");
			thirdPartyInfo
					.setClientSecret("d77745cd-cd00-49e9-960b-fd5d65ecf7f8");
			thirdPartyInfo.setClientStatus(StatusType.ACTIVE.name());
			thirdPartyInfo.setOutHeadersJson("{\"" + OAuthConstants.HOST
					+ "\" : \"graph.api.smartthings.com\", \""
					+ OAuthConstants.CONTENT_TYPE + "\" : \""
					+ MediaType.APPLICATION_FORM_URLENCODED + "\", \""
					+ OAuthConstants.ACCEPT_TYPE + "\" : \""
					+ MediaType.APPLICATION_JSON + "\"}");
			thirdPartyInfo
					.setOutRequestKeys4Grant("{\"authorization_code\" : [\"code\",\"redirect_uri\"]}");
			thirdPartyInfo
					.setOutRequestBody4Grant("{\"authorization_code\" : \"grant_type=authorization_code&code=<code>&client_id=f4522309-772e-445a-860a-92f5e5a7c2c6&client_secret=d77745cd-cd00-49e9-960b-fd5d65ecf7f8&cid=c001&redirect_uri=<redirect_uri>\"}");
			thirdPartyInfo.setInResponseKeys("{\""
					+ KeyTypes.access_token.name() + "\" : \""
					+ KeyTypes.access_token.name() + "\", \""
					+ OAuthConstants.ACCESS_TOKEN_EXPIRE_TIME + "\" : \""
					+ "expires_in\"}");

		} else if (tpName.equals(WINK)) {
			thirdPartyInfo = new ThirdPartyInfoEntity();
			thirdPartyInfo.setId(UUID.randomUUID().toString());
			thirdPartyInfo.setShortName(WINK);
			thirdPartyInfo.setName(WINK);
			thirdPartyInfo
					.setAccessEndpoint("https://api.wink.com/oauth2/token");
			thirdPartyInfo
					.setAuthorizeEndpoint("https://api.wink.com/oauth2/authorize");
			thirdPartyInfo.setClientGrants(new StringBuffer(
					GrantType.authorization_code.name()).append(",")
					.append(GrantType.password.name()).append(",")
					.append(GrantType.refresh_token.name()).toString());
			thirdPartyInfo.setClientId("UhCsjmP07RFYoerznyEVjag3rPUr6ThP");
			thirdPartyInfo.setClientSecret("fFZBCaTw9AYOPblgLa-tv4fXhB0Lo5IE");
			thirdPartyInfo.setClientStatus(StatusType.ACTIVE.name());
			thirdPartyInfo.setOutHeadersJson("{\""
					+ OAuthConstants.CONTENT_TYPE + "\" : \""
					+ MediaType.APPLICATION_JSON + "\", \""
					+ OAuthConstants.ACCEPT_TYPE + "\" : \""
					+ MediaType.APPLICATION_JSON + "\"}");
			thirdPartyInfo
					.setOutRequestKeys4Grant("{\"authorization_code\" : [\"code\"], \"refresh_token\" : [\"refresh_token\"], \"password\" : [\"username\", \"password\"]}");
			thirdPartyInfo
					.setOutRequestBody4Grant("{\"authorization_code\" : \"{\\\"client_secret\\\":\\\"fFZBCaTw9AYOPblgLa-tv4fXhB0Lo5IE\\\",\\\"grant_type\\\":\\\"authorization_code\\\",\\\"code\\\":\\\"<code>\\\"}\", "
							+ "\"refresh_token\" : \"{\\\"client_id\\\":\\\"UhCsjmP07RFYoerznyEVjag3rPUr6ThP\\\",\\\"client_secret\\\":\\\"fFZBCaTw9AYOPblgLa-tv4fXhB0Lo5IE\\\",\\\"grant_type\\\":\\\"refresh_token\\\",\\\"refresh_token\\\":\\\"<refresh_token>\\\"}\", "
							+ "\"password\" : \"{\\\"client_id\\\":\\\"UhCsjmP07RFYoerznyEVjag3rPUr6ThP\\\",\\\"client_secret\\\":\\\"fFZBCaTw9AYOPblgLa-tv4fXhB0Lo5IE\\\",\\\"username\\\":\\\"<username>\\\",\\\"password\\\":\\\"<password>\\\",\\\"grant_type\\\":\\\"password\\\"}\"}");

			thirdPartyInfo.setInResponseKeys("{\""
					+ KeyTypes.access_token.name() + "\" : \""
					+ KeyTypes.access_token.name() + "\", \""
					+ KeyTypes.refresh_token.name() + "\" : \""
					+ KeyTypes.refresh_token.name() + "\"}");

		}

		return thirdPartyInfo;

	}

	@Test
	public void testGet() {

		try {
			List<ThirdPartyInfoEntity> tpList = thirdPartyInfoDAO.getAll();
			for (ThirdPartyInfoEntity tp : tpList) {
				System.out.println("THIRD_PARTY : " + tp.getShortName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
