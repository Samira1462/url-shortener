package de.example.urlshortener.util;

import java.util.Random;
import java.util.stream.Collectors;

public final class HashUtil {

    private static final int ALIAS_LENGTH = 6;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new Random();

    private HashUtil() {
    }

    public static String generateUrlAlias() {
        return RANDOM.ints(ALIAS_LENGTH, 0, CHARACTERS.length())
                .mapToObj(index -> Character.toString(CHARACTERS.charAt(index)))
                .collect(Collectors.joining());
    }
}

