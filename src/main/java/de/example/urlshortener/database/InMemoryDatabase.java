package de.example.urlshortener.database;

import de.example.urlshortener.entity.Url;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class InMemoryDatabase {

    public static final Map<String, Url> IN_MEMORY_DB = Collections.synchronizedMap(new HashMap<>());

    private InMemoryDatabase() {
    }
}
