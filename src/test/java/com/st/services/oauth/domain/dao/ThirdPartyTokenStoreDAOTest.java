package com.st.services.oauth.domain.dao;

import static org.testng.AssertJUnit.assertTrue;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.st.services.oauth.domain.entity.ThirdPartyTokenStoreEntity;

@ContextConfiguration(locations = { "classpath:META-INF/st-domain-context.xml" })
public class ThirdPartyTokenStoreDAOTest extends
		AbstractTestNGSpringContextTests {

	@Autowired
	ThirdPartyTokenStoreDAO thirdPartyTokenStoreDAO;
	ThirdPartyTokenStoreEntity thirdPartyTokenStoreEntity = null;
	private static final String TP_NAME = "FACEBOOK";
	private static final String CUST_ID = "CUST001";

	@Test
	public void testSave() {

		thirdPartyTokenStoreEntity = new ThirdPartyTokenStoreEntity();
		thirdPartyTokenStoreEntity.setId(UUID.randomUUID().toString());
		thirdPartyTokenStoreEntity.setTpName(TP_NAME);
		thirdPartyTokenStoreEntity.setCustomerId(CUST_ID);
		thirdPartyTokenStoreEntity.setAccessToken("sampleAuthToken");
		thirdPartyTokenStoreDAO.save(thirdPartyTokenStoreEntity);

	}

	@Test(dependsOnMethods = { "testSave" })
	public void testGetByCustomerIdAndTP() {

		List<ThirdPartyTokenStoreEntity> thirdPartyTokenStore = thirdPartyTokenStoreDAO
				.getByCustomerIdAndTP(CUST_ID, TP_NAME);
		assertTrue(thirdPartyTokenStore.size() == 1);

	}

}
