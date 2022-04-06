package com.wasp.rottenpotatoes.exception.entity;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ErrorMessage {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String path;
}
