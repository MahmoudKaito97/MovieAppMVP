package com.example.mahmoud.movieapp.MovieDetail.Presenter;

import android.content.Context;

import com.example.mahmoud.movieapp.BuildConfig;
import com.example.mahmoud.movieapp.MovieDetail.Adapters.ReviewAdapter;
import com.example.mahmoud.movieapp.MovieDetail.Adapters.TrailerAdapter;
import com.example.mahmoud.movieapp.MovieDetail.Models.Review;
import com.example.mahmoud.movieapp.MovieDetail.View.DetailFragment;
import com.example.mahmoud.movieapp.MovieDetail.View.DetailView;
import com.example.mahmoud.movieapp.MovieGallery.Model.Movie;
import com.example.mahmoud.movieapp.MovieGallery.Presenter.Presenter;
import com.example.mahmoud.movieapp.MovieGallery.View.MoviesView;
import com.example.mahmoud.movieapp.Retrofit.ReviewResponse;
import com.example.mahmoud.movieapp.Retrofit.TMDBClient;
import com.example.mahmoud.movieapp.Retrofit.TMDBInterface;
import com.example.mahmoud.movieapp.Retrofit.TrailerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MAHMOUD on 7/28/2018.
 */

public class DetailPresenter implements Presenter {
    private TrailerAdapter mTrailerAdapter;
    private ReviewAdapter mReviewAdapter;

    private static final String API_KEY = BuildConfig.MOVIE_DATABASE_API_KEY;

    private Movie movie;
    private DetailView DV;

    public DetailPresenter(Context context){
     movie = new Movie();
     mTrailerAdapter = new TrailerAdapter(context,this);
     mReviewAdapter = new ReviewAdapter(context,this);

    }


    @Override
    public void onCreate(DetailView DV) {
        if(DV instanceof DetailFragment){

            DV.showReviews();
            DV.showTrailers();
            DV.markAsFavoriteORNOT();
            DV.showDetails();
        }
    }

    @Override
    public void onCreate(MoviesView MV) {

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
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    public void getTrailers(){
        TMDBInterface tmdbInterface = TMDBClient.getClient().create(TMDBInterface.class);
        Call<TrailerResponse> call = tmdbInterface.getTrailers(movie.getMovieId(),API_KEY);
        call.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
              if(response.isSuccessful()){
                  int statusCode= response.code();
                  movie.setTrailers(response.body().getTrailers());
                  mTrailerAdapter.notifyDataSetChanged(movie);

              }

                 }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    public void getReviews(){
        TMDBInterface tmdbInterface = TMDBClient.getClient().create(TMDBInterface.class);
        Call<ReviewResponse> call = tmdbInterface.getReviews(movie.getMovieId(),API_KEY);
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
            if(response.isSuccessful()){
                int statusCode= response.code();
                movie.setReviews(response.body().getReviewsOfMovie());
                mReviewAdapter.notifyDataSetChanged(movie);
            }

            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
