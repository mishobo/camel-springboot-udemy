package com.husseinabdallah.camelspringbootudemy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan(basePackages = "com.husseinabdallah.camelspringbootudemy.beans")
public class CamelSpringBootUdemyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamelSpringBootUdemyApplication.class, args);
	}

}
