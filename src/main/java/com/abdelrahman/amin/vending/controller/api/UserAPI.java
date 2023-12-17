package com.abdelrahman.amin.vending.controller.api;


import com.abdelrahman.amin.vending.dto.UserDTO;
import com.abdelrahman.amin.vending.enums.SystemCoins;
import com.abdelrahman.amin.vending.exception.BuisnessException;
import com.abdelrahman.amin.vending.exception.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Map;


@RequestMapping("/api/v1/users")
@Validated
public interface UserAPI {

    @PostMapping
    ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO);
    @PatchMapping("/reset-balance")
    @PreAuthorize("hasRole('ROLE_BUYER')")
    ResponseEntity<Void> resetBalance(Principal principal) throws EntityNotFoundException;

    @PatchMapping("/deposit")
    @PreAuthorize("hasRole('ROLE_BUYER')")
    ResponseEntity<Map<SystemCoins, Integer>> deposit(Principal principal, @RequestBody Map<SystemCoins, Integer> coinsMap) throws EntityNotFoundException, BuisnessException;

}
