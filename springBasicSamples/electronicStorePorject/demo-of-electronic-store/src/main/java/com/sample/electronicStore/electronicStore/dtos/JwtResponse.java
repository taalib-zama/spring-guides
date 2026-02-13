package com.sample.electronicStore.electronicStore.dtos;


import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;
    UserDTO user;
//    private String jwtToken;
    private RefreshTokenDto refreshToken;
}
