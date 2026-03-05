package com.sample.electronicStore.electronicStore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "cart_items")
@AllArgsConstructor
@NoArgsConstructor
@Builder
// Empty class representing a cart item in the electronic store application
//manages  the quantity of items stored in the cart and associates each item with a specific product and cart.
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartItemId;
    private Integer quantity;
    private Long totalPrice;

    @ManyToOne
    private Product product;

    //mapping cart.
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore     //to remove circular dependency caused during serialization
    // this cause infinite recursion while converting to json, this happens when
    // cart references cart items and cart items reference products which again references cart items.
    private Cart cart;

}
