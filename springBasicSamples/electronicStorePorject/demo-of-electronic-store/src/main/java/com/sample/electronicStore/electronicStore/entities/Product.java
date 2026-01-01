package com.sample.electronicStore.electronicStore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "category")     //avoid recursive toString calls
public class Product {
    @Id
    private String productId;
    private String title;

    @Column(length = 1000)
    private String description;


    private Double price;
    private Double discountedPrice;

    private Integer quantity;
    private Boolean isLive;
    private LocalDateTime addedDate;
    private Boolean isInstock;

    private String productImage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
}
