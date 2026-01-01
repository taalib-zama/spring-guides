package com.sample.electronicStore.electronicStore.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddItemsToCartRequest {

    private String productId;
    private Integer quantity;
}
