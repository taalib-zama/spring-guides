package com.sample.electronicStore.electronicStore.controller;

import com.sample.electronicStore.electronicStore.dtos.ApiResponse;
import com.sample.electronicStore.electronicStore.dtos.ProductDTO;
import com.sample.electronicStore.electronicStore.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    //create
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO productDTO) {
        ProductDTO created = productService.create(productDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO,
                                                    @PathVariable("productId") String productId) {
        ProductDTO updated = productService.update(productDTO, productId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("productId") String productId) {
        productService.delete(productId);
        ApiResponse response = ApiResponse.builder()
                .message("Product is deleted successfully")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getSingle(@PathVariable("productId") String productId) {
        ProductDTO dto = productService.getSingle(productId);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAll(Pageable pageable) {
        Page<ProductDTO> page = productService.getAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/live")
    public ResponseEntity<Page<ProductDTO>> getAllLive(Pageable pageable) {
        Page<ProductDTO> page = productService.getAllLive(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDTO>> searchProducts(@RequestParam("subTitle") String subTitle,
                                                           Pageable pageable) {
        Page<ProductDTO> page = productService.searchProducts(subTitle, pageable);
        return ResponseEntity.ok(page);
    }

}
