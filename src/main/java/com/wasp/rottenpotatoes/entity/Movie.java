package com.wasp.rottenpotatoes.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class Movie {
    private Long id;
    private String nameRussian;
    private String nameNative;
    private String description;
    private int releaseYear;
    private BigDecimal rating;
    private BigDecimal price;

    private String posterLink;
    private List<Country> countries;
    private List<Genre> genres;
    private List<Review> reviews;

    public static BigDecimal convertDoubleToBigDecimal(double number) {
        return BigDecimal.valueOf(number).setScale(2, RoundingMode.CEILING);
    }
}
