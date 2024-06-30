package de.example.urlshortener.repository;

import de.example.urlshortener.entity.Url;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class UrlRepository {
    private UrlRepository() {
    }
    private static final Map<String, Url> repo = new HashMap<>();

    public static Optional<Map.Entry<String, Url>> getShortenerUrl(StringBuilder shortenerUrl) {
        return repo.entrySet().stream()
                .filter(url -> url.getValue().getShortenerUrl().compareTo(shortenerUrl) == 0)
                .findFirst();

    }

    public static Optional<Map.Entry<String, Url>> getOriginalUrl(String originalUrl) {
        return repo.entrySet().stream()
                .filter(url -> url.getValue().getLongUrl().equals(originalUrl))
                .findFirst();
    }
    public static void addUrlShortener(String id, Url url) {
        if (!repo.containsValue(url)) {
            UrlRepository.repo.put( id ,url);
        }
    }
}