package com.learning.core.JPADemo.spring_data_jpa_demo.service;

import com.learning.core.JPADemo.spring_data_jpa_demo.entity.User;
import com.learning.core.JPADemo.spring_data_jpa_demo.entity.User.Builder;
import com.learning.core.JPADemo.spring_data_jpa_demo.exception.ResourceNotFoundException;
import com.learning.core.JPADemo.spring_data_jpa_demo.repository.UserRepository;
import com.learning.core.JPADemo.spring_data_jpa_demo.service.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    Logger logger = org.slf4j.LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public User saveUser(User user) {
        logger.info("Saving user: {}", user);
        return userRepository.save(user);

    }

    @Override
    public User getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public void deleteUserById(Integer id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id, HttpStatus.NOT_FOUND));
        userRepository.delete(existingUser);

    }

    @Override
    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + user.getId(), HttpStatus.NOT_FOUND));

        User updatedUser = User.builder()
                .withId(existingUser.getId())
                .withName(user.getName() != null ? user.getName() : existingUser.getName())
                .withAge(user.getAge() != null ? user.getAge() : existingUser.getAge())
                .withCity(user.getCity() != null ? user.getCity() : existingUser.getCity())
                .build();
            return userRepository.save(updatedUser);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
