package com.jay.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.BusAutoConfiguration;
import org.springframework.cloud.bus.endpoint.AbstractBusEndpoint;
import org.springframework.cloud.bus.endpoint.BusEndpoint;
import org.springframework.cloud.bus.event.EnvironmentChangeListener;
import org.springframework.cloud.bus.event.RefreshListener;
import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;
import org.springframework.cloud.bus.event.SentApplicationEvent;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

@EnableEurekaClient
@SpringBootApplication
public class CloudConfigClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudConfigClientApplication.class, args);

	}
}
