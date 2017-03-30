package com.st.services.oauth.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.st.services.oauth.domain.ThirdPartyInfoStore;
import com.st.services.oauth.domain.entity.ThirdPartyInfoEntity;

@ContextConfiguration(locations = { "classpath:META-INF/st-domain-context.xml" })
public class ThirdPartyInfoStoreTest extends AbstractTestNGSpringContextTests {
	
	@Autowired
	ThirdPartyInfoStore thirdPartyInfoStore;
	
	@Test
	public void testGetThirdPartyInfo() {
		ThirdPartyInfoEntity tp = thirdPartyInfoStore.get("SMART_THINGS");
		System.out.println("THIRD_PART is: " + tp.getName());
	}

}
