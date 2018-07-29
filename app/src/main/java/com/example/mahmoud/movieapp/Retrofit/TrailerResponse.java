package com.example.mahmoud.movieapp.Retrofit;

import com.example.mahmoud.movieapp.MovieDetail.Models.Trailer;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by MAHMOUD on 7/28/2018.
 */

public class TrailerResponse {
    @SerializedName("results")
    ArrayList<Trailer> mTrailers;

    public TrailerResponse() {
        mTrailers = new ArrayList<>();
    }

    public ArrayList<Trailer> getTrailers() {
        return mTrailers;
    }

    public void setTrailers(ArrayList<Trailer> trailers) {
        mTrailers = trailers;
    }
}
