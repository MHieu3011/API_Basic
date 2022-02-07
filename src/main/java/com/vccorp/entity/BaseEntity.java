package com.vccorp.entity;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "createddate")
	@CreatedDate
	private Time createdDate;

	@Column(name = "createdby")
	@CreatedBy
	private String createdBy;

	@Column(name = "modifieddate")
	@LastModifiedDate
	private Time modifiedDate;

	@Column(name = "modifiedby")
	@LastModifiedBy
	private String modifiedBy;

	public Long getId() {
		return id;
	}

	public Time getCreatedDate() {
		return createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Time getModifiedDate() {
		return modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

}
