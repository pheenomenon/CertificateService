package com.acme.cert.service;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;


import com.acme.cert.bean.CertificateRecord;
import com.acme.cert.dao.CertificateRecordDao;

@RunWith(MockitoJUnitRunner.class)
public class CertificateServiceTest {

	private CertificateService certService;
	
	@Mock
	private CertificateRecordDao mockCertDao;

	private CertificateRecord mockRec = new CertificateRecord();
	
	@Before
	public void setup() {
		certService = new CertificateService();
	}
	@Test
	public void test_getCertificate_CacheValid_lookupFromCache() {
		//Arrange
		mockRec.setCertificate("random-certificate");
		mockRec.setName("www.fan-kub-server.com");
		//created 10 days ago with an expiration age of 90 days
		mockRec.setCreationDate(LocalDate.now().minusDays(10));
		mockRec.setExpirationDate(LocalDate.now().plusDays(80));
		
		when(mockCertDao.findByName(anyString())).thenReturn(mockRec);
		certService.setCertDao(mockCertDao);
		
		//Act
		CertificateRecord retCertRec = certService.getCertificate("www.fan-kub-server.com");
		
		//Assert
		/*
		 * 1. Cache is valid as lt 30 days
		 * 2. Cert is Not expired
		 * 3. shud return from db
		 */
		assertThat(retCertRec.getCertificate()).isEqualTo("random-certificate");
		//fail("Not yet implemented");
	}
	
	
	@Test
	public void test_getCertificate_CreateNew() {
		//Arrange
		mockRec.setCertificate("random-certificate");
		mockRec.setName("www.fan-kub-server.com");
		//created 100 days ago with an expiration age of 90 days : cert expired
		mockRec.setCreationDate(LocalDate.now().minusDays(100));
		mockRec.setExpirationDate(LocalDate.now().minusDays(10));
		
		when(mockCertDao.findByName(anyString())).thenReturn(null);
		certService.setCertDao(mockCertDao);
		
		//Act
		CertificateRecord retCertRec = certService.getCertificate("www.fan-kub-server.com");
		
		//Assert
		/*
		 * 1. Cache is invalid as gt 30 days
		 * 2. Cert is expired
		 * 3. shud return from external service
		 */
		assertThat(retCertRec.getCertificate()).isNotEqualTo("random-certificate");
		//fail("Not yet implemented");
	}

}
