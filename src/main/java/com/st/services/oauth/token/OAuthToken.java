package com.st.services.oauth.token;

import java.util.Date;

public class OAuthToken {
    
    private long id;
    private long wmCustomerId;
    private long thirdPartyId;
    private String tokenValue;
    private String tokenType;
    private String tokenSecret;
    private String refreshToken;
    private boolean isExpired;
    private String oauthVersion;
    private GrantType grantType;
    private Date expirationDate;
    private Date issueDate;
    
    public Date getIssueDate() {
        return issueDate;
    }
    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getWmCustomerId() {
        return wmCustomerId;
    }
    public void setWmCustomerId(long wmCustomerId) {
        this.wmCustomerId = wmCustomerId;
    }
    public long getThirdPartyId() {
        return thirdPartyId;
    }
    public void setThirdPartyId(long thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }
    public String getTokenValue() {
        return tokenValue;
    }
    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }
    public String getTokenType() {
        return tokenType;
    }
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    public String getTokenSecret() {
        return tokenSecret;
    }
    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }
    public String getRefreshToken() {
        return refreshToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public boolean isExpired() {
        return isExpired;
    }
    public void setExpired(boolean isExpired) {
        this.isExpired = isExpired;
    }
    public String getOauthVersion() {
        return oauthVersion;
    }
    public void setOauthVersion(String oauthVersion) {
        this.oauthVersion = oauthVersion;
    }
    public GrantType getGrantType() {
        return grantType;
    }
    public void setGrantType(GrantType grantType) {
        this.grantType = grantType;
    }
    public Date getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
    
    


}
