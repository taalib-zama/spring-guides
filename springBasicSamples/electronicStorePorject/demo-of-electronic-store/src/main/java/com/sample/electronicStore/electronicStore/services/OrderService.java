package com.sample.electronicStore.electronicStore.services;

import com.sample.electronicStore.electronicStore.dtos.CreateOrderRequest;
import com.sample.electronicStore.electronicStore.dtos.OrderDTO;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface OrderService {

    //create order
    OrderDTO createOrder(CreateOrderRequest order);

    //update order
    OrderDTO updateOrder(OrderDTO order, String orderId);

    //delete order
    void deleteOrder(String orderId);

    //get single order
    OrderDTO getOrderById(String orderId);

    //get all orders of user
    List<OrderDTO> getOrdersOfUser(String userId);

    //get All orders
    Page<OrderDTO> getAllOrders(Pageable page);


}
