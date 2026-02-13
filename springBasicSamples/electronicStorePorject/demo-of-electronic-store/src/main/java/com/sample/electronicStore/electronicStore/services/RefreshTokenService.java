package com.sample.electronicStore.electronicStore.services;


import com.sample.electronicStore.electronicStore.dtos.RefreshTokenDto;
import com.sample.electronicStore.electronicStore.dtos.UserDTO;

public interface RefreshTokenService {

    //create
    RefreshTokenDto createRefreshToken(String username);

    // find by token
    RefreshTokenDto findByToken(String token);
//verify

    RefreshTokenDto verifyRefreshToken(RefreshTokenDto refreshTokenDto);

    UserDTO getUser(RefreshTokenDto dto);

}
