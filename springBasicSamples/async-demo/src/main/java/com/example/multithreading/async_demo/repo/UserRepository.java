package com.example.multithreading.async_demo.repo;

import com.example.multithreading.async_demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {


}
