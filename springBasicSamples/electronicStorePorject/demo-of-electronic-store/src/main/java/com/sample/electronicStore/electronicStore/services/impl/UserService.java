package com.sample.electronicStore.electronicStore.services.impl;

import com.sample.electronicStore.electronicStore.dtos.UserDTO;
import com.sample.electronicStore.electronicStore.entities.User;
import com.sample.electronicStore.electronicStore.exceptions.ResourceNotFoundException;
import com.sample.electronicStore.electronicStore.exceptions.UserNotFoundException;
import com.sample.electronicStore.electronicStore.mapper.UserMapper;
import com.sample.electronicStore.electronicStore.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service

public class UserService implements com.sample.electronicStore.electronicStore.services.UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Value("${user.profile.image.path}")
    private String imagePath;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        String userId = java.util.UUID.randomUUID().toString();
        userDTO.setUserId(userId);

        //repo methods need entity object and not DTO.
        User user = userMapper.toEntity(userDTO);
        User saved = userRepository.save(user);
        return userMapper.toDTO(saved);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found", HttpStatus.NOT_FOUND));
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());
        user.setGender(userDTO.getGender());
        user.setImagePath(userDTO.getImagePath());
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public void deleteUser(String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        //before user deletion , we can also remove all the associated data
        //delete the user image.
        String fullPath = imagePath + user.getImagePath();
        Path path = Paths.get(fullPath);
        try {
            Files.delete(path);
        } catch (NoSuchFileException e) {
            throw new ResourceNotFoundException("Image file not found for deletion: " + fullPath, HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while deleting image file: " + fullPath, e);
        }
        userRepository.delete(user);
    }

    @Override
    public Page<UserDTO> getAllUser(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::toDTO);
    }

    @Override
    public UserDTO getUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return userMapper.toDTO(user);
    }

    @Override
    public List<UserDTO> searchUser(String keyword) {
        List<User> users = userRepository.findByKeyword(keyword);
        return userMapper.toDTOList(users);
    }
}
