package com.wasp.rottenpotatoes.entity.nbu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NBURateDto {
    private String cc;
    private double rate;
}
