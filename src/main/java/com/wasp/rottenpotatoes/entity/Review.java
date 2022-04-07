package com.wasp.rottenpotatoes.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Review {
    private Long id;
    private User user;
    private String content;
}
