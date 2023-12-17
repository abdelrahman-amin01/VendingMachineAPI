package com.abdelrahman.amin.vending.mapper;

import com.abdelrahman.amin.vending.dto.ProductDTO;
import com.abdelrahman.amin.vending.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDto(Product product);

    Product toEntity(ProductDTO productDTO);

}
