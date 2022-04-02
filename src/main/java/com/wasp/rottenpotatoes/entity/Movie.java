package com.wasp.rottenpotatoes.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
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
    private List<County> counties;
    private List<Genre> genres;
    private List<Review> reviews;
}
