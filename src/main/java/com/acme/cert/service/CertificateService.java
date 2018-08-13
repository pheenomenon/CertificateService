package com.acme.cert.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acme.cert.bean.CertificateRecord;
import com.acme.cert.dao.CertificateRecordDao;

@Service
public class CertificateService {
	
	//Caches certificates for a configurable amount of time - 30 days
	public static final int cacheDuration = 30;
	
	@Autowired 
	private CertificateRecordDao certDao;
	
	/**
	 * retrieves certificate for input domain 
	 * @param domainName
	 * @return CertificateRecord
	 */
	public CertificateRecord getCertificate(String domainName) {
		CertificateRecord entry = null;
		//if cert for domain exists in DB/cache
		entry  = getCertFromCache(domainName);

		if(entry != null) {
			if(isCacheExpired(entry)) {
				entry = requestCertificateAgain(domainName);
				storeCertInCache(entry);
			} 
			if(isCertExpired(entry)) {
				//in cache but expired
				entry = renewCertificate(entry);
				storeCertInCache(entry);
	
			}
		} else {
			//not in cache, request for a new one
			entry = createNewCertificate(domainName);
			storeCertInCache(entry);
		}
		
		return entry;		

	}
	
	private boolean isCacheExpired(CertificateRecord rec) {
		boolean isExpired =true;
		//cert was issued or renewed before last 30 days
		isExpired = LocalDate.now().isAfter(rec.getLastModifiedDate().plusDays(cacheDuration));
		
		return isExpired;
	}
	
	/**
	 * 
	 * @param domain
	 * @return
	 */
	private CertificateRecord getCertFromCache(String domain) {
		CertificateRecord entry = certDao.findByName(domain);
		return entry;
	}
	
	public CertificateRecordDao getCertDao() {
		return certDao;
	}

	public void setCertDao(CertificateRecordDao certDao) {
		this.certDao = certDao;
	}

	/**
	 * saves cert in cache/db
	 * @param certRec
	 */
	private void storeCertInCache(CertificateRecord certRec) {
		certDao.save(certRec);
		
	}
	
	/**
	 * checks if the cached cert is expired
	 * @param rec
	 * @return true if expired false otherwise
	 */
	private boolean isCertExpired(CertificateRecord rec) {
		if(rec != null) {
			LocalDate expDate = rec.getExpirationDate();
			if(LocalDate.now().isAfter(expDate)) {
				return true;
			} else {
				return false;
			}
			
		}
		//TODO throw exception rec null
		return false;
	}

	/**
	 * renews the expired cert
	 * @param expRec
	 * @return
	 */
	private CertificateRecord renewCertificate(CertificateRecord expRec) {
		CertificateRecord renewedCert = null;
		renewedCert = createNewCertificate(expRec.getName());
		return renewedCert;
		
	}
	
	/**
	 * external service call
	 * @param domainName
	 * @return
	 */
	private CertificateRecord createNewCertificate(String domainName) {
		CertificateRecord newCert = null; 
		try {
			Thread.sleep(10000);
			String newCertString = UUID.randomUUID().toString();
			int expiryIn = 90;
			newCert = populateCertificateBean(domainName, newCertString,expiryIn);
			
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			
		}
		return newCert;
	}
	
	/**
	 * Assumption: this method will call service to re-request instead of create
	 * requests the cert again 
	 * @param expRec
	 * @return
	 */
	private CertificateRecord requestCertificateAgain(String domain) {
		CertificateRecord renewedCert = null;
		renewedCert = createNewCertificate(domain);
		return renewedCert;
		
	}

	
	/**
	 * create an application specific CertificateRecord based on result from external call
	 * @param name
	 * @param cert
	 * @param days
	 * @return
	 */
	private CertificateRecord populateCertificateBean(String name, String cert, int days) {
		CertificateRecord newCert = new CertificateRecord(name,cert);
		//assume cert has an expiry of 90 days
		newCert.setExpirationDate(LocalDate.now().plusDays(days));
		
		return newCert;
	}
	
}
