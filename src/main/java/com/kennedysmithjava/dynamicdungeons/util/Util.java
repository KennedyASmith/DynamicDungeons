package com.kennedysmithjava.dynamicdungeons.util;

import java.util.*;

public class Util {


    @SafeVarargs
    public static <T> List<T> list(T... elements) {
        return new ArrayList<>(Arrays.asList(elements));
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> map(K key, V value, Object... objects) {
        if (objects.length % 2 != 0) {
            throw new IllegalArgumentException("Objects must be provided in key-value pairs.");
        }

        Map<K, V> map = new HashMap<>();
        map.put(key, value);

        for (int i = 0; i < objects.length; i += 2) {
            K k = (K) objects[i];
            V v = (V) objects[i + 1];
            map.put(k, v);
        }

        return map;
    }

}
