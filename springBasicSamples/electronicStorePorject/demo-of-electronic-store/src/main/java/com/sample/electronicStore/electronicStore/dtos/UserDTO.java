package com.sample.electronicStore.electronicStore.dtos;


import com.sample.electronicStore.electronicStore.entities.Gender;
import com.sample.electronicStore.electronicStore.entities.Providers;
import com.sample.electronicStore.electronicStore.validations.ImageNameValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String userId;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    /*@Email(message = "Email should be valid")*/
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    , message = "Email should be valid")
    private String email;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 50, message = "Password must be between 6 and 50 characters")
    private String password;

    private String about;

    @ImageNameValid(message = "Invalid Image Name")
    private String imagePath;


    private List<RoleDTO> roles;




    private Providers provider;


    // why user DTO ?
    // to avoid cyclic dependency, entities represent persistent data and are used for DB persistence and fetch.
    // But for data transfer between controller and service layer we use DTO (Data Transfer Object).



}
