package com.wasp.rottenpotatoes.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private Long id;
    private String userName;
}
