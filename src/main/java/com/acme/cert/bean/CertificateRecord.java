package com.acme.cert.bean;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class CertificateRecord {
	
	@Id @GeneratedValue
	Long id;
	String name;
	String certificate;
	LocalDate creationDate;
	LocalDate expirationDate;
	LocalDate lastModifiedDate;
	
	@PrePersist
    protected void onCreate() {
		creationDate= lastModifiedDate = LocalDate.now();
    }
	
	@PreUpdate
	protected void onUpdate() {
		lastModifiedDate = LocalDate.now();
	}
	
	public LocalDate getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}


	public LocalDate getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(LocalDate lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCertificate() {
		return certificate;
	}


	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}


	public LocalDate getExpirationDate() {
		return expirationDate;
	}


	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}


	public CertificateRecord() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CertificateRecord(String name, String certificate) {
		this();
		this.name = name;
		this.certificate = certificate;
		
	}
	
	
	

}
