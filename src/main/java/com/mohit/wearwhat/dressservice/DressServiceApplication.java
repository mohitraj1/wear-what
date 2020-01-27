package com.mohit.wearwhat.dressservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.mohit.wearwhat.dressservice.*"})
public class DressServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DressServiceApplication.class, args);
	}

}
