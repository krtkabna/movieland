package com.wasp.rottenpotatoes.entity.nbu;

import java.util.Arrays;

public enum Currency {
    UAH, USD, EUR;

    public static boolean contains(String name) {
        return Arrays.stream(values())
            .map(Currency::name)
            .anyMatch(value -> value.equalsIgnoreCase(name));
    }
}
