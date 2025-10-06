package com.learning.core.JPADemo.spring_data_jpa_demo.repository;

import com.learning.core.JPADemo.spring_data_jpa_demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
