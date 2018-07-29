package com.example.mahmoud.movieapp.MovieGallery.Presenter;

import android.content.Context;

import com.example.mahmoud.movieapp.BuildConfig;
import com.example.mahmoud.movieapp.MovieDetail.View.DetailView;
import com.example.mahmoud.movieapp.MovieGallery.Adapter.ImageAdapter;
import com.example.mahmoud.movieapp.MovieGallery.Model.DBHandler;
import com.example.mahmoud.movieapp.MovieGallery.Model.Movie;
import com.example.mahmoud.movieapp.MovieGallery.View.MainFragment;
import com.example.mahmoud.movieapp.MovieGallery.View.MoviesView;
import com.example.mahmoud.movieapp.Retrofit.MoviesResponse;
import com.example.mahmoud.movieapp.Retrofit.TMDBClient;
import com.example.mahmoud.movieapp.Retrofit.TMDBInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MAHMOUD on 7/15/2018.
 */

public class MoviesPresenter implements Presenter {

    private ArrayList<Movie> movies;
    private DBHandler db;
    private MoviesView MV;
    private static final String API_KEY = BuildConfig.MOVIE_DATABASE_API_KEY;
    private ImageAdapter adapter;
    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public void getMoviesFromDb() {

        movies= db.getAllMovies();
    }



    public void getMoviesFromTMDB(String s,ImageAdapter adapter) {

        TMDBInterface tmdbInterface = TMDBClient.getClient().create(TMDBInterface.class);

        Call<MoviesResponse> call;
        if(s.equals("popular")){
            call = tmdbInterface.getPopular(API_KEY);
        }
        else if(s.equals("top_rated"))
            call = tmdbInterface.getTopRated(API_KEY);

        else return;

        this.adapter = adapter;
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {

                if(response.isSuccessful()){
                    int statusCode = response.code();

                    movies = response.body().getMovies();

                        (MoviesPresenter.this).adapter.notifyDataSetChanged(movies);
                }

            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
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
    public MoviesPresenter(ArrayList<Movie> movies, Context context){

         this.movies=movies;
                 db = new DBHandler(context);

    }

    @Override
    public void onCreate(DetailView DV) {

    }

    @Override
    public void onCreate(MoviesView MV) {

        if(MV instanceof MainFragment){

            MV.swipe();//return the sortType and put it in retrofit
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