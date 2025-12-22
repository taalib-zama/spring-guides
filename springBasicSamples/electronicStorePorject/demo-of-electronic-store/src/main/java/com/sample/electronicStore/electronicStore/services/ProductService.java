package com.sample.electronicStore.electronicStore.services;

import com.sample.electronicStore.electronicStore.dtos.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    //create
    ProductDTO create(ProductDTO productDTO);

    //update
    ProductDTO update(ProductDTO productDTO, String productId);

    //delete
    void delete(String productId);

    //getSingle
    ProductDTO getSingle(String productId);

    //getAll
    Page<ProductDTO> getAll(Pageable pageable);

    //getAllLive
    Page<ProductDTO> getAllLive(Pageable pageable);

    //searchProducts
    Page<ProductDTO> searchProducts(String subTitle, Pageable pageable);
}
