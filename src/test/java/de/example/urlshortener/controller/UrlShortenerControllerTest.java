package de.example.urlshortener.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.example.urlshortener.dto.RequestDto;
import de.example.urlshortener.dto.ResponseDto;
import de.example.urlshortener.service.UrlShortenerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UrlShortenerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UrlShortenerServiceImpl serviceUnderTest;

    @Test
    void encodeUrl_whenRequestDto_thenReturnShorterUrl() throws Exception {
        RequestDto dto = new RequestDto();
        dto.setUrl("http://home.com");
        ResponseDto expected = new ResponseDto("http://home.com", new StringBuilder("https://bl.co/FVkLdT"));

        when(serviceUnderTest.encode(dto.getUrl())).thenReturn(Optional.of(expected));

        ResultActions actual = mockMvc.perform(post("/api/v1/encode")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        actual.andExpect(status().isCreated())
                .andExpect(jsonPath("$.originalUrl").value(expected.getOriginalUrl()))
                .andExpect(jsonPath("$.shortUrl").value(expected.getShortUrl().toString()))
                .andDo(print());
    }

    @Test
    void encodeUrl_whenInvalidData_thenReturn4XX() throws Exception {
        RequestDto dto = new RequestDto();
        dto.setUrl("http://home.com"+ "}");
        mockMvc.perform(post("/api/v1/encode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void encodeUrl_whenInvalidData_thenReturn5XX() throws Exception {
        mockMvc.perform(post("/api/v1/encode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("http://home.com")))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void decodeShortUrl_whenShorterUrl_thenReturnLongUrl() throws Exception {
        String shortUrl = "https://bl.co/FVkLdT";
        ResponseDto expected = new ResponseDto("http://home.com", new StringBuilder("https://bl.co/FVkLdT"));
        when(serviceUnderTest.decode(shortUrl)).thenReturn(Optional.of(expected));

        ResultActions actual = mockMvc.perform(get("/api/v1/decode")
                .contentType(MediaType.APPLICATION_JSON)
                .param("shortUrl", shortUrl));

        actual.andExpect(status().isOk())
                .andExpect(jsonPath("$.originalUrl").exists());
    }

    @Test
    void decodeShortUrl_whenInvalidData_thenReturn4XX() throws Exception {
        String shortUrl = "";

        mockMvc.perform(get("/api/v1/decode")
                        .param("shortUrl", shortUrl))
                .andExpect(status().is4xxClientError());
    }
}
