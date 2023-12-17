package com.abdelrahman.amin.vending.dto;

import com.abdelrahman.amin.vending.enums.Roles;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private String  username;
    private String  password;
    private Roles role;
    private Integer balance;

    private Map<String,String> attributes;

}
