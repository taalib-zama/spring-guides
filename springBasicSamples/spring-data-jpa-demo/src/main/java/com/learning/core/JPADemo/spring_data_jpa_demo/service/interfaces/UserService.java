package com.learning.core.JPADemo.spring_data_jpa_demo.service.interfaces;

import com.learning.core.JPADemo.spring_data_jpa_demo.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User getUserById(Integer id);
    void deleteUserById(Integer id);
    User updateUser(User user, Integer id);
    List<User> getAllUsers();

}
