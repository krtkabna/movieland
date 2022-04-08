package com.wasp.rottenpotatoes.entity.nbu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NBURateDto {
    @JsonProperty("cc")
    private String currencyCode;
    private double rate;
}
