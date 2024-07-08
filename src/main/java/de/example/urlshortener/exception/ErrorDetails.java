package de.example.urlshortener.exception;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;


public record ErrorDetails(
        LocalDateTime timestamp,
        String message,
        String details
) {
    public static ErrorDetails from(Throwable throwable) {
        return new ErrorDetails(now(), throwable.getMessage(), throwable.getClass().getSimpleName());
    }

    public static ErrorDetails from(Throwable throwable, String details) {
        return new ErrorDetails(now(), throwable.getMessage(), details);
    }
}