package de.example.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestDto(
        @NotBlank
        String url
) {
}
