package com.plat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 服务中心
 *
 */
@SpringBootApplication(exclude= { DataSourceAutoConfiguration.class})
@EnableZuulProxy
@EnableEurekaServer
public class PlatEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatEurekaApplication.class, args);
    }
}
