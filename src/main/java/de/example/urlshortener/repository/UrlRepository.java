package de.example.urlshortener.repository;

import de.example.urlshortener.entity.Url;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static de.example.urlshortener.database.InMemoryDatabase.IN_MEMORY_DB;

@Repository
public class UrlRepository {


    private UrlRepository() {
    }

    public Optional<Url> getShortUrl(String shortUrl) {
        return IN_MEMORY_DB.values()
                .stream()
                .filter(url -> url.shortUrl().equals(shortUrl))
                .findFirst();

    }

    public Optional<Url> getOriginalUrl(String originalUrl) {
        return IN_MEMORY_DB.values()
                .stream()
                .filter(url -> url.originalUrl().equals(originalUrl))
                .findFirst();
    }

    public void addShortedUrl(String id, Url url) {
        IN_MEMORY_DB.putIfAbsent(id, url);
    }
}
