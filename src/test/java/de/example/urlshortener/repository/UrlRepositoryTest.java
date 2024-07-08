package de.example.urlshortener.repository;

import de.example.urlshortener.entity.Url;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static de.example.urlshortener.database.InMemoryDatabase.IN_MEMORY_DB;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UrlRepositoryTest {

    @Autowired
    private UrlRepository systemUnderTest;

    @BeforeEach
    void setUp() {
        IN_MEMORY_DB.clear();
    }

    @Test
    public void getShortenerUrl_whenShortenerUrlExists_thenReturnOptionalWithMatchingEntry() {
        var id = UUID.randomUUID();
        var url = new Url(id.toString(), "http://home.com", "http://bl.co/abdcfg");
        systemUnderTest.addShortedUrl(id.toString(), url);

        var actual = systemUnderTest.getShortUrl("http://bl.co/abdcfg");

        assertTrue(actual.isPresent());
        assertEquals(id.toString(), actual.get().id());
        assertEquals(url.shortUrl(), actual.get().shortUrl());
    }

    @Test
    public void getShortenerUrl_whenShortUrlDoesNotExist_thenReturnEmptyOptional() {
        var actual = systemUnderTest.getShortUrl("");

        assertFalse(actual.isPresent());
    }

    @Test
    public void getUrl_whenOriginalUrlExists_thenReturnOptionalWithMatchingEntry() {
        var id = UUID.randomUUID();
        var url = new Url(id.toString(), "http://home.com", "http://bl.co/abdcfg");
        systemUnderTest.addShortedUrl(id.toString(), url);

        var actual = systemUnderTest.getOriginalUrl("http://home.com");

        assertTrue(actual.isPresent());
        assertEquals(url, actual.get());
    }

    @Test
    public void getUrl_whenOriginalUrlDoesNotExist_thenReturnEmptyOptional() {
        var actual = systemUnderTest.getOriginalUrl("");

        assertFalse(actual.isPresent());
    }

    @Test
    public void addUrlShortener_whenUrlNotInRepository_thenAddUrl() {
        var id = UUID.randomUUID();
        var url = new Url(id.toString(), "http://home.com", "http://bl.co/abdcfg");

        systemUnderTest.addShortedUrl(id.toString(), url);

        assertTrue(IN_MEMORY_DB.containsKey(id.toString()));
        assertEquals(url, IN_MEMORY_DB.get(id.toString()));
    }

    @Test
    public void addUrlShortener_whenUrlAlreadyInRepository_thenNotAddShortedUrl() {
        var id = UUID.randomUUID().toString();
        var url1 = new Url(id, "http://home.com", "http://bl.co/abdcfg");
        var url2 = new Url(id, "http://home.com", "http://bl.co/abdcfg");

        systemUnderTest.addShortedUrl(id, url1);
        systemUnderTest.addShortedUrl(id, url2);

        assertEquals(1, IN_MEMORY_DB.size());
        assertTrue(IN_MEMORY_DB.containsKey(id));

    }
}
