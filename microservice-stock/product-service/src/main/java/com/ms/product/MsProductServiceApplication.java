package com.ms.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsProductServiceApplication.class, args);
	}

}
