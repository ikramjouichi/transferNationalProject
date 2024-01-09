package com.ensa.microservicetransfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MicroserviceTransferApplication {

	public static void main(String[] args) {
		System.out.println("welcome to transfer service");
		SpringApplication.run(MicroserviceTransferApplication.class, args);
	}
}
