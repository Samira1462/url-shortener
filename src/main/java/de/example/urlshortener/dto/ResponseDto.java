package de.example.urlshortener.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ResponseDto {
    private String originalUrl;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private StringBuilder shortUrl;
}
