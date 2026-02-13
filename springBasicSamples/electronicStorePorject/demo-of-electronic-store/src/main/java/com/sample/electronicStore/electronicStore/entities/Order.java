package com.sample.electronicStore.electronicStore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    private String orderId;

    //PENDING, DELIVERED, DISPATCHED, CANCELED - ENUM
    private String orderStatus;

    //PAID, UNPAID
    private String paymentStatus;

    private Long orderAmount;

    @Column(length = 1000)
    private String billingAddress;

    private String billingPhone;
    private String email;
    private String billingPincode;
    private String billingName;
    private LocalDateTime orderedDate;
    private LocalDateTime deliveredDate;

    //user id
    @ManyToOne (fetch = FetchType.EAGER) //bidirectional mapping - one user can have many orders
    @JoinColumn(name = "user_id")
    private User user;


    //this order can have multiple order items
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderItem> orderItems = new ArrayList<>();

}
