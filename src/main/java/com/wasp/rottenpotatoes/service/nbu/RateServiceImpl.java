package com.wasp.rottenpotatoes.service.nbu;

import com.wasp.rottenpotatoes.entity.nbu.Currency;
import com.wasp.rottenpotatoes.entity.nbu.Rate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {
    private final Map<LocalDate, List<Rate>> cache = new HashMap<>();
    private final NBUService nbuService;

    @Override
    public double getRate(Currency currency, LocalDate date) {
        List<Rate> rates = cache.computeIfAbsent(date, nbuService::getRates);

        return rates.stream()
            .filter(rate -> rate.currency() == currency)
            .map(Rate::rate)
            .findFirst().orElse(1D);
    }
}
