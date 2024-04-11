package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootMongodbLoginApplication {
	private static final Logger logger = LoggerFactory.getLogger(SpringBootMongodbLoginApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMongodbLoginApplication.class, args);
		logger.info("Running...");
	}

}
