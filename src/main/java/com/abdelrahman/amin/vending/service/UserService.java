package com.abdelrahman.amin.vending.service;


import com.abdelrahman.amin.vending.dto.UserDTO;
import com.abdelrahman.amin.vending.entity.User;
import com.abdelrahman.amin.vending.enums.SystemCoins;
import com.abdelrahman.amin.vending.exception.BuisnessException;
import com.abdelrahman.amin.vending.exception.EntityNotFoundException;

import java.util.Map;

public interface UserService {
    User getUserByUserName(String username) throws EntityNotFoundException;

    void resetBalance(String username) throws EntityNotFoundException;

    User saveUser(User user);

    Map<SystemCoins, Integer> deposit(String username, Map<SystemCoins, Integer> coinsMap) throws EntityNotFoundException, BuisnessException;

    UserDTO createUser(UserDTO userDTO);
}
