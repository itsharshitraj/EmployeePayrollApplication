package com.tit.employeepayrollapp;

import com.tit.employeepayrollapp.config.AppConfig;
import com.tit.employeepayrollapp.config.ConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
public class EmployeepayrollappApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeepayrollappApplication.class, args);
	}

}
