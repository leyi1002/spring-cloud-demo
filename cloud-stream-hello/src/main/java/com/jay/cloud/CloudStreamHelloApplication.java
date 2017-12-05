package com.jay.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.binder.Binder;
import org.springframework.cloud.stream.binder.rabbit.config.RabbitServiceAutoConfiguration;
import org.springframework.cloud.stream.binder.rabbit.provisioning.RabbitExchangeQueueProvisioner;

@SpringBootApplication
public class CloudStreamHelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudStreamHelloApplication.class, args);
	}
}
