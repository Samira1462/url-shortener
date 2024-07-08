package de.example.urlshortener.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseDto(
        String originalUrl,
        String shortUrl
) {
}
