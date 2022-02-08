package com.vccorp.model;

import java.sql.Time;

public abstract class AbstractModel {

	private Long id;
	private Time createdDate;
	private String createdBy;
	private Time modifiedDate;
	private String modifiedby;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Time getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Time createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Time getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Time modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}

}
