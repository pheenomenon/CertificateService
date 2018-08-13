package com.acme.cert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class CertificateServiceApplication extends SpringBootServletInitializer{

	//for jar
	public static void main(String[] args) {
		SpringApplication.run(CertificateServiceApplication.class, args);

	}

	//for war
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		
		return (builder.sources(CertificateServiceApplication.class));
	}
	
	

}
