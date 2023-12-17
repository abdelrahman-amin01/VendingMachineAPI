package com.abdelrahman.amin.vending.repository;

import com.abdelrahman.amin.vending.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
