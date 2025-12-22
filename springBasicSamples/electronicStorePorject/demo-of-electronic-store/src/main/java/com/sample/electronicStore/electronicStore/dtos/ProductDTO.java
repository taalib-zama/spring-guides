package com.sample.electronicStore.electronicStore.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class ProductDTO {

    private String productId;
    private String title;
    private String description;
    private Double price;
    private Double discountedPrice;
    private Integer quantity;
    private Boolean isLive;
    private Date addedDate;
    private Boolean isInstock;
    private String productImage;

}
