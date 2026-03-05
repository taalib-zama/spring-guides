package com.sample.electronicStore.electronicStore.mapper;


import com.sample.electronicStore.electronicStore.dtos.UserDTO;
import com.sample.electronicStore.electronicStore.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "roles", target = "roles")
    @Mapping(source = "provider", target = "provider")
    UserDTO toDTO(User user);

    @Mapping(source = "roles", target = "roles")
    @Mapping(source = "provider", target = "provider")
    User toEntity(UserDTO userDTO);

    List<UserDTO> toDTOList(List<User> users);
}
