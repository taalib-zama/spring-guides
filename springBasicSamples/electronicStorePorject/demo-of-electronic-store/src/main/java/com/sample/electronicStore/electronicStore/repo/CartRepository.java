package com.sample.electronicStore.electronicStore.repo;

import com.sample.electronicStore.electronicStore.entities.Cart;
import com.sample.electronicStore.electronicStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {

    Optional<Cart> findByUser(User user);

}
