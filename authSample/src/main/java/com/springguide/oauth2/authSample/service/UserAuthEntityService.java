package com.springguide.oauth2.authSample.service;


import com.springguide.oauth2.authSample.entity.UserAuthEntity;

import com.springguide.oauth2.authSample.repo.UserAuthEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthEntityService implements UserDetailsService {

    @Autowired
    UserAuthEntityRepository userAuthEntityRepository;

    public UserDetails saveUser(UserAuthEntity userAuth) {
        // Logic to save user details
        // This is a placeholder for the actual implementation
        return userAuthEntityRepository.save(userAuth);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAuthEntityRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
