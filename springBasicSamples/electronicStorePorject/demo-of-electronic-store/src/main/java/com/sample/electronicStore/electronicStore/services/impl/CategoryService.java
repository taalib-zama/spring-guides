package com.sample.electronicStore.electronicStore.services.impl;

import com.sample.electronicStore.electronicStore.dtos.CategoryDTO;
import com.sample.electronicStore.electronicStore.entities.Category;
import com.sample.electronicStore.electronicStore.repo.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CategoryService implements com.sample.electronicStore.electronicStore.services.CategoryService {


    @Autowired
    private CategoryRepository categoryRepository;


    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

        //creating category id
        String categoryId = java.util.UUID.randomUUID().toString();
        categoryDTO.setCategoryId(categoryId);


        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(String categoryId, CategoryDTO categoryDTO) {
         Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new RuntimeException("Category not found"));
            category.setTitle(categoryDTO.getTitle());
            category.setDescription(categoryDTO.getDescription());
            Category updatedCategory = categoryRepository.save(category);
            return modelMapper.map(updatedCategory, CategoryDTO.class);
    }

    @Override
    public void deleteCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
    }

    @Override
    public Page<CategoryDTO> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> page = categoryRepository.findAll(pageable);
        return page.map(category -> modelMapper.map(category, CategoryDTO.class));
    }

    @Override
    public CategoryDTO getSingle(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public Page<CategoryDTO> searchCategory(String keyword, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Category> page = categoryRepository.findByTitleContaining(keyword, pageable);
        return page.map(category -> modelMapper.map(category, CategoryDTO.class));
    }
}
