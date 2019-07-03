package com.libpay.dispatcher;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
@EnableAspectJAutoProxy
public class Application {
	private static Logger logger = LoggerFactory.getLogger("Application");



	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
