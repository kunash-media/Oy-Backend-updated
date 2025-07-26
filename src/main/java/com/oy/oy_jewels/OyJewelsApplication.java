package com.oy.oy_jewels;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value="classpath:config/application.secrets.properties",ignoreResourceNotFound = true)
public class OyJewelsApplication {

	public static void main(String[] args) {
		SpringApplication.run(OyJewelsApplication.class, args);
	}

}

