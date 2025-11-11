package com.sample.electronicStore.electronicStore.mapper;


import com.sample.electronicStore.electronicStore.dtos.UserDTO;
import com.sample.electronicStore.electronicStore.entities.User;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
    List<UserDTO> toDTOList(List<User> users);
}
