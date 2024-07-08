package de.example.urlshortener.service;

import de.example.urlshortener.entity.Url;
import de.example.urlshortener.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static de.example.urlshortener.database.InMemoryDatabase.IN_MEMORY_DB;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UrlShortenerServiceImplTest {

    private static final String ORIGINAL_URL = "http://home.com";

    private static final String SHORT_URL = "https://bl.co/abcd";

    @Autowired
    private UrlShortenerService systemUnderTest;

    @Nested
    class EncodeTest {
        @BeforeEach
        void setUp() {
            IN_MEMORY_DB.clear();
        }

        @Test
        public void encode_whenUrlExists_thenReturnResponseDto() {
            var actual = systemUnderTest.encode(ORIGINAL_URL);

            assertNotNull(actual);
            assertTrue(actual.isPresent());
            assertNotNull(actual.get().shortUrl());
        }
    }

    @Nested
    class DecodeTest {

        @BeforeEach
        void setUp() {
            var url = new Url(UUID.randomUUID().toString(), ORIGINAL_URL, SHORT_URL);
            IN_MEMORY_DB.putIfAbsent(url.id(), url);
        }

        @Test
        public void givenShortUrl_thenReturnOriginalUrl() {

            var actual = systemUnderTest.decode(SHORT_URL);

            assertNotNull(actual);
            assertTrue(actual.isPresent());
            assertEquals(ORIGINAL_URL, actual.get().originalUrl());
        }

        @Test
        public void givenEmptyShortUrl_thenThrowNotFoundException() {
            var givenShorUrl = "";
            assertThrows(NotFoundException.class, () -> systemUnderTest.decode(givenShorUrl));
        }
    }
}

