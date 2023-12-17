package com.abdelrahman.amin.vending.service;


import com.abdelrahman.amin.vending.dto.OrderDTO;
import com.abdelrahman.amin.vending.dto.ProductDTO;
import com.abdelrahman.amin.vending.exception.EntityNotFoundException;
import com.abdelrahman.amin.vending.exception.NotAvailableAmountException;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getProducts(Integer pageNumber, Integer pageSize);

    ProductDTO getProduct(Long productId) throws EntityNotFoundException;

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(Long productId, ProductDTO productDTO) throws EntityNotFoundException;

    void deleteProduct(Long productId);

    OrderDTO buyProduct(Long productId, Integer productAmount, String username) throws NotAvailableAmountException, EntityNotFoundException;
}
