package com.st.services.event.model;

public class EventResponse {

	private ResponseStatus status;
	private Object payload;
	private SmartliError error;

	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public SmartliError getError() {
		return error;
	}

	public void setError(SmartliError error) {
		this.error = error;
	}

}
