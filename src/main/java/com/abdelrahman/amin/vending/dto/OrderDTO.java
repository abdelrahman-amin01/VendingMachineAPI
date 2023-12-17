package com.abdelrahman.amin.vending.dto;

import com.abdelrahman.amin.vending.enums.SystemCoins;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO implements Serializable {
    private String productName;
    private Integer amount;
    private Integer totalSpent;
    private Map<SystemCoins, Integer> change;
}
