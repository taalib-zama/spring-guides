package com.sample.electronicStore.electronicStore.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
    @Id
    private String cartId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToOne
    @JsonIgnore
    private User user;

    //mapping cart items
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<CartItem> cartItems;


}
