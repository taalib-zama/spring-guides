package com.sample.electronicStore.electronicStore.services.impl;

import com.sample.electronicStore.electronicStore.dtos.RefreshTokenDto;
import com.sample.electronicStore.electronicStore.dtos.UserDTO;
import com.sample.electronicStore.electronicStore.entities.RefreshToken;
import com.sample.electronicStore.electronicStore.entities.User;
import com.sample.electronicStore.electronicStore.exceptions.ResourceNotFoundException;
import com.sample.electronicStore.electronicStore.repo.RefreshTokenRepository;
import com.sample.electronicStore.electronicStore.repo.UserRepository;
import com.sample.electronicStore.electronicStore.services.RefreshTokenService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {


    private UserRepository userRepository;

    private RefreshTokenRepository refreshTokenRepository;

    private ModelMapper modelMapper;

    public RefreshTokenServiceImpl(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RefreshTokenDto createRefreshToken(String username) {

        User user = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User not found !!", HttpStatus.NOT_FOUND));

        RefreshToken refreshToken = refreshTokenRepository.findByUser(user).orElse(null);

        if (refreshToken == null) {
            refreshToken = RefreshToken.builder()
                    .user(user)
                    .token(UUID.randomUUID().toString())
                    .expiryDate(Instant.now().plusSeconds(5 * 24 * 60 * 60))
                    .build();
        } else {
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setExpiryDate(Instant.now().plusSeconds(5 * 24 * 60 * 60));
        }
        RefreshToken savedToken = refreshTokenRepository.save(refreshToken);
        return this.modelMapper.map(savedToken, RefreshTokenDto.class);


    }

    @Override
    public RefreshTokenDto findByToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> new ResourceNotFoundException("Refresh  token not found in database!!", HttpStatus.NOT_FOUND));
        return this.modelMapper.map(refreshToken, RefreshTokenDto.class);
    }

    @Override
    public RefreshTokenDto verifyRefreshToken(RefreshTokenDto token) {


        var refreshToken = modelMapper.map(token, RefreshToken.class);

        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh Token Expired !!");

        }
        return token;
    }

    @Override
    public UserDTO getUser(RefreshTokenDto dto) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(dto.getToken()).orElseThrow(()->  new ResourceNotFoundException("Token not found", HttpStatus.NOT_FOUND));
        User user = refreshToken.getUser();
        return modelMapper.map(user, UserDTO.class);
    }
}
