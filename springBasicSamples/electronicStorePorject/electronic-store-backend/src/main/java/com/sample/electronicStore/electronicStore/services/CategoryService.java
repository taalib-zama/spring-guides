package com.sample.electronicStore.electronicStore.services;

import com.sample.electronicStore.electronicStore.dtos.CategoryDTO;
import org.springframework.data.domain.Page;


public interface CategoryService {

    //create

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    //update

    CategoryDTO updateCategory(String categoryId, CategoryDTO categoryDTO);

    //delete
    void deleteCategory(String categoryId);


    //getAll
    Page<CategoryDTO> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);


    //getSingle
    CategoryDTO getSingle(String categoryId);

    //search
    Page<CategoryDTO> searchCategory(String keyword, int pageNumber, int pageSize);
}
