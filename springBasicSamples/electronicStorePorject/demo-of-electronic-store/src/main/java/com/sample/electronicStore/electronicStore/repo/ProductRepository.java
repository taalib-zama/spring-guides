package com.sample.electronicStore.electronicStore.repo;

import com.sample.electronicStore.electronicStore.entities.Category;
import com.sample.electronicStore.electronicStore.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    //search
    Page<Product> findByTitleContaining(Pageable pageable,String subTitle);

    //list of live products
    Page<Product> findByIsLiveTrue(Pageable pageable);

    //list of products by category
    Page<Product> findByCategory(Pageable pageable, Category category);
}
