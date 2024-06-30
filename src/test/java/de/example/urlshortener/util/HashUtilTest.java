package de.example.urlshortener.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashUtilTest {
    @Test
    public void generateShortAliasLength() {
        String alias = HashUtil.generateShortAlias();
        assertEquals(6, alias.length(), "Generated alias should have a length of 6");
    }

    @Test
    public void generateShortAliasCharacterSet() {
        String alias = HashUtil.generateShortAlias();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (char c : alias.toCharArray()) {
            assertTrue(characters.contains(String.valueOf(c)), "Generated alias should only contain characters from the allowed set");
        }
    }
}