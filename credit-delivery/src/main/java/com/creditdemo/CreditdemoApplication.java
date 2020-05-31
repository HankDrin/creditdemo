package com.creditdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(scanBasePackages = { "com.creditdemo" })
@ServletComponentScan
public class CreditdemoApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(CreditdemoApplication.class, args);
	}

}
