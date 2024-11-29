package com.project.btl_mmt1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class BtlMmt1Application {

	public static void main(String[] args) {

		SpringApplication.run(BtlMmt1Application.class, args);
	}

}