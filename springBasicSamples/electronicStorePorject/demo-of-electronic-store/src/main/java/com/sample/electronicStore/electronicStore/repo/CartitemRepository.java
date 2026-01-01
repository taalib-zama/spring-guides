package com.sample.electronicStore.electronicStore.repo;

import com.sample.electronicStore.electronicStore.entities.Cart;
import com.sample.electronicStore.electronicStore.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartitemRepository extends JpaRepository<CartItem, Integer> {

}
