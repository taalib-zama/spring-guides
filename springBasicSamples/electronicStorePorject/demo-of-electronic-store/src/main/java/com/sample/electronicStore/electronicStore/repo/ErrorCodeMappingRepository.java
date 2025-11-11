package com.sample.electronicStore.electronicStore.repo;

import com.sample.electronicStore.electronicStore.entities.ErrorCodeMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ErrorCodeMappingRepository extends JpaRepository<ErrorCodeMapping, Long> {
    Optional<com.sample.electronicStore.electronicStore.entities.ErrorCodeMapping> findByMessage(String message);
}
