package com.sample.electronicStore.electronicStore.controllerTests;

import com.sample.electronicStore.electronicStore.controller.UserController;
import com.sample.electronicStore.electronicStore.dtos.UserDTO;
import com.sample.electronicStore.electronicStore.services.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("User Controller Tests")
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDTO userDTO;
    private Page<UserDTO> userPage;

    @BeforeEach
    void setUp() {
        userDTO = UserDTO.builder()
                .userId("123")
                .name("John Doe")
                .email("john@example.com")
                .build();

        userPage = new PageImpl<>(List.of(userDTO));
    }

    @Nested
    @DisplayName("Create User")
    class CreateUser {

        @Test
        @DisplayName("Should create user successfully")
        void shouldCreateUserSuccessfully() {
            // Given
            when(userService.createUser(userDTO)).thenReturn(userDTO);

            // When
            ResponseEntity<UserDTO> response = userController.createUser(userDTO);

            // Then
            assertAll(
                    () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED),
                    () -> assertThat(response.getBody()).isEqualTo(userDTO),
                    () -> verify(userService).createUser(userDTO)
            );
        }
    }

    @Nested
    @DisplayName("Update User")
    class UpdateUser {

        @Test
        @DisplayName("Should update user successfully")
        void shouldUpdateUserSuccessfully() {
            // Given
            String userId = "123";
            when(userService.updateUser(userDTO, userId)).thenReturn(userDTO);

            // When
            ResponseEntity<UserDTO> response = userController.updateUser(userDTO, userId);

            // Then
            assertAll(
                    () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(response.getBody()).isEqualTo(userDTO),
                    () -> verify(userService).updateUser(userDTO, userId)
            );
        }
    }

    @Nested
    @DisplayName("Delete User")
    class DeleteUser {

        @Test
        @DisplayName("Should delete user successfully")
        void shouldDeleteUserSuccessfully() {
            // Given
            String userId = "123";
            doNothing().when(userService).deleteUser(userId);

            // When
            userController.deleteUser(userId);

            // Then
            verify(userService).deleteUser(userId);
        }
    }

    @Nested
    @DisplayName("Get All Users")
    class GetAllUsers {

        @Test
        @DisplayName("Should get all users with pagination")
        void shouldGetAllUsersWithPagination() {
            // Given
            Pageable pageable = PageRequest.of(0, 10);
            when(userService.getAllUser(pageable)).thenReturn(userPage);

            // When
            ResponseEntity<Page<UserDTO>> response = userController.getAllUsers(pageable);

            // Then
            assertAll(
                    () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(response.getBody()).isEqualTo(userPage),
                    () -> verify(userService).getAllUser(pageable)
            );
        }
    }

    @Nested
    @DisplayName("Get User By ID")
    class GetUserById {

        @Test
        @DisplayName("Should get user by ID successfully")
        void shouldGetUserByIdSuccessfully() {
            // Given
            String userId = "123";
            when(userService.getUserById(userId)).thenReturn(userDTO);

            // When
            ResponseEntity<UserDTO> response = userController.getUserById(userId);

            // Then
            assertAll(
                    () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(response.getBody()).isEqualTo(userDTO),
                    () -> verify(userService).getUserById(userId)
            );
        }
    }

    @Nested
    @DisplayName("Search Users")
    class SearchUsers {

        @Test
        @DisplayName("Should search users by keyword")
        void shouldSearchUsersByKeyword() {
            // Given
            String keyword = "john";
            List<UserDTO> users = List.of(userDTO);
            when(userService.searchUser(keyword)).thenReturn(users);

            // When
            ResponseEntity<List<UserDTO>> response = userController.searchUsers(keyword);

            // Then
            assertAll(
                    () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(response.getBody()).isEqualTo(users),
                    () -> verify(userService).searchUser(keyword)
            );
        }
    }

    @Nested
    @DisplayName("Get User By Email")
    class GetUserByEmail {

        @Test
        @DisplayName("Should get user by email successfully")
        void shouldGetUserByEmailSuccessfully() {
            // Given
            String email = "john@example.com";
            when(userService.getUserByEmail(email)).thenReturn(userDTO);

            // When
            ResponseEntity<UserDTO> response = userController.getUserByEmail(email);

            // Then
            assertAll(
                    () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(response.getBody()).isEqualTo(userDTO),
                    () -> verify(userService).getUserByEmail(email)
            );
        }
    }
}

