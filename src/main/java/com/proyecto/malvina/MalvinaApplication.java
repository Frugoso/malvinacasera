package com.proyecto.malvina;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MalvinaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MalvinaApplication.class, args);
	}

}
