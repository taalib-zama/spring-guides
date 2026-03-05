package com.sample.electronicStore.electronicStore;

import com.sample.electronicStore.electronicStore.entities.User;
import com.sample.electronicStore.electronicStore.repo.UserRepository;
import com.sample.electronicStore.electronicStore.security.JwtHelper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class ElectronicStoreApplicationTests {
    @Test
    void contextLoads() {
    }

    @Mock
    private UserRepository userRepository;

    @Autowired
    private JwtHelper jwtHelper;

    @Test
    void testToken(){
        // Create mock user
        User mockUser = User.builder()
                .email("admin@gmail.com")
                .userId("test-id")
                .name("Admin")
                .build();

        // Mock repository behavior
        when(userRepository.findByEmail("admin@gmail.com"))
                .thenReturn(Optional.of(mockUser));

        User user = userRepository.findByEmail("admin@gmail.com")
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtHelper.generateToken(user);
        System.out.println(token);
        System.out.println("getting username from token");
        System.out.println(jwtHelper.getUsernameFromToken(token));
        System.out.println("Token expired: ");
        System.out.println(jwtHelper.isTokenExpired(token));
    }

}
