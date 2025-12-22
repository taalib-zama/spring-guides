package com.sample.electronicStore.electronicStore.services.impl;

import com.sample.electronicStore.electronicStore.dtos.CategoryDTO;
import com.sample.electronicStore.electronicStore.dtos.ProductDTO;
import com.sample.electronicStore.electronicStore.entities.Category;
import com.sample.electronicStore.electronicStore.entities.Product;
import com.sample.electronicStore.electronicStore.entities.User;
import com.sample.electronicStore.electronicStore.repo.ProductRepository;
import com.sample.electronicStore.electronicStore.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ProductDTO create(ProductDTO productDTO) {

        //create the random id.
        String productId = java.util.UUID.randomUUID().toString();
        productDTO.setProductId(productId);

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
        return null;
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
}
