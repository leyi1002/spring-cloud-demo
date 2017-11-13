package com.jay.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class CloudDashbordApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudDashbordApplication.class, args);
	}
}
