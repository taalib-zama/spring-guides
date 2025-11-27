package com.sample.electronicStore.electronicStore.repo;

import com.sample.electronicStore.electronicStore.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,String> {

    Page<Category> findByTitleContaining(String keyword, Pageable pageable);
}
