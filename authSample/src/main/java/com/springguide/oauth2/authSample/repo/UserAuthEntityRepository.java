package com.springguide.oauth2.authSample.repo;

import com.springguide.oauth2.authSample.entity.UserAuthEntity;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserAuthEntityRepository extends JpaRepository <UserAuthEntity, Long> {
    Optional<UserAuthEntity> findByUsername(String username);
}
