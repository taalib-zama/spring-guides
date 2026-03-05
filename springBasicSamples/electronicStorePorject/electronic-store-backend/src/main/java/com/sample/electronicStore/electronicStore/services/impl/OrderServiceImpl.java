package com.sample.electronicStore.electronicStore.services.impl;

import com.sample.electronicStore.electronicStore.dtos.CreateOrderRequest;
import com.sample.electronicStore.electronicStore.dtos.OrderDTO;
import com.sample.electronicStore.electronicStore.entities.*;
import com.sample.electronicStore.electronicStore.exceptions.BadApiRequestException;
import com.sample.electronicStore.electronicStore.exceptions.ResourceNotFoundException;
import com.sample.electronicStore.electronicStore.repo.CartRepository;
import com.sample.electronicStore.electronicStore.repo.OrderRepository;
import com.sample.electronicStore.electronicStore.repo.UserRepository;
import com.sample.electronicStore.electronicStore.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartServiceImpl cartService;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public OrderDTO createOrder(CreateOrderRequest order) {

        String userId = order.getUserId();
        String cartId = order.getCartId();

        //fetch user from userId
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found", HttpStatus.NOT_FOUND));
        //fetch cart from cartId


        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart not found for user", HttpStatus.NOT_FOUND));
        //create order entity from order dto
        List<CartItem> cartItems = cart.getCartItems();
        if(cartItems.size() <= 0){
            throw new BadApiRequestException("Invalid number of items in cart");
        };
        /*Order currentOrder = Order.builder().billingAddress(order.getBillingAddress())
                .billingName(order.getBillingName())
                .billingPhone(order.getBillingPhone())
                .billingPincode(order.getBillingPincode())
                .email(order.getEmail())
                .orderStatus(order.getOrderStatus())
                .paymentStatus(order.getPaymentStatus())
                .orderAmount(order.getOrderAmount())
                .orderedDate(LocalDateTime.now())
                .user(user)
                .orderId(UUID.randomUUID().toString())
                .build();*/

        Order currentOrder = Order.builder()
                .billingName(order.getBillingName())
                .billingPhone(order.getBillingPhone())
                .billingAddress(order.getBillingAddress())
                .orderedDate(LocalDateTime.now())
                .deliveredDate(null)
                .paymentStatus(order.getPaymentStatus())
                .orderStatus(order.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();


        //need to create order items from cart items
        var orderItems = cartItems.stream().map(cartItem -> {
            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .totalPrice((long) (cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice()))
                    .product(cartItem.getProduct())
                    .order(currentOrder)
                    .build();
            return orderItem;
        }).toList();

        currentOrder.setOrderItems(orderItems);
        currentOrder.setOrderAmount(orderItems.stream().mapToLong(OrderItem::getTotalPrice).sum());


        //clear the cart
        cart.getCartItems().clear();
        cartRepository.save(cart);

        //saving order to db
        Order savedOrder = orderRepository.save(currentOrder);

        return modelMapper.map(savedOrder, OrderDTO.class);
    }

    @Override
    public OrderDTO updateOrder(OrderDTO order, String orderId) {
        return null;
    }

    @Override
    public void deleteOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("order is not found !!", HttpStatus.NOT_FOUND));
        orderRepository.delete(order);
    }

    @Override
    public OrderDTO getOrderById(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found !!", HttpStatus.NOT_FOUND));
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getOrdersOfUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found !!", HttpStatus.NOT_FOUND));
        List<Order> orders = orderRepository.findByUser(user);
        List<OrderDTO> orderDtos = orders.stream().map(order -> modelMapper.map(order, OrderDTO.class)).collect(Collectors.toList());
        return orderDtos;
    }

    @Override
    public Page<OrderDTO> getAllOrders(Pageable page) {
        Page<Order> ordersPage = orderRepository.findAll(page);
        return ordersPage.map(order -> modelMapper.map(order, OrderDTO.class));
    }
}
