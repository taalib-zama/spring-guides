package com.sample.electronicStore.electronicStore.controller;

import com.sample.electronicStore.electronicStore.dtos.*;
import com.sample.electronicStore.electronicStore.services.FileService;
import com.sample.electronicStore.electronicStore.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/products")
public class ProductController {
    //create
    @Autowired
    private ProductService productService;


    @Autowired
    private FileService fileService;


    @Value("${product.profile.image.path}")
    private String imageUploadPath; //added in config file.


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

    //upload image
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(
            @RequestParam("productImage") MultipartFile imageFile,
            @PathVariable String productId) {

        try {
            String filename = fileService.uploadImage(imageFile, imageUploadPath);

            // Update user with the returned filename (not original filename)
            ProductDTO productDTO = productService.getSingle(productId);
            productDTO.setProductImage(filename);  // Use returned filename, not original
            productService.update(productDTO,productId);

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


    //serve image
    @GetMapping("/image/{productId}")
    public void serveUserImage(@PathVariable String productId, HttpServletResponse response) throws IOException{
        ProductDTO productDTO = productService.getSingle(productId);
        String imagePath = productDTO.getProductImage();
        try {
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            fileService.getResource(imageUploadPath, imagePath).transferTo(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
