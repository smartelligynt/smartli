package com.st.services.oauth.token;

public class ProviderToken extends OAuthToken {

    private String tokenGenMethod;
    private String redirectUri;

    public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public String getTokenGenMethod() {
        return tokenGenMethod;
    }

    public void setTokenGenMethod(String tokenGenMethod) {
        this.tokenGenMethod = tokenGenMethod;
    }
    
    
}
