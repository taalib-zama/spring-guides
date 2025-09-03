package com.springguide.oauth2.authSample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.springguide.oauth2.authSample.repo")
public class AuthSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthSampleApplication.class, args);
	}

}
