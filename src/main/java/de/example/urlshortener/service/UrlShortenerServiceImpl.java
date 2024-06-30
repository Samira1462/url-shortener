package de.example.urlshortener.service;

import de.example.urlshortener.entity.Url;
import de.example.urlshortener.exception.NotFoundException;
import de.example.urlshortener.repository.UrlRepository;
import de.example.urlshortener.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static de.example.urlshortener.util.HashUtil.generateShortAlias;

@Slf4j
@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    private final String baseUrl;
    private final String errorMessage;

    public UrlShortenerServiceImpl(
            @Value("${baseUrl}") String baseUrl,
            @Value("${NotFound.ErrorMessage}") String errorMessage
    ) {
        this.baseUrl = baseUrl;
        this.errorMessage = errorMessage;
    }

    public Optional<ResponseDto> encode(String longUrl) {
        Optional<Map.Entry<String, Url>> existUrl = UrlRepository.getOriginalUrl(longUrl);
        log.info("get url: {}", existUrl);
        var shortenedURL = new StringBuilder();
        if (existUrl.isPresent()) {
            log.info("return exist url: {}", existUrl);
            var shortUrl = existUrl.get().getValue().getShortenerUrl();
            return Optional.of(new ResponseDto(longUrl, shortenedURL.append(shortUrl)));
        }

        UUID id = UUID.randomUUID();
        var url = new Url(id.toString(), longUrl, shortenedURL.append(baseUrl).append(generateShortAlias()));
        UrlRepository.addUrlShortener(id.toString() ,url);
        log.info("add short url");
        return Optional.of(new ResponseDto(longUrl, shortenedURL));
    }

    public Optional<ResponseDto> decode(String shortenerUrl) {
        log.info("decode short url");
        return Optional.ofNullable(UrlRepository.getShortenerUrl(new StringBuilder(shortenerUrl))
                .map(entry -> {
                    Url url = entry.getValue();
                    return new ResponseDto(url.getLongUrl(), null);
                })
                .orElseThrow(() -> new NotFoundException(errorMessage)));
    }
}
