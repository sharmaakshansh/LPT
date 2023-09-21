package com.freshlybrewed.raterecomm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.freshlybrewed")
public class RaterecommApplication {

	public static void main(String[] args) {
		SpringApplication.run(RaterecommApplication.class, args);
	}

}
