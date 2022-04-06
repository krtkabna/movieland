package com.wasp.rottenpotatoes.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
@Value //makes class final, its fields - private final
public class Genre {
    Long id;
    String name;
}
