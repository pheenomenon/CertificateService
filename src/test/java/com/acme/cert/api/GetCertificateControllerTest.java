package com.acme.cert.api;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.acme.cert.bean.CertificateRecord;
import com.acme.cert.service.CertificateService;

@RunWith(SpringRunner.class)
@WebMvcTest(GetCertificateController.class)
public class GetCertificateControllerTest {
	
	@MockBean
	private CertificateService certService;
	
	private CertificateRecord mockRec = new CertificateRecord();
	
	@Autowired
	private MockMvc mvc;	
	
	@Test
	public void test() throws Exception {
		//Arrange
		mockRec.setCertificate("random-certificate");
		mockRec.setName("www.fan-kub-server.com");
		mockRec.setCreationDate(LocalDate.now());
		
		when(certService.getCertificate(anyString())).thenReturn(mockRec);
		
		//Act
		ResultActions result = mvc.perform(get("/cert/{domain}","www.fan-kub-server.com"));
		
		//Assert
		
		result.andExpect(status().is2xxSuccessful())
			.andExpect(jsonPath("$.name").value("www.fan-kub-server.com"));
		//fail("Not yet implemented");		
	}

}
