package com.wasp.rottenpotatoes.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieRequest {
    private SortingStrategy sortingStrategy;
}
