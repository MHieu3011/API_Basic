package com.vccorp.dto;

import java.sql.Time;

public abstract class AbstractDTO {

	private Long id;
	private Time createdDate;
	private String createdBy;
	private Time modifiedDate;
	private String modifiedBy;
	private long[] ids;

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

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public long[] getIds() {
		return ids;
	}

	public void setIds(long[] ids) {
		this.ids = ids;
	}

}
