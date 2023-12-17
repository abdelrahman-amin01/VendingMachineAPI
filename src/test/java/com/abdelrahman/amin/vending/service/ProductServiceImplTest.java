package com.abdelrahman.amin.vending.service;

import com.abdelrahman.amin.vending.dto.OrderDTO;
import com.abdelrahman.amin.vending.dto.ProductDTO;
import com.abdelrahman.amin.vending.entity.Product;
import com.abdelrahman.amin.vending.entity.User;
import com.abdelrahman.amin.vending.exception.EntityNotFoundException;
import com.abdelrahman.amin.vending.exception.NotAvailableAmountException;
import com.abdelrahman.amin.vending.mapper.ProductMapper;
import com.abdelrahman.amin.vending.repository.ProductRepository;
import com.abdelrahman.amin.vending.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.abdelrahman.amin.vending.fixture.ProductFixture.getProduct;
import static com.abdelrahman.amin.vending.fixture.ProductFixture.getProductDTO;
import static com.abdelrahman.amin.vending.fixture.ProductFixture.getProductDTOList;
import static com.abdelrahman.amin.vending.fixture.ProductFixture.getProductPage;
import static com.abdelrahman.amin.vending.fixture.UserFixture.getUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @InjectMocks
    ProductServiceImpl productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserService userService;
    @Spy
    private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);


    @Test
    void getProducts_getAll_ReturnList() {
        when(productRepository.findAll(any(Pageable.class)))
                .thenReturn(getProductPage());
        List<ProductDTO> products = productService.getProducts(1, 10);
        assertEquals(products, getProductDTOList());
    }


    @Test
    void getProduct_idNotExist_Throw(){
        when(productRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,() ->  productService.getProduct(1L));
    }

    @Test
    void getProduct_getById_ReturnProduct() throws EntityNotFoundException {
        when(productRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getProduct()));
        ProductDTO product = productService.getProduct(1L);
        assertEquals(product, getProductDTO());
    }
    @Test
    void createProduct_save_ReturnProduct() {
        when(productRepository.save(any(Product.class)))
                .thenReturn(getProduct());
        ProductDTO product = productService.createProduct(getProductDTO());
        assertEquals(product, getProductDTO());
    }

    @Test
    void updateProduct_update_ReturnProduct() throws EntityNotFoundException {
        when(productRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getProduct()));
        when(productRepository.save(any(Product.class)))
                .thenReturn(getProduct());
        ProductDTO product = productService.updateProduct(1L,getProductDTO());
        assertEquals(product, getProductDTO());
    }

    @Test
    void updateProduct_idNotExist_Throw() {
        when(productRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,() -> productService.updateProduct(1L,getProductDTO()));
    }


    @Test
    void deleteProduct_save_ReturnProduct() {
        doNothing().when(productRepository).delete(any(Product.class));
         productService.deleteProduct(1L);
        verify(productRepository, times(1)).delete(any(Product.class));
    }
    @Test
    void buyProduct_idNotExist_throw() {
        when(productRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,() -> productService.buyProduct(1L,1,"seller"));
    }
    @Test
    void buyProduct_userHasNoBalance_Throw() throws EntityNotFoundException {
        when(productRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getProduct()));
        User user = getUser();
        user.setBalance(1);
        when(userService.getUserByUserName(any(String.class)))
                .thenReturn(user);
        assertThrows(NotAvailableAmountException.class,() -> productService.buyProduct(1L,1,"seller"));
    }
    @Test
    void buyProduct_muchProductAmount_Throw(){
        when(productRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getProduct()));
        assertThrows(NotAvailableAmountException.class,() -> productService.buyProduct(1L,2,"seller"));
    }
    @Test
    void buyProduct_Buy_ReturnOrder() throws EntityNotFoundException, NotAvailableAmountException {
        when(productRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(getProduct()));
        User user = getUser();
        when(userService.getUserByUserName(any(String.class)))
                .thenReturn(user);
        when(productRepository.save(any(Product.class)))
                .thenReturn(getProduct());
        when(userService.saveUser(any(User.class)))
                .thenReturn(user);
        OrderDTO orderDTO = productService.buyProduct(1L, 1, "seller");
        assertEquals(orderDTO.getTotalSpent(),5);
    }

}