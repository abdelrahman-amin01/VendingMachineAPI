package com.abdelrahman.amin.vending.fixture;

import com.abdelrahman.amin.vending.dto.ProductDTO;
import com.abdelrahman.amin.vending.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductFixture {
    public static ProductDTO getProductDTO() {
        return ProductDTO.builder()
                        .id(1L)
                        .productName("productName")
                        .amountAvailable(1)
                        .cost(5)
                        .sellerId("seller")
                        .build();
    }

    public static Product getProduct() {
        return Product.builder()
                        .id(1L)
                        .productName("productName")
                        .amountAvailable(1)
                        .cost(5)
                        .sellerId("seller")
                        .build();
    }

    public static List<Product> getProductList() {
        return new ArrayList<>(
                Arrays.asList(
                        Product.builder()
                                .id(1L)
                                .productName("productName")
                                .amountAvailable(1)
                                .cost(5)
                                .sellerId("seller")

                                .build(),
                        Product.builder()
                                .id(1L)
                                .productName("productName2")
                                .amountAvailable(1)
                                .cost(5)
                                .sellerId("seller2")
                                .build()));
    }

    public static Page<ProductDTO> getProductDTOPage() {

        return new PageImpl<>(getProductDTOList());
    }
    public static List<ProductDTO> getProductDTOList() {
        return new ArrayList<>(
                Arrays.asList(
                        ProductDTO.builder()
                                .id(1L)
                                .productName("productName")
                                .amountAvailable(1)
                                .cost(5)
                                .sellerId("seller")

                                .build(),
                        ProductDTO.builder()
                                .id(1L)
                                .productName("productName2")
                                .amountAvailable(1)
                                .cost(5)
                                .sellerId("seller2")
                                .build()));
    }

    public static Page<Product> getProductPage() {

        return new PageImpl<>(getProductList());
    }
}
