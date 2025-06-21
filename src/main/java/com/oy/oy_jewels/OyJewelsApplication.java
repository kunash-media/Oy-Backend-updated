package com.oy.oy_jewels;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class OyJewelsApplication {

	// This MUST be the very first thing that happens
	static {
		// Set multiple variations of the property to ensure it's caught
		System.setProperty("org.apache.tomcat.util.http.fileupload.FileUploadBase.DEFAULT_FILE_COUNT_MAX", "200");
		System.setProperty("org.apache.tomcat.util.http.fileupload.impl.FileUploadBase.FILE_COUNT_MAX", "200");
		System.setProperty("org.apache.tomcat.util.http.fileupload.FileUploadBase.FILE_COUNT_MAX", "200");

		// Debug: Print to verify properties are set
		System.out.println("File count limit set to: " +
				System.getProperty("org.apache.tomcat.util.http.fileupload.FileUploadBase.DEFAULT_FILE_COUNT_MAX"));
	}

	public static void main(String[] args) {
		// Set properties again just before starting Spring
		System.setProperty("org.apache.tomcat.util.http.fileupload.FileUploadBase.DEFAULT_FILE_COUNT_MAX", "200");
		System.setProperty("org.apache.tomcat.util.http.fileupload.impl.FileUploadBase.FILE_COUNT_MAX", "200");

		SpringApplication.run(OyJewelsApplication.class, args);
	}

}


