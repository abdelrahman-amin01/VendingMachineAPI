package com.abdelrahman.amin.vending.controller;


import com.abdelrahman.amin.vending.controller.api.ProductAPI;
import com.abdelrahman.amin.vending.dto.OrderDTO;
import com.abdelrahman.amin.vending.dto.ProductDTO;
import com.abdelrahman.amin.vending.exception.EntityNotFoundException;
import com.abdelrahman.amin.vending.exception.NotAvailableAmountException;
import com.abdelrahman.amin.vending.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
public class ProductController implements ProductAPI {

    private ProductService productService;


    @Override
    public ResponseEntity<List<ProductDTO>> getProducts(Integer pageNumber, Integer pageSize) {
        List<ProductDTO> productDTOS = productService.getProducts(pageNumber, pageSize);
        return ResponseEntity.ok(productDTOS);
    }


    @Override
    public ResponseEntity<ProductDTO> getProduct(Long productId) throws EntityNotFoundException {
        ProductDTO productDTOS = productService.getProduct(productId);
        return ResponseEntity.ok(productDTOS);
    }


    @Override
    public ResponseEntity<ProductDTO> createProduct(ProductDTO productDTO, Principal principal) {
        ProductDTO post = productService.createProduct(productDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Override
    public ResponseEntity<ProductDTO> updateProduct(Long productId, ProductDTO productDTO, Principal principal) throws EntityNotFoundException {
        ProductDTO post = productService.updateProduct(productId, productDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();

    }

    @Override
    public ResponseEntity<OrderDTO> buyProduct(Long productId, Integer productAmount, Principal principal) throws EntityNotFoundException, NotAvailableAmountException {
        return ResponseEntity.ok(productService.buyProduct(productId, productAmount, principal.getName()));
    }
}
