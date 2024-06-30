package de.example.urlshortener.service;

import de.example.urlshortener.dto.ResponseDto;

import java.util.Optional;

public interface UrlShortenerService {

    Optional<ResponseDto> encode(String longUrl);

    Optional<ResponseDto> decode(String shortenerUrl);
}
