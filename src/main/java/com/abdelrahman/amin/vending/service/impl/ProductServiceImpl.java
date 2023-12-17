package com.abdelrahman.amin.vending.service.impl;


import com.abdelrahman.amin.vending.dto.OrderDTO;
import com.abdelrahman.amin.vending.dto.ProductDTO;
import com.abdelrahman.amin.vending.entity.Product;
import com.abdelrahman.amin.vending.entity.User;
import com.abdelrahman.amin.vending.enums.SystemCoins;
import com.abdelrahman.amin.vending.exception.EntityNotFoundException;
import com.abdelrahman.amin.vending.exception.NotAvailableAmountException;
import com.abdelrahman.amin.vending.mapper.ProductMapper;
import com.abdelrahman.amin.vending.repository.ProductRepository;
import com.abdelrahman.amin.vending.service.ProductService;
import com.abdelrahman.amin.vending.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private UserService userService;
    private ProductMapper productMapper;

    @Override
    public List<ProductDTO> getProducts(Integer pageNumber, Integer pageSize) {
        Page<Product> productPage = productRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return productPage.map(product -> productMapper.toDto(product)).toList();
    }

    @Override
    public ProductDTO getProduct(Long entityId) throws EntityNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(entityId);
        return optionalProduct.map(product -> productMapper.toDto(product))
                .orElseThrow(() ->
                        new EntityNotFoundException(String.valueOf(entityId)));
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product savedProduct = productRepository.save(productMapper.toEntity(productDTO));
        return productMapper.toDto(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) throws EntityNotFoundException {
        productRepository.findById(productId).orElseThrow(() ->
                new EntityNotFoundException(String.valueOf(productId)));
        Product savedProduct = productRepository.save(productMapper.toEntity(productDTO));
        return productMapper.toDto(savedProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = new Product();
        product.setId(productId);
        productRepository.delete(product);
    }

    @Override
    public OrderDTO buyProduct(Long productId, Integer productAmount, String username) throws NotAvailableAmountException, EntityNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException(String.valueOf(productId)));
        if (product.getAmountAvailable() < productAmount) {
            throw new NotAvailableAmountException(productId);
        }
        User user = userService.getUserByUserName(username);
        if (user.getBalance() < productAmount * product.getCost()) {
            throw new NotAvailableAmountException(productId);
        }
        user.setBalance(user.getBalance() - productAmount * product.getCost());
        product.setAmountAvailable(product.getAmountAvailable() - productAmount);
        productRepository.save(product);
        userService.saveUser(user);
        Map<SystemCoins, Integer> calculatedCents = SystemCoins.calculateCents(user.getBalance());
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setProductName(product.getProductName());
        orderDTO.setTotalSpent(productAmount * product.getCost());
        orderDTO.setAmount(productAmount);
        orderDTO.setChange(calculatedCents);
        return orderDTO;
    }


}