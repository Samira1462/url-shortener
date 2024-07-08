package de.example.urlshortener.entity;

public record Url(
        String id,
        String originalUrl,
        String shortUrl
) {
}