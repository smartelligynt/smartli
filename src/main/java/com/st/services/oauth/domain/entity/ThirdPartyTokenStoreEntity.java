package com.st.services.oauth.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "3P_TOKEN_STORE")
public class ThirdPartyTokenStoreEntity extends BaseEntity {

	@Id
	@Column(name = "ID")
	private String id;
	@Column(name = "TP_NAME")
	private String tpName;
	@Column(name = "CUSTOMER_ID")
	private String customerId;
	@Column(name = "ACCESS_TOKEN")
	private String accessToken;
	@Column(name = "ACCESS_TOKEN_CREATE_TS")
	private Date accessTokenCreateDTM;
	@Column(name = "ACCESS_TOKEN_EXPIRE_TS")
	private Date accessTokenExpiryDTM;
	@Column(name = "REFRESH_TOKEN")
	private String refreshToken;
	@Column(name = "REFRESH_TOKEN_CREATE_TS")
	private Date refreshTokenCreateDTM;
	@Column(name = "REFRESH_TOKEN_EXPIRE_TS")
	private Date refreshTokenExpiryDTM;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTpName() {
		return tpName;
	}

	public void setTpName(String tpName) {
		this.tpName = tpName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Date getAccessTokenCreateDTM() {
		return accessTokenCreateDTM;
	}

	public void setAccessTokenCreateDTM(Date accessTokenCreateDTM) {
		this.accessTokenCreateDTM = accessTokenCreateDTM;
	}

	public Date getAccessTokenExpiryDTM() {
		return accessTokenExpiryDTM;
	}

	public void setAccessTokenExpiryDTM(Date accessTokenExpiryDTM) {
		this.accessTokenExpiryDTM = accessTokenExpiryDTM;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Date getRefreshTokenCreateDTM() {
		return refreshTokenCreateDTM;
	}

	public void setRefreshTokenCreateDTM(Date refreshTokenCreateDTM) {
		this.refreshTokenCreateDTM = refreshTokenCreateDTM;
	}

	public Date getRefreshTokenExpiryDTM() {
		return refreshTokenExpiryDTM;
	}

	public void setRefreshTokenExpiryDTM(Date refreshTokenExpiryDTM) {
		this.refreshTokenExpiryDTM = refreshTokenExpiryDTM;
	}

}
