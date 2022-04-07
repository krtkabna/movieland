package com.wasp.rottenpotatoes.service.nbu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wasp.rottenpotatoes.entity.nbu.Currency;
import com.wasp.rottenpotatoes.entity.nbu.NBURateDto;
import com.wasp.rottenpotatoes.entity.nbu.Rate;
import com.wasp.rottenpotatoes.exception.NBURequestException;
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
public class NBUServiceImpl implements NBUService {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final List<String> CURRENCY_NAMES =
        Arrays.stream(Currency.values())
            .map(Currency::name)
            .toList();

    @Value("${nbu.path}")
    private String url;

    @Override
    @SneakyThrows
    public List<Rate> getRates(LocalDate date) {
        return parse(getRatesJson());
    }

    private List<Rate> parse(String json) throws IOException {
        return Arrays.stream(MAPPER.readValue(json, NBURateDto[].class))
            .filter(dto -> CURRENCY_NAMES.contains(dto.getCc()))
            .map(mapDtoToRate())
            .toList();
    }

    private String getRatesJson() {
        try {
            log.info("request for url: {}", url);
            var client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

            HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());
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
        return dto -> new Rate(Currency.valueOf(dto.getCc()), dto.getRate());
    }
}
