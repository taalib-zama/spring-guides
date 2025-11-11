package com.sample.electronicStore.electronicStore.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String userId;

    @Column(name = "user_name", nullable = false, length = 100)
    private String name;

    @Column(name = "user_email", unique = true, nullable = false)
    private String email;

    @Column(name = "user_password", nullable = false, length = 50)
    private String password;

    @Column(name = "user_gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "about", length = 1000)
    private String about;

    @Column(name = "user_image_url")
    private String imagePath;
}
