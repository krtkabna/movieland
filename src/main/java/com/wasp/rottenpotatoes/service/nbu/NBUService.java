package com.wasp.rottenpotatoes.service.nbu;

import com.wasp.rottenpotatoes.entity.nbu.Rate;
import java.time.LocalDate;
import java.util.List;

public interface NBUService {
    List<Rate> getRates(LocalDate date);
}
