package com.abdelrahman.amin.vending.enums;

import java.util.HashMap;
import java.util.Map;

public enum SystemCoins {
    COIN_100_CENT, COIN_50_CENT, COIN_20_CENT, COIN_10_CENT, COIN_5_CENT,
    UNSUPPORTED_COIN;

    public static Map<SystemCoins, Integer> calculateCents(Integer balance) {
        HashMap<SystemCoins, Integer> map = new HashMap<>(6);
        if (balance / 100.0 >= 1) {
            map.put(COIN_100_CENT, (balance / 100));
            balance %= 100;
        }
        if (balance / 50.0 >= 1) {
            map.put(COIN_50_CENT, (balance / 50));
            balance %= 50;
        }
        if (balance / 20.0 >= 1) {
            map.put(COIN_20_CENT, (balance / 20));
            balance %= 20;
        }
        if (balance / 10.0 >= 1) {
            map.put(COIN_10_CENT, (balance / 10));
            balance %= 10;
        }
        if (balance / 5.0 >= 1) {
            map.put(COIN_5_CENT, (balance / 5));
            balance %= 5;
        }
        if (balance > 0) {
            map.put(UNSUPPORTED_COIN, balance);
        }
        return map;
    }


    public static Integer calculateCents(Map<SystemCoins, Integer> coinsMap) {
        Integer balance = 0;
        balance += coinsMap.getOrDefault(COIN_100_CENT, 0) * 100;
        balance += coinsMap.getOrDefault(COIN_50_CENT, 0) * 50;
        balance += coinsMap.getOrDefault(COIN_20_CENT, 0) * 20;
        balance += coinsMap.getOrDefault(COIN_10_CENT, 0) * 10;
        balance += coinsMap.getOrDefault(COIN_5_CENT, 0) * 5;
        balance += coinsMap.getOrDefault(UNSUPPORTED_COIN, 0);
        return balance;
    }
}
