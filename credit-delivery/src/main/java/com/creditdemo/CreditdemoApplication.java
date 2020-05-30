package com.creditdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = { "com.creditdemo" }, exclude = { DataSourceAutoConfiguration.class })
public class CreditdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditdemoApplication.class, args);
	}

}
