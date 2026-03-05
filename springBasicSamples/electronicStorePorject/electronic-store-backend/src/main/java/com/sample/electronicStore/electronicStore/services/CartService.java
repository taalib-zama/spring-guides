package com.sample.electronicStore.electronicStore.services;

import com.sample.electronicStore.electronicStore.dtos.AddItemsToCartRequest;
import com.sample.electronicStore.electronicStore.dtos.CartDTO;

import java.io.InputStream;

public interface CartService {
    //add items to cart
    // case 1: if cart is not there create cart and add item
    // case 2: if cart is there then update cart

    CartDTO addItemToCart(String userId, AddItemsToCartRequest addItemsToCartRequest);


    //remove item from cart
    void removeItemFromCart(String userId, Integer cartItemId);

    //clear cart
    void clearCart(String userId);

    CartDTO getCartByUser(String userId);
}
