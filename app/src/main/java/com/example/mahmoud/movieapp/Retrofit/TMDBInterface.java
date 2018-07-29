package com.example.mahmoud.movieapp.Retrofit;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by MAHMOUD on 7/28/2018.
 */

public interface TMDBInterface {



    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRated(@Query("api_key") String API_KEY );

    @GET("movie/popular")
    Call<MoviesResponse> getPopular(@Query("api_key") String API_KEY );

/*    @GET("movies/{id}")
    Call<MoviesResponse> getMoviesDetails(@Path("id")int id,@Query("api_key") String API_KEY );*/

    @GET("movie/{id}/videos")
    Call<TrailerResponse> getTrailers(@Path("id") int id, @Query("api_key") String API_KEY );

    @GET("movie/{id}/reviews")
    Call<ReviewResponse> getReviews(@Path("id") int id, @Query("api_key") String API_KEY );

}
