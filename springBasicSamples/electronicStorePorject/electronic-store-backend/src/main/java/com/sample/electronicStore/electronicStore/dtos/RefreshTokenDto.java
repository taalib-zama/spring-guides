package com.sample.electronicStore.electronicStore.dtos;


import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.Instant;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RefreshTokenDto {
    private int id;
    private String token;
    private Instant expiryDate;

    @OneToOne
    private UserDTO user;

}
