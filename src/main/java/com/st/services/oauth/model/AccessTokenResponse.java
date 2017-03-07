package com.st.services.oauth.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="accessTokenResponse")
public class AccessTokenResponse {
	
	private String wmCustomerId;
	
	private TokenResponse token;
	
	@XmlElement
	public String getWmCustomerId() {
		return wmCustomerId;
	}

	public void setWmCustomerId(String wmCustomerId) {
		this.wmCustomerId = wmCustomerId;
	}

	@XmlElement(name="Token")
	public TokenResponse getToken() {
		return token;
	}

	public void setToken(TokenResponse token) {
		this.token = token;
	}

}