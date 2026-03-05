package com.sample.electronicStore.electronicStore.repo;

import com.sample.electronicStore.electronicStore.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

}
