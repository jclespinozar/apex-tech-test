package com.worker.apex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class ApexWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApexWorkerApplication.class, args);
	}

}
