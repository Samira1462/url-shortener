package de.example.urlshortener.repository;

import de.example.urlshortener.entity.Url;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UrlRepositoryTest {
    static Map<String, Url> repo;

    @BeforeAll
    public static void initializeRepo() throws Exception {
        Field field = UrlRepository.class.getDeclaredField("repo");
        field.setAccessible(true);
        repo = (Map<String, Url>) field.get(UrlRepository.class);
        repo.clear();
    }

    @Test
    public void getShortenerUrl_whenShortenerUrlExists_thenReturnOptionalWithMatchingEntry() {
        var id = UUID.randomUUID();
        Url url = new Url(id.toString(), "http://home.com", new StringBuilder("http://bl.co/abdcfg"));
        UrlRepository.addUrlShortener(id.toString(), url);

        Optional<Map.Entry<String, Url>> result = UrlRepository.getShortenerUrl(new StringBuilder("http://bl.co/abdcfg"));

        assertTrue(result.isPresent());
        assertEquals(id.toString(), result.get().getKey());
        assertEquals(url, result.get().getValue());

        repo.clear();
    }

    @Test
    public void getShortenerUrl_whenShortenerUrlDoesNotExist_thenReturnEmptyOptional() {
        Optional<Map.Entry<String, Url>> result = UrlRepository.getShortenerUrl(new StringBuilder(""));

        assertFalse(result.isPresent());
    }

    @Test
    public void getUrl_whenOriginalUrlExists_thenReturnOptionalWithMatchingEntry() {
        var id = UUID.randomUUID();
        Url url = new Url(id.toString(), "http://home.com", new StringBuilder("http://bl.co/abdcfg"));
        UrlRepository.addUrlShortener(id.toString(), url);

        Optional<Map.Entry<String, Url>> result = UrlRepository.getOriginalUrl("http://home.com");

        assertTrue(result.isPresent());
        assertEquals(url, result.get().getValue());

        repo.clear();
    }

    @Test
    public void getUrl_whenOriginalUrlDoesNotExist_thenReturnEmptyOptional() {
        Optional<Map.Entry<String, Url>> result = UrlRepository.getOriginalUrl("");

        assertFalse(result.isPresent());
    }

    @Test
    public void addUrlShortener_whenUrlNotInRepository_thenAddUrl() {
        var id = UUID.randomUUID();
        Url url = new Url(id.toString(), "http://home.com", new StringBuilder("http://bl.co/abdcfg"));

        UrlRepository.addUrlShortener(id.toString(), url);

        assertTrue(repo.containsKey(id.toString()));
        assertEquals(url, repo.get(id.toString()));

        repo.clear();
    }

    @Test
    public void addUrlShortener_whenUrlAlreadyInRepository_thenNotAddUrl() {
        var id = UUID.randomUUID().toString();
        Url url1 = new Url(id,"http://home.com", new StringBuilder("http://bl.co/abdcfg"));
        Url url2 = new Url(id,"http://home.com", new StringBuilder("http://bl.co/abdcfg"));

        UrlRepository.addUrlShortener(id, url1);
        UrlRepository.addUrlShortener(id, url2);

        assertEquals(1, repo.size());
        assertTrue(repo.containsKey(id));

        repo.clear();
    }
}