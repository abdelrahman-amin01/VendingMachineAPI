package com.abdelrahman.amin.vending.service.impl;


import com.abdelrahman.amin.vending.config.JwtAuthConverterProperties;
import com.abdelrahman.amin.vending.dto.UserDTO;
import com.abdelrahman.amin.vending.entity.User;
import com.abdelrahman.amin.vending.enums.SystemCoins;
import com.abdelrahman.amin.vending.exception.BuisnessException;
import com.abdelrahman.amin.vending.exception.EntityNotFoundException;
import com.abdelrahman.amin.vending.mapper.UserMapper;
import com.abdelrahman.amin.vending.repository.UserRepository;
import com.abdelrahman.amin.vending.service.UserService;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Keycloak keycloak;
    private final JwtAuthConverterProperties properties;


    @Override
    public void resetBalance(String username) throws EntityNotFoundException {
        User user = getUserByUserName(username);
        user.setBalance(0);
        saveUser(user);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Map<SystemCoins, Integer> deposit(String username, Map<SystemCoins, Integer> coinsMap) throws EntityNotFoundException, BuisnessException {
        if (coinsMap.containsKey(SystemCoins.UNSUPPORTED_COIN)) {
            throw new BuisnessException("Can't accept coins except: 100,50,20,10,5");
        }
        User user = getUserByUserName(username);
        user.setBalance(user.getBalance() + SystemCoins.calculateCents(coinsMap));
        saveUser(user);
        return SystemCoins.calculateCents(user.getBalance());
    }

    @Override
    public User getUserByUserName(String username) throws EntityNotFoundException {
        return userRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException(username));
    }


    @Override
    public UserDTO createUser(UserDTO userDTO) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(userDTO.getPassword());
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(userDTO.getUsername());
        userRepresentation.setEnabled(true);
        userRepresentation.setCredentials(List.of(credentialRepresentation));
        Map<String, String> attributes = userDTO.getAttributes();

        if (attributes != null) {
            userRepresentation.setAttributes(attributes.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> List.of(e.getValue()))));
        }
        RealmResource realmResource = keycloak.realm(properties.getResourceId());
        UsersResource usersResource = realmResource.users();
        Response response = usersResource.create(userRepresentation);

        // Get user resource
        UserRepresentation createdUser = usersResource.search(userDTO.getUsername(), true).get(0);

        // Assign role to user
        RoleRepresentation role = realmResource.roles().get(userDTO.getRole().name()).toRepresentation();
        usersResource.get(createdUser.getId()).roles().realmLevel().add(Collections.singletonList(role));

        if (response.getStatus() != 201) {
            Map<String, String> responseMap = response.readEntity(new GenericType<>() {
            });
            String errorMessage = responseMap.get("errorMessage");
            log.error(errorMessage);
            throw new RuntimeException("Error happen while crating a user:\n" + errorMessage);
        }
        User entity = userMapper.toEntity(userDTO);
        entity.setBalance(0);
        return userMapper.toDto(userRepository.save(entity));
    }
}