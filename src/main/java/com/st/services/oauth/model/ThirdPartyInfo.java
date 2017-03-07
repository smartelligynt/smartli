package com.st.services.oauth.model;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.cxf.common.util.StringUtils;

@XmlRootElement()
public class ThirdPartyInfo {

	private long id;
	private String shortName;
	private String longName;
	private String status;
	private String consumerKey;
	private String consumerSecret;
	private String providerKey;
	private String providerSecret;
	private String comsumerSignMethod;
	private String providerSignMethod;
	private String stEncryptKey;
	private Date stEncryptKeyExpireTime;

	private static final Map<String, ThirdPartyInfo> TP_MAP_BY_ID = new Hashtable<String, ThirdPartyInfo>();
	private static final Map<String, ThirdPartyInfo> TP_MAP_BY_KEY = new Hashtable<String, ThirdPartyInfo>();

	@XmlElement()
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@XmlElement()
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@XmlElement()
	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	@XmlElement()
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@XmlElement()
	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	@XmlElement()
	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	@XmlElement()
	public String getProviderKey() {
		return providerKey;
	}

	public void setProviderKey(String providerKey) {
		this.providerKey = providerKey;
	}

	@XmlElement()
	public String getProviderSecret() {
		return providerSecret;
	}

	public void setProviderSecret(String providerSecret) {
		this.providerSecret = providerSecret;
	}

	@XmlElement()
	public String getConsumerSignMethod() {
		return comsumerSignMethod;
	}

	public void setComsumerSignMethod(String comsumerSignMethod) {
		this.comsumerSignMethod = comsumerSignMethod;
	}

	@XmlElement()
	public String getProviderSignMethod() {
		return providerSignMethod;
	}

	public void setProviderSignMethod(String providerSignMethod) {
		this.providerSignMethod = providerSignMethod;
	}

	public static synchronized void putTPInfo(ThirdPartyInfo tpInfo) {
		if (!StringUtils.isEmpty(tpInfo.getShortName())) {
			TP_MAP_BY_ID.put(tpInfo.getShortName().toUpperCase(), tpInfo);
		}
		if (!StringUtils.isEmpty(tpInfo.getProviderKey())) {
			TP_MAP_BY_KEY.put(tpInfo.getProviderKey(), tpInfo);
		}
	}

	public static synchronized ThirdPartyInfo getTPInfoById(String tpId) {
		return TP_MAP_BY_ID.get(tpId.toUpperCase());
	}

	public static synchronized ThirdPartyInfo getTPInfoByProviderKey(String key) {
		return TP_MAP_BY_KEY.get(key);
	}

	public static synchronized void clearTPDetails() {
		TP_MAP_BY_ID.clear();
		TP_MAP_BY_KEY.clear();
	}

	public void setStEncryptKey(String stEncryptKey) {
		this.stEncryptKey = stEncryptKey;
	}

	@XmlElement()
	public String getStEncryptKey() {
		return stEncryptKey;
	}

	@XmlElement
	public Date getStEncryptKeyExpireTime() {
		return stEncryptKeyExpireTime;
	}

	public void setWmEncryptKeyExpireTime(Date wmEncryptKeyExpireTime) {
		this.stEncryptKeyExpireTime = wmEncryptKeyExpireTime;
	}
}
