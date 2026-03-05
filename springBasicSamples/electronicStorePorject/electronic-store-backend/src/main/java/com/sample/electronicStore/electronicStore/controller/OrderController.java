package com.sample.electronicStore.electronicStore.controller;

import com.sample.electronicStore.electronicStore.dtos.ApiResponse;
import com.sample.electronicStore.electronicStore.dtos.CreateOrderRequest;
import com.sample.electronicStore.electronicStore.dtos.OrderDTO;
import com.sample.electronicStore.electronicStore.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;




    //create order
    @PreAuthorize("hasAnyRole('NORMAL','ADMIN')")
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid  @RequestBody CreateOrderRequest order) {
        OrderDTO createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok(createdOrder);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse> removeOrder(@PathVariable String orderId) {
        orderService.deleteOrder(orderId);
        ApiResponse response = ApiResponse.builder()
                .message("Order deleted successfully")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.ok(response);
    }

    //getorder of user by userId
    @PreAuthorize("hasAnyRole('ADMIN','NORMAL')")
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersOfUser(@PathVariable String userId) {
        List<OrderDTO> orders = orderService.getOrdersOfUser(userId);
        return ResponseEntity.ok(orders);
    }

    //get all oders
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<OrderDTO>> getAllOrders(Pageable page) {
        Page<OrderDTO> orders = orderService.getAllOrders(page);
        return ResponseEntity.ok(orders);
    }

}
