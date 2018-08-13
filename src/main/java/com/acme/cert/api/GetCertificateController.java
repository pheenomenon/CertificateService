package com.acme.cert.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.acme.cert.bean.CertificateRecord;
import com.acme.cert.service.CertificateService;

@RestController
public class GetCertificateController {

	@Autowired
	CertificateService serv;

	@RequestMapping("/cert/{domain}")
	public @ResponseBody CertificateRecord requestCertificate(@PathVariable String domain) {
		
		//get certificate for domain
		CertificateRecord certificate = serv.getCertificate(domain);
		//return "SSL certificate :" + certificate.getCertificate();
		return certificate;
	}
	
}
