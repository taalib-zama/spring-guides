package com.sample.electronicStore.electronicStore.dtos;

import com.sample.electronicStore.electronicStore.entities.Cart;
import com.sample.electronicStore.electronicStore.entities.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDTO {

    private Integer cartItemId;
    private Integer quantity;
    private Long totalPrice;
    private ProductDTO product;
    private Cart cart;
}
