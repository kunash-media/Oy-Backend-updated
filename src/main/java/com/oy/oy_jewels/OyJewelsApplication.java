package com.oy.oy_jewels;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@PropertySource(value="classpath:config/application.secrets.properties",ignoreResourceNotFound = true)
@EnableScheduling
public class OyJewelsApplication {

	public static void main(String[] args) {
		SpringApplication.run(OyJewelsApplication.class, args);
	}
}

