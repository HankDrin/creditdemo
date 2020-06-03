package com.plat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(scanBasePackages = { "com.creditdemo" })
@ServletComponentScan
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class CreditdemoApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(CreditdemoApplication.class, args);
	}

}
