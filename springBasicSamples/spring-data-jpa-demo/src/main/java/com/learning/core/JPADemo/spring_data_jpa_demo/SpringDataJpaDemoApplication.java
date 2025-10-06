package com.learning.core.JPADemo.spring_data_jpa_demo;

import com.learning.core.JPADemo.spring_data_jpa_demo.entity.User;
import com.learning.core.JPADemo.spring_data_jpa_demo.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDataJpaDemoApplication {


	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaDemoApplication.class, args);
	}

}
