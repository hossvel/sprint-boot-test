package com.devhoss.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class SprintBootTestApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(SprintBootTestApplication.class, args);
		System.out.println("Hola Mundo!!");
	}

}
