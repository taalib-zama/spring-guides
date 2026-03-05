package com.sample.electronicStore.electronicStore.services.impl;

import com.sample.electronicStore.electronicStore.dtos.CategoryDTO;
import com.sample.electronicStore.electronicStore.dtos.ProductDTO;
import com.sample.electronicStore.electronicStore.entities.Category;
import com.sample.electronicStore.electronicStore.entities.Product;
import com.sample.electronicStore.electronicStore.entities.User;
import com.sample.electronicStore.electronicStore.exceptions.ResourceNotFoundException;
import com.sample.electronicStore.electronicStore.mapper.ProductMapper;
import com.sample.electronicStore.electronicStore.repo.CategoryRepository;
import com.sample.electronicStore.electronicStore.repo.ProductRepository;
import com.sample.electronicStore.electronicStore.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.time.LocalDateTime.now;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

@Autowired
private ProductMapper productMapper;


    @Override
    public ProductDTO create(ProductDTO productDTO) {

        //create the random id.
        String productId = java.util.UUID.randomUUID().toString();
        productDTO.setProductId(productId);
        productDTO.setAddedDate(now());

        Product product = modelMapper.map(productDTO, Product.class);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO update(ProductDTO productDTO, String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        // update fields only when provided to avoid overwriting with nulls
        Optional.ofNullable(productDTO.getTitle()).ifPresent(product::setTitle);
        Optional.ofNullable(productDTO.getDescription()).ifPresent(product::setDescription);
        Optional.ofNullable(productDTO.getPrice()).ifPresent(product::setPrice);
        Optional.ofNullable(productDTO.getProductImage()).ifPresent(product::setProductImage);
        Optional.ofNullable(productDTO.getAddedDate()).ifPresent(product::setAddedDate);
        Optional.ofNullable(productDTO.getDiscountedPrice()).ifPresent(product::setDiscountedPrice);
        Optional.ofNullable(productDTO.getIsLive()).ifPresent(product::setIsLive);
        Optional.ofNullable(productDTO.getIsInstock()).ifPresent(product::setIsInstock);

        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    @Override
    public void delete(String productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public ProductDTO getSingle(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public Page<ProductDTO> getAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(product -> modelMapper.map(product, ProductDTO.class));
    }


    //when giving pagable object spring automatically converts the pageable object into page request with page number and size.
    //given in query params like /api/v1/products?page=0&size=10,asc it will convert into pageable object.
    @Override
    public Page<ProductDTO> getAllLive(Pageable pageable) {
        Page<Product> liveProducts = productRepository.findByIsLiveTrue(pageable);
        return liveProducts.map(product -> modelMapper.map(product, ProductDTO.class));
    }

    @Override
    public Page<ProductDTO> searchProducts(String subTitle, Pageable pageable) {
        Page<Product> products = productRepository.findByTitleContaining(pageable, subTitle);
        return products.map(product -> modelMapper.map(product, ProductDTO.class));
    }

    //create product inside category (with category)
    @Override
    public ProductDTO createWithCategory(ProductDTO productDTO, String categoryId) {
        //fetch the category by id
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category== null) {
            throw new RuntimeException("Category not found");
        }

        String productId = java.util.UUID.randomUUID().toString();
        productDTO.setProductId(productId);
        productDTO.setAddedDate(now());

        Product product = modelMapper.map(productDTO, Product.class);
        //update the category to product entity
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);

    }

    @Override
    public ProductDTO updateCategory(String categoryId, String productId) {
        //fetch the product by id
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        //fetch the category by id
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));

        //set the new category to product
        product.setCategory(category);
        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    @Override
    public Page<ProductDTO> getAllWithCategory(String categoryId, Pageable pageable) {
        /*if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with ID: " + categoryId, HttpStatus.NOT_FOUND);
        }*/

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId, HttpStatus.NOT_FOUND));
        Page<Product> products = productRepository.findByCategory(pageable, category);
        return products.map(productMapper::toDTO);
    }


}
