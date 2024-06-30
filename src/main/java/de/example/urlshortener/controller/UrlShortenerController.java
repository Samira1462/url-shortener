package de.example.urlshortener.controller;

import de.example.urlshortener.dto.RequestDto;
import de.example.urlshortener.dto.ResponseDto;
import de.example.urlshortener.service.UrlShortenerServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UrlShortenerController {
    private final UrlShortenerServiceImpl shortenerService;
    @PostMapping("/encode")
    public ResponseEntity<ResponseDto> encode(@Valid @RequestBody RequestDto urlDto) {
        return shortenerService.encode(urlDto.getUrl())
                .map(responseDto -> new ResponseEntity<>(responseDto, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/decode")
    public ResponseEntity<ResponseDto> decode(@Valid @NonNull @RequestParam String shortUrl) {
        return shortenerService.decode(shortUrl)
                .map(responseDto -> new ResponseEntity<>(responseDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}
