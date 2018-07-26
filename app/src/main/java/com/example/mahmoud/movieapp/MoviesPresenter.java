package com.example.mahmoud.movieapp;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.example.mahmoud.movieapp.Models.Movie;

import java.util.ArrayList;

/**
 * Created by MAHMOUD on 7/15/2018.
 */

public class MoviesPresenter implements Presenter {

    private ArrayList<Movie> movies;
    private DBHandler db;
    private MoviesView MV;

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public void getMoviesFromDb() {

        movies= db.getAllMovies();
    }



    public void getMoviesFromFMC(String s) {


        try{
            new FetchMovieCover().execute(s);
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }


    public MoviesView getMV() {
        return MV;
    }

    public void setMV(MoviesView MV) {
        this.MV = MV;
    }

    public MoviesPresenter(){

        movies=new ArrayList<>();

    }
    public MoviesPresenter(ArrayList<Movie> m, Context context){
        movies = m;

        db = new DBHandler(context);

    }

    @Override
    public void onCreate(MoviesView MV) {

        if(MV instanceof MainFragment){
            MV.showImagesOnGrid();
        }

    }

    @Override
    public void onResume() {

    }



    @Override
    public void onStart() {

    }

    @Override
    public void onSaveInstanceState() {

    }



}
