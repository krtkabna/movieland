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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class NBUServiceImpl implements NBUService {
    private static final String APPEND_DATE_TO_URL_FORMAT = "&date=%s";
    private static final String NBU_DATE_PATTERN = "yyyyMMdd";
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private final ObjectMapper objectMapper;

    @Value("${nbu.path}")
    private String path;

    @Override
    @SneakyThrows
    public List<Rate> getRates(LocalDate date) {
        String url = formatUrlWithDate(date);
        return parse(getRatesJson(url));
    }

    private String formatUrlWithDate(LocalDate date) {
        return path + APPEND_DATE_TO_URL_FORMAT.formatted(
            date.format(DateTimeFormatter.ofPattern(NBU_DATE_PATTERN)));
    }

    private String getRatesJson(String url) {
        try {
            ResponseEntity<String> response = REST_TEMPLATE.getForEntity(url, String.class);
            return response.getBody();
        } catch (RestClientException e) {
            throw new NBURequestException("NBU request error, url: " + url, e);
        }
    }

    private List<Rate> parse(String json) throws IOException {
        return Arrays.stream(objectMapper.readValue(json, NBURateDto[].class))
            .filter(dto -> Currency.contains(dto.getCurrencyCode()))
            .map(mapDtoToRate())
            .toList();
    }

    private Function<NBURateDto, Rate> mapDtoToRate() {
        return dto -> new Rate(Currency.valueOf(dto.getCurrencyCode()), dto.getRate());
    }
}
