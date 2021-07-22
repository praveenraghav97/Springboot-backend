package com.extramarks.springboot;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.extramarks.springboot.service.EmployeeServiceImpl;

@SpringBootApplication

public class SpringbootBackendApplication {

	public static void main(String[] args) {
		
		new File(EmployeeServiceImpl.uploadDirectory).mkdir();		
		SpringApplication.run(SpringbootBackendApplication.class, args);
	}
}
