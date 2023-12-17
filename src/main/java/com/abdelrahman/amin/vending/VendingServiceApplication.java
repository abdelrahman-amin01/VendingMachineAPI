package com.abdelrahman.amin.vending;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VendingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VendingServiceApplication.class, args);
    }

}