package com.jrl.myemployees;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"com.jrl.myemployees"})
public class MyEmployeesApplication {
	private static final Logger logger = LoggerFactory.getLogger(MyEmployeesApplication.class);
	
	public static void main(String[] args) {
        SpringApplication.run(MyEmployeesApplication.class, args);
		logger.info("MyEmployees RESTful API has started");
	}

}
