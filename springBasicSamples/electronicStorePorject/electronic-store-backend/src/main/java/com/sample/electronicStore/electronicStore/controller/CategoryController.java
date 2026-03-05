package com.sample.electronicStore.electronicStore.controller;

import com.sample.electronicStore.electronicStore.dtos.CategoryDTO;
import com.sample.electronicStore.electronicStore.dtos.ImageResponse;
import com.sample.electronicStore.electronicStore.dtos.ProductDTO;
import com.sample.electronicStore.electronicStore.dtos.UserDTO;
import com.sample.electronicStore.electronicStore.repo.CategoryRepository;
import com.sample.electronicStore.electronicStore.services.CategoryService;
import com.sample.electronicStore.electronicStore.services.FileService;
import com.sample.electronicStore.electronicStore.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;

@RestController
@RequestMapping("/categories")
public class CategoryController {


    @Autowired
    CategoryService categoryService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ProductService productService;


    @Value("${category.profile.image.path}")
    private String imageUploadPath; //added in config file.
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


    //upload category image.
    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadUserImage(
            @RequestParam("categoryImage") MultipartFile imageFile,
            @PathVariable String categoryId) {

        try {
            String filename = fileService.uploadImage(imageFile, imageUploadPath);

            // Update user with the returned filename (not original filename)
            CategoryDTO categoryDTO = categoryService.getSingle(categoryId);
            categoryDTO.setCoverImage(filename);  // Use returned filename, not original
            categoryService.updateCategory(categoryId,categoryDTO);

            ImageResponse response = ImageResponse.builder()
                    .imageName(filename)  // Use returned filename
                    .success(true)
                    .status(HttpStatus.CREATED)
                    .message("Image uploaded successfully")
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IOException e) {
            ImageResponse response = ImageResponse.builder()
                    .imageName(null)
                    .success(false)
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("Image upload failed: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //server image
    @GetMapping("/image/{categoryId}")
    public void serveUserImage(@PathVariable String categoryId, HttpServletResponse response) {
        CategoryDTO categoryDTO = categoryService.getSingle(categoryId);
        if (categoryDTO == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            try {
                response.getWriter().write("{\"error\": \"Category not found\"}");
            } catch (IOException ignored) {}
            return;
        }

        String imagePath = categoryDTO.getCoverImage();
        if (imagePath == null || imagePath.trim().isEmpty()) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            try {
                response.getWriter().write("{\"error\": \"No image associated with category\"}");
            } catch (IOException ignored) {}
            return;
        }

        // Resolve actual file and check existence before streaming
        File resolved = new File(imageUploadPath, imagePath);
        // Add debugging info: list files in upload dir if file missing
        if (!resolved.exists() || !resolved.isFile()) {
            // Log directory contents for debugging (useful during development)
            File dir = new File(imageUploadPath);
            String files = "[not available]";
            if (dir.exists() && dir.isDirectory()) {
                files = Arrays.toString(dir.list());
            }

            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            try {
                response.getWriter().write("{\"error\": \"Image file not found\", \"requested\": \"" + resolved.getAbsolutePath() + "\", \"filesInDir\": \"" + files + "\"}");
            } catch (IOException ignored) {}
            return;
        }

        try (InputStream is = fileService.getResource(imageUploadPath, imagePath)) {
            // Determine content type if possible
            String contentType = Files.probeContentType(resolved.toPath());
            if (contentType == null) {
                contentType = MediaType.IMAGE_JPEG_VALUE; // fallback
            }
            response.setContentType(contentType);
            is.transferTo(response.getOutputStream());
        } catch (IOException e) {
            // Return JSON error to avoid content-type mismatch
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            try {
                response.getWriter().write("{\"error\": \"Failed to read image: " + e.getMessage() + "\"}");
            } catch (IOException ignored) {}
        }
    }

    //Add product inside category page.
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDTO> createProductInCategory(@RequestBody ProductDTO productDTO, @PathVariable("categoryId") String categoryId){
        ProductDTO createdProduct = productService.createWithCategory(productDTO, categoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }


    //update category of product
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDTO> updateProductCategory(@PathVariable("categoryId") String categoryId, @PathVariable("productId") String productId){
        ProductDTO updatedProduct = productService.updateCategory(categoryId, productId);
        return ResponseEntity.ok(updatedProduct);
    }

    //get all products of category.
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<Page<ProductDTO>> getAllProductsOfCategory(@PathVariable("categoryId") String categoryId, Pageable pageable) {

        Page<ProductDTO> products = productService.getAllWithCategory(categoryId,pageable);
        return ResponseEntity.ok(products);
            }
}
