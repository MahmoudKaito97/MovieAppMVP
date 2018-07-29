package com.example.mahmoud.movieapp.Retrofit;

import com.example.mahmoud.movieapp.MovieDetail.Models.Review;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by MAHMOUD on 7/28/2018.
 */

public class ReviewResponse {
    @SerializedName("results")
    ArrayList<Review> reviewsOfMovie;

    public ReviewResponse() {
        this.reviewsOfMovie = new ArrayList<>();
    }

    public ArrayList<Review> getReviewsOfMovie() {
        return reviewsOfMovie;
    }

    public void setReviewsOfMovie(ArrayList<Review> reviewsOfMovie) {
        this.reviewsOfMovie = reviewsOfMovie;
    }
}
