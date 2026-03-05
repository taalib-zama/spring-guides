package com.sample.electronicStore.electronicStore;

import com.sample.electronicStore.electronicStore.config.AppConstants;
import com.sample.electronicStore.electronicStore.entities.Role;
import com.sample.electronicStore.electronicStore.entities.User;
import com.sample.electronicStore.electronicStore.repo.RoleRepository;
import com.sample.electronicStore.electronicStore.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableWebMvc
public class ElectronicStoreApplication implements CommandLineRunner {

	public static void main(String[] args) {

        SpringApplication.run(ElectronicStoreApplication.class, args);
	}


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public void run(String... args) throws Exception {
        //System.out.printf("Encoded password : %s",passwordEncoder.encode("password12223"));


        //This code is performing database initialization when the Spring Boot application starts up. It implements the CommandLineRunner interface, so the run method executes after the application context is loaded.
        //Here's what it does:
        //Role Creation
        //Admin Role: Checks if "ROLE_ADMIN" exists in the database
        //If not found, creates a new Role with name "ROLE_" + AppConstants.ROLE_ADMIN and saves it
        //Normal Role: Checks if "ROLE_NORMAL" exists in the database
        //If not found, creates a new Role with name "ROLE__" + AppConstants.ROLE_NORMAL and saves it
        //Note: There's a typo here - it uses "ROLE__" (double underscore) instead of "ROLE_"
        //Default Admin User Creation
        //Admin User: Checks if user with email "mailto:durgesh@gmail.com" exists
        //If not found, creates a default admin user with:
        //Name: "durgesh"
        //Email: "mailto:durgesh@gmail.com"

        //Random UUID as user ID
        //Purpose
        //This is a common pattern for:
        //Seeding the database with essential roles and a default admin user
        //Ensuring the application has required data to function properly on first startup
        //Creating a default admin account for initial system access
        Role roleAdmin = roleRepository.findByName("ROLE_" + AppConstants.ROLE_ADMIN).orElse(null);
        if (roleAdmin == null) {
            Role role1 = new Role();
            role1.setRoleId(UUID.randomUUID().toString());
            role1.setName("ROLE_" + AppConstants.ROLE_ADMIN);
            roleAdmin = roleRepository.save(role1);  // Assign saved role
        }

        Role roleNormal = roleRepository.findByName("ROLE_" + AppConstants.ROLE_NORMAL).orElse(null);  // Fix typo
        if (roleNormal == null) {
            Role role2 = new Role();
            role2.setRoleId(UUID.randomUUID().toString());
            role2.setName("ROLE_" + AppConstants.ROLE_NORMAL);  // Fix typo
            roleNormal = roleRepository.save(role2);
        }

        User user = userRepository.findByEmail("admin@gmail.com").orElse(null);
        if (user == null) {
            user = new User();
            user.setName("admin");
            user.setEmail("admin@gmail.com");
                user.setPassword(passwordEncoder.encode("admin123"));
            user.setRoles(List.of(roleAdmin));  // Now roleAdmin is not null
            user.setUserId(UUID.randomUUID().toString());
            userRepository.save(user);
        }
    }
}
