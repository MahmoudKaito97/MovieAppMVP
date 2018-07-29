package com.example.mahmoud.movieapp.Retrofit;

import com.example.mahmoud.movieapp.MovieGallery.Model.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by MAHMOUD on 7/28/2018.
 */

public class MoviesResponse {
    @SerializedName("results")
    ArrayList<Movie> listOfAllMovies;

    public MoviesResponse()
    {
    listOfAllMovies=new ArrayList<>();
    }
    public ArrayList<Movie> getMovies(){return listOfAllMovies;}
    public void setMovies(ArrayList<Movie> M){listOfAllMovies=M;}

}
