package com.wasp.rottenpotatoes.service.nbu;

import com.wasp.rottenpotatoes.entity.nbu.Currency;
import java.time.LocalDate;

public interface RateService {
    double getRate(Currency currency, LocalDate date);
}
