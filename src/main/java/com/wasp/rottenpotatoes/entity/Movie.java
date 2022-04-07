package com.wasp.rottenpotatoes.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private double rating;
    private double price;

    private String posterLink;
    private List<Country> countries;
    private List<Genre> genres;
    private List<Review> reviews;
}
