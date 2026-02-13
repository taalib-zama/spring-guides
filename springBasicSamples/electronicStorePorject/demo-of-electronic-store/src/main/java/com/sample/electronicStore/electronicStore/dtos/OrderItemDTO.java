package com.sample.electronicStore.electronicStore.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class OrderItemDTO {

    private Integer orderItemId;
    private Integer quantity;
    private Long totalPrice;
    private ProductDTO product;

    @JsonIgnore
    private OrderDTO order;
}
