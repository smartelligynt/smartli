package com.st.services.oauth.domain.entity;

import java.util.Date;

import javax.persistence.Column;

public class BaseEntity {



	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATED_DTM")
	private Date createdDTM;

	@Column(name = "MODIFIED_BY")
	private String modifiedBy;

	@Column(name = "MODIFIED_DTM")
	private Date modifiedDTM;



	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDTM() {
		return createdDTM;
	}

	public void setCreatedDTM(Date createdDTM) {
		this.createdDTM = createdDTM;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDTM() {
		return modifiedDTM;
	}

	public void setModifiedDTM(Date modifiedDTM) {
		this.modifiedDTM = modifiedDTM;
	}

}
