package com.st.services.oauth.domain.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.cxf.common.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.st.services.oauth.util.GrantType;
import com.st.services.oauth.util.JsonUtil;

/**
 * Entity bean with JPA annotations Hibernate provides JPA implementation
 * 
 * @author pankaj
 *
 */
@Entity
@Table(name = "THIRDPARTY_INFO")
public class ThirdPartyInfoEntity extends BaseEntity {

	// @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "SHORT_NAME")
	private String shortName;

	@Column(name = "LONG_NAME")
	private String name;

	@Column(name = "CLIENT_STATUS")
	private String clientStatus;

	@Column(name = "CLIENT_ID")
	private String clientId;

	@Column(name = "CLIENT_SECRET")
	private String clientSecret;

	@Column(name = "CLIENT_GRANTS")
	private String clientGrants;

	@Column(name = "AUTHORIZE_URL")
	private String authorizeEndpoint;

	@Column(name = "ACCESS_URL")
	private String accessEndpoint;

	@Column(name = "RESOURCE_URL")
	private String resourceEndpoint;

	@Column(name = "PROVIDER_ID")
	private String providerId;

	@Column(name = "PROVIDER_SECRET")
	private String providerSecret;

	@Column(name = "PROVIDER_STATUS")
	private String providerStatus;

	@Column(name = "OUT_HEADERS_MAP")
	private String outHeadersJson;

	@Column(name = "OUT_REQ_KEYS_4GRANT")
	private String outRequestKeys4Grant;

	@Column(name = "OUT_REQ_BODY_4GRANT")
	private String outRequestBody4Grant;

	@Column(name = "IN_RESPONSE_KEYS")
	private String inResponseKeys;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClientStatus() {
		return clientStatus;
	}

	public void setClientStatus(String clientStatus) {
		this.clientStatus = clientStatus;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getClientGrants() {
		return clientGrants;
	}

	public void setClientGrants(String clientGrants) {
		this.clientGrants = clientGrants;
	}

	public String getAuthorizeEndpoint() {
		return authorizeEndpoint;
	}

	public void setAuthorizeEndpoint(String authorizeEndpoint) {
		this.authorizeEndpoint = authorizeEndpoint;
	}

	public String getAccessEndpoint() {
		return accessEndpoint;
	}

	public void setAccessEndpoint(String accessEndpoint) {
		this.accessEndpoint = accessEndpoint;
	}

	public String getResourceEndpoint() {
		return resourceEndpoint;
	}

	public void setResourceEndpoint(String resourceEndpoint) {
		this.resourceEndpoint = resourceEndpoint;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getProviderSecret() {
		return providerSecret;
	}

	public void setProviderSecret(String providerSecret) {
		this.providerSecret = providerSecret;
	}

	public String getProviderStatus() {
		return providerStatus;
	}

	public void setProviderStatus(String providerStatus) {
		this.providerStatus = providerStatus;
	}

	public String getOutHeadersJson() {
		return outHeadersJson;
	}

	public void setOutHeadersJson(String outHeadersJson) {
		this.outHeadersJson = outHeadersJson;
	}

	public String getOutRequestKeys4Grant() {
		return outRequestKeys4Grant;
	}

	public void setOutRequestKeys4Grant(String outRequestKeys4Grant) {
		this.outRequestKeys4Grant = outRequestKeys4Grant;
	}

	public String getOutRequestBody4Grant() {
		return outRequestBody4Grant;
	}

	public void setOutRequestBody4Grant(String outRequestBody4Grant) {
		this.outRequestBody4Grant = outRequestBody4Grant;
	}

	public List<String> getOutRequestKeys(GrantType grantType) {
		if (StringUtils.isEmpty(this.outRequestKeys4Grant)) {
			return null;
		}

		Map<String, List<String>> accessTokenResponse = (Map<String, List<String>>) JsonUtil
				.convertJsonToMap(this.outRequestKeys4Grant,
						new TypeReference<HashMap<String, List<String>>>() {
						});

		return accessTokenResponse.get(grantType.name());

	}

	public String getInResponseKeys() {
		return inResponseKeys;
	}

	public void setInResponseKeys(String inResponseKeys) {
		this.inResponseKeys = inResponseKeys;
	}

	public Map<String, String> getOutHeadersMap() {
		if (StringUtils.isEmpty(this.outHeadersJson)) {
			return null;
		}

		return (Map<String, String>) JsonUtil.convertJsonToMap(
				this.outHeadersJson,
				new TypeReference<HashMap<String, String>>() {
				});

	}

	public String getOutRequestBodyTemplate(GrantType grantType) {
		if (StringUtils.isEmpty(this.outRequestKeys4Grant)) {
			return null;
		}

		Map<String, String> accessTokenResponse = (Map<String, String>) JsonUtil
				.convertJsonToMap(this.outRequestBody4Grant,
						new TypeReference<HashMap<String, String>>() {
						});

		return accessTokenResponse.get(grantType.name());

	}

	public Map<String, String> getInResponseKeysMap() {
		if (StringUtils.isEmpty(this.inResponseKeys)) {
			return null;
		}

		return (Map<String, String>) JsonUtil.convertJsonToMap(
				this.inResponseKeys,
				new TypeReference<HashMap<String, String>>() {
				});

	}

}
