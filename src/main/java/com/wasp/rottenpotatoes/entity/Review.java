package com.wasp.rottenpotatoes.entity;

import lombok.Data;

@Data
public class Review {
    private Long id;
    private String userName;
    private String content;
}
