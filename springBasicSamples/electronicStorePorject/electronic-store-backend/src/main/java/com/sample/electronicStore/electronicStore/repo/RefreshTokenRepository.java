package com.sample.electronicStore.electronicStore.repo;

import com.sample.electronicStore.electronicStore.entities.RefreshToken;
import com.sample.electronicStore.electronicStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(User user);

}