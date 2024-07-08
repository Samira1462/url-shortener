package de.example.urlshortener.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class HashUtilTest {

    @Test
    void testGenerateUrlAlias() {
        var actual = HashUtil.generateUrlAlias();
        assertEquals(6, actual.length());
    }
}
