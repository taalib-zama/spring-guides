package com.sample.electronicStore.electronicStore.services.impl;

import com.sample.electronicStore.electronicStore.dtos.CategoryDTO;
import com.sample.electronicStore.electronicStore.entities.Category;
import com.sample.electronicStore.electronicStore.repo.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class CategoryService implements com.sample.electronicStore.electronicStore.services.CategoryService {


    @Autowired
    private CategoryRepository categoryRepository;


    @Autowired
    private ModelMapper modelMapper;

    @Value("${category.profile.image.path}")
    private String imageUploadPath;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

        //creating category id
        String categoryId = java.util.UUID.randomUUID().toString();
        categoryDTO.setCategoryId(categoryId);

        // validate provided coverImage: if client provided a filename that doesn't exist on disk, clear it
        if (categoryDTO.getCoverImage() != null && !categoryDTO.getCoverImage().trim().isEmpty()) {
            File f = new File(imageUploadPath, categoryDTO.getCoverImage());
            if (!f.exists() || !f.isFile()) {
                // avoid storing dangling reference
                System.out.println("Warning: provided coverImage '" + categoryDTO.getCoverImage() + "' not found under " + imageUploadPath + ". Clearing before save.");
                categoryDTO.setCoverImage(null);
            }
        }

        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(String categoryId, CategoryDTO categoryDTO) {
         Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new RuntimeException("Category not found"));
            // update fields only when provided to avoid overwriting with nulls
            if (categoryDTO.getTitle() != null) {
                category.setTitle(categoryDTO.getTitle());
            }
            if (categoryDTO.getDescription() != null) {
                category.setDescription(categoryDTO.getDescription());
            }
            if (categoryDTO.getCoverImage() != null) {
                category.setCoverImage(categoryDTO.getCoverImage());
            }
            Category updatedCategory = categoryRepository.save(category);
            return modelMapper.map(updatedCategory, CategoryDTO.class);
    }

    @Override
    public void deleteCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        String coverImage = category.getCoverImage();
        if (coverImage != null && !coverImage.trim().isEmpty()) {
            File imgFile = new File(imageUploadPath, coverImage);
            try {
                if (imgFile.exists() && imgFile.isFile()) {
                    if (imgFile.delete()) {
                        log.info("Deleted image file: " + imgFile.getAbsolutePath());
                    } else {
                        log.info("Failed to delete image file: " + imgFile.getAbsolutePath());
                    }
                } else {
                    log.info("Image file not found: " + imgFile.getAbsolutePath());
                }
            } catch (SecurityException ex) {
                log.info("Unable to delete image file due to security manager: " + imgFile.getAbsolutePath());
            }
        }


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
