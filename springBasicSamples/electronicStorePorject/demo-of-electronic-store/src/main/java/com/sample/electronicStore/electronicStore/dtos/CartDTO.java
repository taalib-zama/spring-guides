package com.sample.electronicStore.electronicStore.dtos;

import com.sample.electronicStore.electronicStore.entities.CartItem;
import com.sample.electronicStore.electronicStore.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO {


    private String cartId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private UserDTO user;


    private List<CartItemDTO> cartItems;
}
