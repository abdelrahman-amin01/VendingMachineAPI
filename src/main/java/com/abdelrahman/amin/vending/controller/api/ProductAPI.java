package com.abdelrahman.amin.vending.controller.api;


import com.abdelrahman.amin.vending.dto.OrderDTO;
import com.abdelrahman.amin.vending.dto.ProductDTO;
import com.abdelrahman.amin.vending.exception.EntityNotFoundException;
import com.abdelrahman.amin.vending.exception.NotAvailableAmountException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;


@RequestMapping("/api/v1/products")
@Validated
public interface ProductAPI {

    @GetMapping()
    ResponseEntity<List<ProductDTO>> getProducts(@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "10") Integer pageSize);

    @GetMapping("/{productId}")
    ResponseEntity<ProductDTO> getProduct(@PathVariable Long productId) throws EntityNotFoundException;

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_SELLER') and #principal.name == #productDTO.sellerId")
    ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO, Principal principal);

    @PutMapping("/{productId}")
    @PreAuthorize("hasRole('ROLE_SELLER') and #principal.name == #productDTO.sellerId")
    ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO, Principal principal) throws EntityNotFoundException;

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    ResponseEntity<Void> deleteProduct(@PathVariable Long productId);

    @GetMapping("/{productId}/purchase")
    @PreAuthorize("hasRole('ROLE_BUYER')")
    ResponseEntity<OrderDTO> buyProduct(@PathVariable Long productId, @RequestParam Integer productAmount, Principal principal) throws EntityNotFoundException, NotAvailableAmountException;

}
