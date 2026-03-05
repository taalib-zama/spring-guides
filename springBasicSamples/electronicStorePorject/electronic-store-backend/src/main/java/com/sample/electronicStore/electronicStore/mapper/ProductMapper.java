package com.sample.electronicStore.electronicStore.mapper;

import com.sample.electronicStore.electronicStore.dtos.ProductDTO;
import com.sample.electronicStore.electronicStore.dtos.UserDTO;
import com.sample.electronicStore.electronicStore.entities.Product;
import com.sample.electronicStore.electronicStore.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

        ProductDTO toDTO(Product product);
        Product toEntity(ProductDTO productDTO);
        List<ProductDTO> toDTOList(List<Product> products);
}
