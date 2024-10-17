package com.ias.dynamodb.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.ias.dynamodb"})
public class MicroserviceDynamodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceDynamodbApplication.class, args);
	}

}
