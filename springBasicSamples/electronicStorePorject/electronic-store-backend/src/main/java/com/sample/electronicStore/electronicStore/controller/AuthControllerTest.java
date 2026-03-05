package com.sample.electronicStore.electronicStore.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/test/auth")
public class AuthControllerTest {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private ModelMapper modelMapper;


    @GetMapping("/current-user")
    public ResponseEntity<UserDetails> getCurrentUser(Principal principal) {
        return ResponseEntity.ok(modelMapper.map(userDetailsService.loadUserByUsername(principal.getName()), UserDetails.class));
    }
}
