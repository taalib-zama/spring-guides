package com.learning.core.JPADemo.spring_data_jpa_demo.repository;

import com.learning.core.JPADemo.spring_data_jpa_demo.entity.ErrorCodeMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ErrorCodeMappingRepository extends JpaRepository<ErrorCodeMapping, Long> {
    Optional<ErrorCodeMapping> findByMessage(String message);
}
