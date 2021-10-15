package com.safetynet.alerts.application.integration.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class SpringBootIntegrationConfig {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootIntegrationConfig.class, args);
	}
}
