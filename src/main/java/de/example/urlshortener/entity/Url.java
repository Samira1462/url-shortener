package de.example.urlshortener.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Url {
    private String id;
    private String longUrl;
    private StringBuilder shortenerUrl;
}

