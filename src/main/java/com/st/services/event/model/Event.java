package com.st.services.event.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.st.services.util.JsonUtil;


@XmlRootElement(namespace = "com.st.service.event.model")
@XmlAccessorType(XmlAccessType.NONE)
public class Event implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "id")
	private String id;

	@XmlElement(name = "en")
	private String eventName;

	@XmlElement(name = "ev")
	private String eventValue;

	@XmlElement(name = "et")
	private Date eventTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventValue() {
		return eventValue;
	}

	public void setEventValue(String eventValue) {
		this.eventValue = eventValue;
	}

	public Date getEventTime() {
		return eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = this.id == null ? (prime * result) : this.id.hashCode();

		return result;
	}

	@Override
	public boolean equals(final Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (this.getClass() != obj.getClass()) {
			return false;
		}

		final Event other = (Event) obj;
		if ((this.id == other.id)
				|| (this.id != null && other.id != null && this.id
						.equals(other.id))) {
			return true;
		}

		return false;
	}
	
	@Override
	public String toString() {

		try {
			return JsonUtil.convertModelToJson(this);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

	}

}
