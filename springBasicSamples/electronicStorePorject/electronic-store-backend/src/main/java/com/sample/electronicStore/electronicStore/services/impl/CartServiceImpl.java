package com.sample.electronicStore.electronicStore.services.impl;


import com.sample.electronicStore.electronicStore.dtos.AddItemsToCartRequest;
import com.sample.electronicStore.electronicStore.dtos.CartDTO;
import com.sample.electronicStore.electronicStore.entities.Cart;
import com.sample.electronicStore.electronicStore.entities.CartItem;
import com.sample.electronicStore.electronicStore.entities.Product;
import com.sample.electronicStore.electronicStore.entities.User;
import com.sample.electronicStore.electronicStore.exceptions.ResourceNotFoundException;
import com.sample.electronicStore.electronicStore.exceptions.UserNotFoundException;
import com.sample.electronicStore.electronicStore.repo.CartItemRepository;
import com.sample.electronicStore.electronicStore.repo.CartRepository;
import com.sample.electronicStore.electronicStore.repo.ProductRepository;
import com.sample.electronicStore.electronicStore.repo.UserRepository;
import com.sample.electronicStore.electronicStore.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

    @Autowired

    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartItemRepository cartitemRepository;


    @Override
    public CartDTO addItemToCart(String userId, AddItemsToCartRequest addItemsToCartRequest) {
        /*Integer quantity = addItemsToCartRequest.getQuantity();
        String productId = addItemsToCartRequest.getProductId();
        //fetch the product
        var product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        //fetch the user
        var user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        cartRepository.findByUser(user).ifPresentOrElse(cart -> {;
            //cart is present, update the cart
            var cartItemOptional = cart.getCartItems().stream()
                    .filter(item -> item.getProduct().getProductId().equals(productId))
                    .findFirst();
            if (cartItemOptional.isPresent()) {
                //item is already present in cart, update quantity
                var cartItem = cartItemOptional.get();
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
            } else {
                //item is not present in cart, add new item
                var cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);
                cartItem.setCart(cart);
                cart.getCartItems().add(cartItem);
            }
            cartRepository.save(cart);
        }, () -> {
            //cart is not present, create new cart
            var cart = new Cart();
            cart.setUser(user);
            var cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setCart(cart);
            cart.getCartItems().add(cartItem);
            cartRepository.save(cart);
        });
        var updatedCart = cartRepository.findByUser(user).get();
        return modelMapper.map(updatedCart, CartDTO.class);*/
        // Fetch entities with proper exception handling
        var product = productRepository.findById(addItemsToCartRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + addItemsToCartRequest.getProductId(), HttpStatus.NOT_FOUND));
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        // Get or create cart
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> createNewCart(user));

        // Add or update item
        addOrUpdateCartItem(cart, product, addItemsToCartRequest.getQuantity());

        // Save and return
        Cart savedCart = cartRepository.save(cart);
        return modelMapper.map(savedCart, CartDTO.class);
    }


    @Override
    public void removeItemFromCart(String userId, Integer cartItemId) {
        CartItem cartItem = cartitemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found: " + cartItemId, HttpStatus.NOT_FOUND));
        cartitemRepository.delete(cartItem);
    }

    @Override
    public void clearCart(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId, HttpStatus.NOT_FOUND));
        cart.getCartItems().clear();
        cartRepository.save(cart);

    }

    @Override
    public CartDTO getCartByUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId, HttpStatus.NOT_FOUND));
        return modelMapper.map(cart, CartDTO.class);
    }


    private Cart createNewCart(User user) {
        return Cart.builder()
            .cartId(UUID.randomUUID().toString())
            .user(user)
            .createdAt(LocalDateTime.now())
            .cartItems(new ArrayList<>())
            .build();
    }

    private void addOrUpdateCartItem(Cart cart, Product product, Integer quantity) {
        cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(product.getProductId()))
                .findFirst()
                .ifPresentOrElse(
                        item -> {
                            item.setQuantity(item.getQuantity() + quantity);
                            Double price = product.getDiscountedPrice() != null ? product.getDiscountedPrice() : product.getPrice();
                            item.setTotalPrice((long) (price * item.getQuantity()));
                        },
                        () -> cart.getCartItems().add(createCartItem(cart, product, quantity))
                );

        // Update cart timestamp and calculate total
        cart.setUpdatedAt(LocalDateTime.now());
    }

    private CartItem createCartItem(Cart cart, Product product, Integer quantity) {
        Double price = product.getDiscountedPrice() != null ? product.getDiscountedPrice() : product.getPrice();
        return CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .totalPrice((long) (price * quantity))
                .build();
    }
    }
