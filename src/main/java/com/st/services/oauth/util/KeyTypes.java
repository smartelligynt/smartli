package com.st.services.oauth.util;

public enum KeyTypes {
	code("<code>"), redirect_uri("<redirect_uri>"), access_token("<access_token>"),
	refresh_token("<refresh_token>"), username("<username>"), password("<password>");

	private String tag;

	KeyTypes(String tag) {
		this.tag = tag;
	}

	public String tag() {
		return tag;
	}

}
