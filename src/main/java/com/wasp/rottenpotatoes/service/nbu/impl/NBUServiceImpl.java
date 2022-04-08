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
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class NBUServiceImpl implements NBUService {
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    @Value("${nbu.path}")
    private String url;

    @Override
    @SneakyThrows
    public List<Rate> getRates(LocalDate date) {
        return parse(getRatesJson());
    }

    private List<Rate> parse(String json) throws IOException {
        return Arrays.stream(objectMapper.readValue(json, NBURateDto[].class))
            .filter(dto -> Currency.contains(dto.getCurrencyCode()))//fixme кидається на те чого нема в єнамі НЕ ТИМ ЕКСЕПШЕНОМ
            .map(mapDtoToRate())
            .toList();
    }

    private String getRatesJson() {
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

    private Function<NBURateDto, Rate> mapDtoToRate() {
        return dto -> new Rate(Currency.valueOf(dto.getCurrencyCode()), dto.getRate());
    }
}
