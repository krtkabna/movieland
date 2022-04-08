package com.wasp.rottenpotatoes.service.nbu.impl;

import com.wasp.rottenpotatoes.entity.nbu.Currency;
import com.wasp.rottenpotatoes.entity.nbu.Rate;
import com.wasp.rottenpotatoes.service.nbu.NBUService;
import com.wasp.rottenpotatoes.service.nbu.RateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {
    private final Map<LocalDate, List<Rate>> cache = new ConcurrentHashMap<>();
    private final NBUService nbuService;

    @Override
    //TODO вирішити питання того що курс оновлюється о 15:30
    public double getRate(Currency currency, LocalDate date) {
        List<Rate> rates = cache.computeIfAbsent(date, nbuService::getRates);

        return rates.stream()
            .filter(rate -> rate.currency() == currency)
            .map(Rate::rate)
            .findFirst().orElse(1D);
    }
}
