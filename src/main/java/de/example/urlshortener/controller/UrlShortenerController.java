package de.example.urlshortener.controller;

import de.example.urlshortener.dto.RequestDto;
import de.example.urlshortener.dto.ResponseDto;
import de.example.urlshortener.service.UrlShortenerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.http.ResponseEntity.badRequest;

@RestController
@Validated
@RequestMapping("/api/v1")
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/encode")
    public ResponseEntity<ResponseDto> encode(@Valid @RequestBody RequestDto dto) {
        return urlShortenerService.encode(dto.url())
                .map(response -> ResponseEntity.created(URI.create(response.shortUrl())).body(response))
                .orElseGet(() -> badRequest().build());
    }

    @GetMapping("/decode")
    public ResponseEntity<ResponseDto> decode(@Valid @NonNull @RequestParam("shortUrl") String url) {
        return urlShortenerService.decode(url)
                .map(ResponseEntity::ok)
                .orElseGet(() -> badRequest().build());
    }
}
