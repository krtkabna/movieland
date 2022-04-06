package com.wasp.rottenpotatoes.request;

import com.wasp.rottenpotatoes.entity.SortOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieRequest {
    private SortingStrategy sortingStrategy;
}
