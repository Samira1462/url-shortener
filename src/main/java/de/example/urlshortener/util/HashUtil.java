package de.example.urlshortener.util;

import java.util.Random;

public final class HashUtil {
    private HashUtil() {
    }

    public static String generateShortAlias() {
        var random = new Random();
        var characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        var length = 6;

        return random.ints(length, 0, characters.length())
                .mapToObj(characters::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
