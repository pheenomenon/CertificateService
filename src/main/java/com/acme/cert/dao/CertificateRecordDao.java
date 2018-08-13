package com.acme.cert.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.acme.cert.bean.CertificateRecord;

public interface CertificateRecordDao extends CrudRepository<CertificateRecord, Long>{
	
	List<CertificateRecord> findAll();
	
	CertificateRecord findByName(String domainName);

}
