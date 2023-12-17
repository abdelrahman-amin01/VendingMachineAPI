package com.abdelrahman.amin.vending.mapper;

import com.abdelrahman.amin.vending.dto.ProductDTO;
import com.abdelrahman.amin.vending.dto.UserDTO;
import com.abdelrahman.amin.vending.entity.Product;
import com.abdelrahman.amin.vending.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
   UserDTO toDto(User user);

    User toEntity(UserDTO userDTO);

}
