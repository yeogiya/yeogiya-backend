package com.yeogiya.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan(basePackages = {"com.yeogiya"})
@EnableJpaRepositories(basePackages = {"com.yeogiya"})
@EnableFeignClients
public class YeogiyaApplication {

	public static void main(String[] args) {
		SpringApplication.run(YeogiyaApplication.class, args);
	}

}
