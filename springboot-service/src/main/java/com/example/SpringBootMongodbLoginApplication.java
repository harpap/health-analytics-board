package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.FileCopyUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;

@SpringBootApplication
public class SpringBootMongodbLoginApplication {
	private static final Logger logger = LoggerFactory.getLogger(SpringBootMongodbLoginApplication.class);

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// System.out.println("Classpath: " + System.getProperty("java.class.path"));
		// Create directories if they don't exist
		File directory = new File("ssl/");
		if (!directory.exists()) {
			directory.mkdirs(); // This will create all necessary parent directories
		}
		// Load the resource as an InputStream
		InputStream truststoreInputStream = SpringBootMongodbLoginApplication.class.getClassLoader().getResourceAsStream("ssl/kafka.truststore.jks");
		InputStream keystoreInputStream = SpringBootMongodbLoginApplication.class.getClassLoader().getResourceAsStream("ssl/kafka.keystore.certh.jks");
	
		// Check if the resources are found
		if (truststoreInputStream == null || keystoreInputStream == null) {
			throw new FileNotFoundException("Truststore or keystore file not found");
		}
	
		// Copy the InputStreams to the desired location
		FileCopyUtils.copy(truststoreInputStream, new FileOutputStream("ssl/kafka.truststore.jks"));
		FileCopyUtils.copy(keystoreInputStream, new FileOutputStream("ssl/kafka.keystore.certh.jks"));
	
		SpringApplication.run(SpringBootMongodbLoginApplication.class, args);
		logger.info("Running....");
		System.out.println("lllllllllllllllllll");
	}

}
