package com.sseung.chating;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ChatingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatingServerApplication.class, args);
	}
	
}
