package com.sample.electronicStore.electronicStore.services.impl;

import com.sample.electronicStore.electronicStore.config.AppConstants;
import com.sample.electronicStore.electronicStore.dtos.UserDTO;
import com.sample.electronicStore.electronicStore.entities.Role;
import com.sample.electronicStore.electronicStore.entities.User;
import com.sample.electronicStore.electronicStore.exceptions.ResourceNotFoundException;
import com.sample.electronicStore.electronicStore.exceptions.UserNotFoundException;
import com.sample.electronicStore.electronicStore.mapper.UserMapper;
import com.sample.electronicStore.electronicStore.repo.RoleRepository;
import com.sample.electronicStore.electronicStore.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service

public class UserService implements com.sample.electronicStore.electronicStore.services.UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${user.profile.image.path}")
    private String imagePath;


    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        String userId = java.util.UUID.randomUUID().toString();
        userDTO.setUserId(userId);

        //encode password and set
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        //get the normal role from role repo and set it to user
        Role normalRole = roleRepository.findByName("ROLE_" + AppConstants.ROLE_NORMAL)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setRoleId(UUID.randomUUID().toString());
                    newRole.setName("ROLE_" + AppConstants.ROLE_NORMAL);
                    return roleRepository.save(newRole);
                });


        //userDTO.setRoles(List.of(normalRole));

        //repo methods need entity object and not DTO.
        User user = userMapper.toEntity(userDTO);
        user.setRoles(List.of(normalRole))  ; //setting normal role to user
        User saved = userRepository.save(user);
        return userMapper.toDTO(saved);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found", HttpStatus.NOT_FOUND));
        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }
        if (userDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        if (userDTO.getAbout() != null) {
            user.setAbout(userDTO.getAbout());
        }
        if (userDTO.getGender() != null) {
            user.setGender(userDTO.getGender());
        }
        if (userDTO.getImagePath() != null) {
            user.setImagePath(userDTO.getImagePath());
        }
        if (userDTO.getProvider() != null) {
            user.setProvider(userDTO.getProvider());
        }
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

    @Override
    public UserDTO setRoleToUser(String email, String roleName) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleName, HttpStatus.NOT_FOUND));

        user.setRoles(new ArrayList<>(Arrays.asList(role)));
        User saved = userRepository.save(user);
        return userMapper.toDTO(saved);
    }
}
