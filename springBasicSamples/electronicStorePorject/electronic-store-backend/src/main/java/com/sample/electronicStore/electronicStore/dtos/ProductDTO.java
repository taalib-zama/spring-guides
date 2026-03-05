package com.sample.electronicStore.electronicStore.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sample.electronicStore.electronicStore.entities.Category;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private LocalDateTime addedDate;
    private Boolean isInstock;
    private String productImage;

    @JsonIgnore
    private CategoryDTO categoryDTO;

}
