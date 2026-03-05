package com.sample.electronicStore.electronicStore.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private CartDTO cart;
}
