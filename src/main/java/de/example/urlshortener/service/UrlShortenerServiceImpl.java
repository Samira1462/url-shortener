package de.example.urlshortener.service;

import de.example.urlshortener.dto.ResponseDto;
import de.example.urlshortener.entity.Url;
import de.example.urlshortener.exception.NotFoundException;
import de.example.urlshortener.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static de.example.urlshortener.util.HashUtil.generateUrlAlias;
import static java.util.Objects.requireNonNull;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${baseUrl}")
    private String baseUrl;

    @Value("${NotFound.ErrorMessage}")
    private String errorMessage;

    private final UrlRepository urlRepository;

    public UrlShortenerServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Optional<ResponseDto> encode(String originalUrl) {
        requireNonNull(originalUrl);

        var existUrl = urlRepository.getOriginalUrl(originalUrl);
        if (existUrl.isPresent()) {
            logger.info("URL already exist, return exist url: {}", existUrl);
            return Optional.of(new ResponseDto(originalUrl, existUrl.get().shortUrl()));
        }

        var shortUrl = baseUrl + generateUrlAlias();
        var url = new Url(UUID.randomUUID().toString(), originalUrl, shortUrl);
        urlRepository.addShortedUrl(url.id(), url);
        logger.info("Added new short url: {}", shortUrl);

        return Optional.of(new ResponseDto(originalUrl, shortUrl));
    }

    public Optional<ResponseDto> decode(String shortUrl) {
        requireNonNull(shortUrl);

        logger.info("Get original url of shortUrl:{}", shortUrl);
        return Optional.ofNullable(urlRepository.getShortUrl(shortUrl)
                .map(url -> new ResponseDto(url.originalUrl(), null))
                .orElseThrow(() -> new NotFoundException(errorMessage)));
    }
}

