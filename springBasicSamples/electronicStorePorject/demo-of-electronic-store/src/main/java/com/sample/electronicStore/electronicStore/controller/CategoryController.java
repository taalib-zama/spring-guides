package com.sample.electronicStore.electronicStore.controller;

import com.sample.electronicStore.electronicStore.dtos.CategoryDTO;
import com.sample.electronicStore.electronicStore.repo.CategoryRepository;
import com.sample.electronicStore.electronicStore.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {


    @Autowired
    CategoryService categoryService;
    //create
    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO){
        //call service to save category
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(201).body(createdCategory);
    }

    //update
    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable String categoryId, @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(categoryId, categoryDTO);
        return ResponseEntity.ok(updatedCategory);
    }


    //delete
   @DeleteMapping("/delete/{categoryId}")
   public ResponseEntity<String> deleteCategory(@PathVariable String categoryId){
       categoryService.deleteCategory(categoryId);
       return ResponseEntity.ok("DELETION COMPLETED");
   }


    //getAll
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCategories(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        return ResponseEntity.ok(categoryService.getAll(pageNumber, pageSize, sortBy, sortDir));
    }


    //getSingle
    @GetMapping("/getSingle/{categoryId}")
    public ResponseEntity<CategoryDTO> getSingleCategory(@PathVariable String categoryId){
        CategoryDTO categoryDTO = categoryService.getSingle(categoryId);
        return ResponseEntity.ok(categoryDTO);
    }

    //search on title
    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> searchCategory(@PathVariable String keyword,
                                            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize){
        return ResponseEntity.ok(categoryService.searchCategory(keyword, pageNumber, pageSize));
    }

}
