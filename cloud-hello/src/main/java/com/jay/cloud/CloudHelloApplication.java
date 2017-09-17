package com.jay.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class CloudHelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudHelloApplication.class, args);
	}
}
