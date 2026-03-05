package com.sample.electronicStore.electronicStore.controller;


import com.sample.electronicStore.electronicStore.dtos.AddItemsToCartRequest;
import com.sample.electronicStore.electronicStore.dtos.ApiResponse;
import com.sample.electronicStore.electronicStore.dtos.CartDTO;
import com.sample.electronicStore.electronicStore.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Secured({"ADMIN", "NORMAL"})   //works same as defining preauthorize for multiple roles on each api.
public class CartController {

    private final CartService cartService;

    @PostMapping("/{userId}/items")
    public ResponseEntity<CartDTO> addItemToCart(
            @PathVariable String userId,
            @RequestBody AddItemsToCartRequest request) {
        CartDTO cart = cartService.addItemToCart(userId, request);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{userId}/items/{cartItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ApiResponse> removeItemFromCart(
            @PathVariable String userId,
            @PathVariable Integer cartItemId) {
        cartService.removeItemFromCart(userId, cartItemId);
        return ApiResponse.builder()
                .message("Item removed from cart successfully")
                .success(true)
                .status(HttpStatus.OK)
                .build()
                .toResponseEntity();
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ApiResponse> clearCart(@PathVariable String userId) {

        cartService.clearCart(userId);
        return ApiResponse.builder()
                .message("Cart cleared successfully")
                .success(true)
                .status(HttpStatus.OK)
                .build()
                .toResponseEntity();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> getCartByUser(@PathVariable String userId) {
        CartDTO cart = cartService.getCartByUser(userId);
        return ResponseEntity.ok(cart);
    }

}
