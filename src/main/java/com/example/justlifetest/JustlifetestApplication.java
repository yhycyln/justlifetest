package com.example.justlifetest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.justlifetest.repository")
@EntityScan("com.example.justlifetest.model")
public class JustlifetestApplication {

	public static void main(String[] args) {
		SpringApplication.run(JustlifetestApplication.class, args);
	}

}
