package com.wasp.rottenpotatoes.service.nbu.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wasp.rottenpotatoes.entity.nbu.Currency;
import com.wasp.rottenpotatoes.entity.nbu.NBURateDto;
import com.wasp.rottenpotatoes.entity.nbu.Rate;
import com.wasp.rottenpotatoes.service.nbu.NBUService;
import com.wasp.rottenpotatoes.web.exception.NBURequestException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class NBUServiceImpl implements NBUService {
    public static final String APPEND_DATE_TO_URL_FORMAT = "&date=%s";
    public static final String NBU_DATE_PATTERN = "yyyyMMdd";
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;
    private final RestTemplate restTemplate;

    @Value("${nbu.path}")
    private String path;

    @Override
    @SneakyThrows
    public List<Rate> getRates(LocalDate date) {
        String url = getUrl(date);
        return parse(getRatesJsonRestTemplate(url));
    }

    private String getUrl(LocalDate date) {
        return path + APPEND_DATE_TO_URL_FORMAT.formatted(
            date.format(DateTimeFormatter.ofPattern(NBU_DATE_PATTERN)));
    }

    private List<Rate> parse(String json) throws IOException {
        return Arrays.stream(objectMapper.readValue(json, NBURateDto[].class))
            .filter(dto -> Currency.contains(dto.getCurrencyCode()))
            .map(mapDtoToRate())
            .toList();
    }

    private String getRatesJsonHttpClient(String url) {
        try {
            log.info("request for url: {}", url);
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

            HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception ex) {
            if (ex instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            log.error("NBU request error, url: {}", url, ex);
            throw new NBURequestException("NBU request error, url: " + url);
        }
    }

    private String getRatesJsonRestTemplate(String url) {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    private Function<NBURateDto, Rate> mapDtoToRate() {
        return dto -> new Rate(Currency.valueOf(dto.getCurrencyCode()), dto.getRate());
    }
}
