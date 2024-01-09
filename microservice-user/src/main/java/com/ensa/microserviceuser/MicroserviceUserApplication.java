package com.ensa.microserviceuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroserviceUserApplication {

	public static void main(String[] args) {
		System.out.println("bonjour user Service : ");
		SpringApplication.run(MicroserviceUserApplication.class, args);
	}

}
