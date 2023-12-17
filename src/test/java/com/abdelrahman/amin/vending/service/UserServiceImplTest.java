package com.abdelrahman.amin.vending.service;

import com.abdelrahman.amin.vending.dto.OrderDTO;
import com.abdelrahman.amin.vending.dto.ProductDTO;
import com.abdelrahman.amin.vending.entity.Product;
import com.abdelrahman.amin.vending.entity.User;
import com.abdelrahman.amin.vending.enums.SystemCoins;
import com.abdelrahman.amin.vending.exception.BuisnessException;
import com.abdelrahman.amin.vending.exception.EntityNotFoundException;
import com.abdelrahman.amin.vending.exception.NotAvailableAmountException;
import com.abdelrahman.amin.vending.mapper.ProductMapper;
import com.abdelrahman.amin.vending.repository.ProductRepository;
import com.abdelrahman.amin.vending.repository.UserRepository;
import com.abdelrahman.amin.vending.service.impl.ProductServiceImpl;
import com.abdelrahman.amin.vending.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.abdelrahman.amin.vending.enums.SystemCoins.COIN_100_CENT;
import static com.abdelrahman.amin.vending.enums.SystemCoins.UNSUPPORTED_COIN;
import static com.abdelrahman.amin.vending.fixture.ProductFixture.getProduct;
import static com.abdelrahman.amin.vending.fixture.ProductFixture.getProductDTO;
import static com.abdelrahman.amin.vending.fixture.ProductFixture.getProductDTOList;
import static com.abdelrahman.amin.vending.fixture.ProductFixture.getProductPage;
import static com.abdelrahman.amin.vending.fixture.UserFixture.getUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;


    @Test
    void getUserByUserName_getUser_ReturnUser() throws EntityNotFoundException {
        when(userRepository.findById(any(String.class)))
                .thenReturn(Optional.of(getUser()));
        User user = userService.getUserByUserName("seller");
        assertEquals(user, getUser());
    }


    @Test
    void getUserByUserName_idNotExist_Throw() {
        when(userRepository.findById(any(String.class)))
                .thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.getUserByUserName("seller"));
    }

    @Test
    void saveUser_save_ReturnUser() {
        when(userRepository.save(any(User.class)))
                .thenReturn(getUser());
        User user = userService.saveUser(getUser());
        assertEquals(user, getUser());
    }

    @Test
    void resetBalance_resetByUserName_Reset() throws EntityNotFoundException {
        when(userRepository.findById(any(String.class)))
                .thenReturn(Optional.of(getUser()));
        when(userRepository.save(any(User.class)))
                .thenReturn(getUser());
        userService.resetBalance("buyer");
        verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    void deposit_depositCoins_ReturnTotalWithdraw() throws EntityNotFoundException, BuisnessException {
        when(userRepository.findById(any(String.class)))
                .thenReturn(Optional.of(getUser()));
        when(userRepository.save(any(User.class)))
                .thenReturn(getUser());
        HashMap<SystemCoins, Integer> coinsMap = new HashMap<>();
        coinsMap.put(COIN_100_CENT, 1);
        Map<SystemCoins, Integer> coinsHeCanWithdraw = userService.deposit("buyer", coinsMap);
        assertEquals(coinsHeCanWithdraw.get(COIN_100_CENT), coinsMap.get(COIN_100_CENT));
    }


    @Test
    void deposit_AddUnSupportedCoins_Throw() {
        HashMap<SystemCoins, Integer> coinsMap = new HashMap<>();
        coinsMap.put(UNSUPPORTED_COIN, 13);
        assertThrows(BuisnessException.class, () -> userService.deposit("buyer", coinsMap));

    }


}