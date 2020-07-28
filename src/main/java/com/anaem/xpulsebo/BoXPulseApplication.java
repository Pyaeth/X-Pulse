package com.anaem.xpulsebo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@SpringBootApplication
public class BoXPulseApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(BoXPulseApplication.class, args);
	}

}
