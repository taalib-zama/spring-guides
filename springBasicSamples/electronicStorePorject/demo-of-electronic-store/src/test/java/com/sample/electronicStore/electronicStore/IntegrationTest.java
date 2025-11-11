package com.sample.electronicStore.electronicStore;


import com.sample.electronicStore.electronicStore.dtos.UserDTO;
import com.sample.electronicStore.electronicStore.services.impl.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("User Controller Integration Tests")
class UserControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("Should create user via REST API")
    void shouldCreateUserViaRestApi() {
        // Given
        UserDTO userDTO = UserDTO.builder()
                .name("John Doe")
                .email("john@example.com")
                .build();

        when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);

        // When
        ResponseEntity<UserDTO> response = restTemplate.postForEntity(
                "/api/v1/users", userDTO, UserDTO.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}
