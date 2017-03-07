package com.st.services.oauth.token;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;


@XmlRootElement
public class KeySecretPair {
	
	private String key;
	private String secret;
	
	@XmlElement
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	@XmlElement
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}

}
