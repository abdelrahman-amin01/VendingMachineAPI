package com.abdelrahman.amin.vending.fixture;

import com.abdelrahman.amin.vending.dto.ProductDTO;
import com.abdelrahman.amin.vending.entity.Product;
import com.abdelrahman.amin.vending.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserFixture {
    public static User getUser() {
        return User.builder()
                .username("seller")
                .balance(10)
                .build();
    }

}
