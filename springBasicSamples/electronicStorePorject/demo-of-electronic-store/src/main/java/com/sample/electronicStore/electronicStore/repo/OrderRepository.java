package com.sample.electronicStore.electronicStore.repo;

import com.sample.electronicStore.electronicStore.entities.Order;
import com.sample.electronicStore.electronicStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {


    List<Order> findByUser(User user);


}
