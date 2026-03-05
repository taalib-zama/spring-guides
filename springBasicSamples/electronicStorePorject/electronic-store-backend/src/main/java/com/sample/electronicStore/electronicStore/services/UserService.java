package com.sample.electronicStore.electronicStore.services;

import com.sample.electronicStore.electronicStore.dtos.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserDTO createUser(UserDTO user);

    UserDTO updateUser(UserDTO user, String userId);


    void deleteUser(String userId);

    Page<UserDTO> getAllUser(Pageable pageable);

    UserDTO getUserById(String userId);

    UserDTO getUserByEmail(String email);

    List<UserDTO> searchUser(String keyword); //by name

    UserDTO setRoleToUser(String email, String roleName);

}
