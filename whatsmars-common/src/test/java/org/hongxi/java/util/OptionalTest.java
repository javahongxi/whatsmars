package org.hongxi.java.util;

import org.hongxi.java.Address;
import org.hongxi.java.User;

import java.util.Optional;

/**
 * Created by shenhongxi on 2019/1/22.
 */
public class OptionalTest {

    public static void main(String[] args) {
        Optional<String> emptyOpt = Optional.empty();
        System.out.println(emptyOpt.isPresent());
        System.out.println(emptyOpt.orElse("a"));
        System.out.println(emptyOpt.isPresent());
        System.out.println(emptyOpt.orElseGet(() -> {
            String s1 = "a";
            String s2 = "b";
            return s1 + s2;
        }));

        String s = "Hello";
        Optional<String> notNullOpt = Optional.of(s);
        if (notNullOpt.isPresent()) {
            System.out.println(notNullOpt.get());
        }
        notNullOpt = notNullOpt.filter(s1 -> s1.contains("xyz"));
        System.out.println(notNullOpt.isPresent());
    }

    public static String getName(User user) {
        return Optional.ofNullable(user)
                .map(User::getName)
                .orElse("Unknown");
    }

    public static String getCity(User user) throws IllegalArgumentException {
        return Optional.ofNullable(user)
                .map(User::getAddress)
                .map(Address::getCity)
                .orElseThrow(() -> new IllegalArgumentException("params cant not be null"));
    }
}
