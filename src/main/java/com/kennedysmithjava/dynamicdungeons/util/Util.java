package com.kennedysmithjava.dynamicdungeons.util;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Util {


    @SafeVarargs
    public static <T> List<T> list(T... elements) {
        return new ArrayList<>(Arrays.asList(elements));
    }

    @SafeVarargs
    public static <T> Set<T> set(T... elements) {
        return new HashSet<>(Arrays.asList(elements));
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

    public static <T> T pickRandom(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List cannot be null or empty");
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(list.size());
        return list.get(randomIndex);
    }

    public static <T> T pickRandom(Set<T> set) {
        if (set == null || set.isEmpty()) {
            throw new IllegalArgumentException("Set cannot be null or empty");
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(set.size());
        int currentIndex = 0;
        for (T element : set) {
            if (currentIndex == randomIndex) {
                return element;
            }
            currentIndex++;
        }

        throw new IllegalStateException("Failed to pick a random element");
    }
}
