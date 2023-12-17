package com.abdelrahman.amin.vending.controller;


import com.abdelrahman.amin.vending.controller.api.UserAPI;
import com.abdelrahman.amin.vending.dto.UserDTO;
import com.abdelrahman.amin.vending.enums.SystemCoins;
import com.abdelrahman.amin.vending.exception.BuisnessException;
import com.abdelrahman.amin.vending.exception.EntityNotFoundException;
import com.abdelrahman.amin.vending.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@AllArgsConstructor
public class UserController implements UserAPI {

    private UserService userService;

    @Override
    public ResponseEntity<UserDTO> createUser(UserDTO userDTO) {
        UserDTO user = userService.createUser(userDTO);
        return ResponseEntity.status(201).body(userDTO);

    }

    @Override
    public ResponseEntity<Void> resetBalance(Principal principal) throws EntityNotFoundException {
        userService.resetBalance(principal.getName());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Map<SystemCoins, Integer>> deposit(Principal principal, Map<SystemCoins, Integer> coinsMap) throws EntityNotFoundException, BuisnessException {
        return ResponseEntity.ok(userService.deposit(principal.getName(), coinsMap));
    }
}
