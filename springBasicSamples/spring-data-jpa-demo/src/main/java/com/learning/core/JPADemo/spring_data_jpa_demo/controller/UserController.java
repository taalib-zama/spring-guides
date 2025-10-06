package com.learning.core.JPADemo.spring_data_jpa_demo.controller;


import com.learning.core.JPADemo.spring_data_jpa_demo.entity.User;
import com.learning.core.JPADemo.spring_data_jpa_demo.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("Request to create user");
        User  createdUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("fetchAll")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Request to fetch all users");
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        log.info("Request to fetch user by id: {}", id);
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }



}
