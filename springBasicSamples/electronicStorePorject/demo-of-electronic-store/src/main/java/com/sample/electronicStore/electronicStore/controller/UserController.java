package com.sample.electronicStore.electronicStore.controller;

import com.sample.electronicStore.electronicStore.dtos.ImageResponse;
import com.sample.electronicStore.electronicStore.dtos.UserDTO;
import com.sample.electronicStore.electronicStore.services.FileService;
import com.sample.electronicStore.electronicStore.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "user management related APIs", description = "APIs for managing users - create, update, delete, get, search, upload image")
public class UserController {

    private final UserService userService;

    @Autowired
    private FileService fileService;


    @Value("${user.profile.image.path}")
    private String imageUploadPath; //added in config file.


    @PutMapping("/role")
    public ResponseEntity<UserDTO> setRoleToUser(
            @RequestParam String email,
            @RequestParam String roleName) {
        UserDTO updated = userService.setRoleToUser(email, roleName);
        return ResponseEntity.ok(updated);
    }


    //@PreAuthorize("hasAnyRole('ADMIN', 'NORMAL')")

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        //validation needs to be put on userDTO  AS ITS THE ONE accepting data FROM REQUEST BODY
        UserDTO created = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(
            @RequestBody UserDTO userDTO,
            @PathVariable String userId) {
        UserDTO updated = userService.updateUser(userDTO, userId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }


    //NOTE : Spring automatically converts query parameters to Pageable
    // sample -> /api/v1/users?page=0&size=10

    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAllUsers(Pageable pageable) {
        Page<UserDTO> users = userService.getAllUser(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String userId) {
        UserDTO user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUsers(@RequestParam String keyword) {
        List<UserDTO> users = userService.searchUser(keyword);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/email")
    public ResponseEntity<UserDTO> getUserByEmail(@RequestParam String email) {
        UserDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }


    //upload user image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(
            @RequestParam("userImage") MultipartFile imageFile,
            @PathVariable String userId) {

        try {
            String filename = fileService.uploadImage(imageFile, imageUploadPath);

            // Update user with the returned filename (not original filename)
            UserDTO userDTO = userService.getUserById(userId);
            userDTO.setImagePath(filename);  // Use returned filename, not original
            userService.updateUser(userDTO, userId);

            ImageResponse response = ImageResponse.builder()
                    .imageName(filename)  // Use returned filename
                    .success(true)
                    .status(HttpStatus.CREATED)
                    .message("Image uploaded successfully")
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IOException e) {
            ImageResponse response = ImageResponse.builder()
                    .imageName(null)
                    .success(false)
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("Image upload failed: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //server image
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) {
        UserDTO userDTO = userService.getUserById(userId);
        String imagePath = userDTO.getImagePath();
        try {
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            fileService.getResource(imageUploadPath, imagePath).transferTo(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
