package com.gate.tracker.consistency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsistencyApplication {

	public static void main(String[] args) {
		System.out.println("MONGODB_URI = " + System.getenv("MONGODB_URI"));
		SpringApplication.run(ConsistencyApplication.class, args);
	}
}

