package com.example.Kino_CMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.Kino_CMS.repository")
public class KinoCmsApplication {
	public static void main(String[] args) {
		SpringApplication.run(KinoCmsApplication.class, args);
	}
}