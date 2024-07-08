package de.example.urlshortener.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.example.urlshortener.dto.RequestDto;
import de.example.urlshortener.dto.ResponseDto;
import de.example.urlshortener.service.UrlShortenerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UrlShortenerControllerTest {

    public static final String ENCODE_ENDPOINT = "/api/v1/encode";
    public static final String DECODE_ENDPOINT = "/api/v1/decode";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlShortenerService urlShortenerService;

    @Nested
    @DisplayName("encoding unit tests")
    class EncodeUrlTest {

        @Test
        void givenOriginalUrl_thenReturnShorterUrl() throws Exception {
            var givenRequestBody = new RequestDto("http://home.com");
            var expected = new ResponseDto("http://home.com", "https://bl.co/FVkLdT");

            when(urlShortenerService.encode(givenRequestBody.url())).thenReturn(Optional.of(expected));

            mockMvc.perform(post(ENCODE_ENDPOINT)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(givenRequestBody)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.originalUrl").value(expected.originalUrl()))
                    .andExpect(jsonPath("$.shortUrl").value(expected.shortUrl()))
                    .andDo(print());
        }

        @Test
        void givenInvalidOriginalUrl_thenReturn4XX() throws Exception {
            var givenRequestBody = new RequestDto("home.com");
            mockMvc.perform(post(ENCODE_ENDPOINT)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(givenRequestBody)))
                    .andExpect(status().is4xxClientError());
        }

        @Test
        void givenWrongBodyType_thenReturn5XX() throws Exception {
            var givenRequestBody = "http://home.com";
            mockMvc.perform(post(ENCODE_ENDPOINT)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(givenRequestBody)))
                    .andExpect(status().is5xxServerError());
        }

    }

    @Nested
    @DisplayName("decoding unit tests")
    class DecodeUrlTest {

        @Test
        void givenShortUrl_thenReturnOriginalUrl() throws Exception {
            var givenShortUrl = "https://bl.co/FVkLdT";
            var expected = new ResponseDto("http://home.com", "https://bl.co/FVkLdT");

            when(urlShortenerService.decode(any())).thenReturn(Optional.of(expected));

            mockMvc.perform(get(DECODE_ENDPOINT)
                            .contentType(APPLICATION_JSON)
                            .param("shortUrl", givenShortUrl))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.originalUrl").exists());
        }

        @Test
        void givenEmptyUrl_thenReturn4XX() throws Exception {
            var givenShortUrl = "";

            mockMvc.perform(get(DECODE_ENDPOINT)
                            .param("shortUrl", givenShortUrl))
                    .andExpect(status().is4xxClientError());
        }
    }
}
