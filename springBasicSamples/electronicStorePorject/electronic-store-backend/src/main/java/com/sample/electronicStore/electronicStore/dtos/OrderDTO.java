package com.sample.electronicStore.electronicStore.dtos;


import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
@ToString
public class OrderDTO {

    private String orderId;
    private String orderStatus = "PENDING";
    private String paymentStatus="NOTPAID";
    private Long orderAmount;
    private String billingAddress;
    private String billingPhone;
    private String email;
    private String billingPincode;
    private String billingName;
    private LocalDateTime orderedDate;
    private LocalDateTime deliveredDate;
    private UserDTO user;

    private List<OrderItemDTO> orderItems = new ArrayList<>();




}
