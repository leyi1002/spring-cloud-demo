package com.jay.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@EnableTurbine
@EnableEurekaClient
@SpringBootApplication
public class CloudTurbineApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudTurbineApplication.class, args);
	}
}
