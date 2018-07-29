package com.example.mahmoud.movieapp.MovieDetail.View;


import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mahmoud.movieapp.BuildConfig;
import com.example.mahmoud.movieapp.MovieDetail.Adapters.ReviewAdapter;
import com.example.mahmoud.movieapp.MovieDetail.Adapters.TrailerAdapter;
import com.example.mahmoud.movieapp.MovieDetail.Presenter.DetailPresenter;
import com.example.mahmoud.movieapp.MovieGallery.View.MainActivity;
import com.example.mahmoud.movieapp.MovieGallery.Model.Movie;
import com.example.mahmoud.movieapp.MovieGallery.View.MainFragment;
import com.example.mahmoud.movieapp.MovieDetail.Models.Review;
import com.example.mahmoud.movieapp.MovieDetail.Models.Trailer;
import com.example.mahmoud.movieapp.R;
import com.example.mahmoud.movieapp.Retrofit.TMDBClient;
import com.example.mahmoud.movieapp.Retrofit.TMDBInterface;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DetailFragment extends Fragment implements DetailView{


  public static  Movie movie = new Movie();
    ReviewAdapter revAdapter ;
    TrailerAdapter trailerAdapter;
    boolean orient = true;
    private TextView overview;
    private TextView date;
    private TextView rate;
    private ImageView poster;
    private TextView title;
    private RatingBar ratingBar;
    private ShineButton shineButton;
    private RecyclerView trailerList;
    private RecyclerView reviewList;
    private DetailPresenter mPresenter;
    public DetailFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
if(savedInstanceState!=null){
    if(savedInstanceState.getParcelable("abc").equals(movie)){
        orient=false;
        movie = savedInstanceState.getParcelable("abc");
        revAdapter = new ReviewAdapter(getContext(),mPresenter);
        trailerAdapter = new TrailerAdapter(getContext(),mPresenter);
        mPresenter.setMovie(movie);
    }
    else
        orient=true;
}*/
 mPresenter = new DetailPresenter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,
                             Bundle savedInstanceState) {

        if(!MainActivity.twoPane){
        mPresenter.setMovie((Movie) getActivity().getIntent().getParcelableExtra("MovieObject"));
        }

       // movie.getTrailers().clear();
        //movie.getReviews().clear();

        revAdapter = new ReviewAdapter(getContext(),mPresenter);
        trailerAdapter = new TrailerAdapter(getContext(),mPresenter);
      //  new Trailer_ReviewAsyncClass().execute(movie);

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
//        View header = inflater.inflate(R.layout.row_standard_detail,null,false);
         shineButton = (ShineButton) rootView.findViewById(R.id.shine_button);
        shineButton.init(getActivity());
         overview = (TextView) rootView.findViewById(R.id.overview);
        date = (TextView) rootView.findViewById(R.id.release_date);
        rate = (TextView) rootView.findViewById(R.id.rating);
         poster = (ImageView) rootView.findViewById(R.id.poster);
        title = (TextView) rootView.findViewById(R.id.title);
        ratingBar = (RatingBar) rootView.findViewById(R.id.rating_bar);
        //SimpleDateFormat df = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);



         trailerList = (RecyclerView) rootView.findViewById(R.id.trailer_recyclerView);
        trailerList.setLayoutManager(new LinearLayoutManager(getContext()));
        trailerList.setAdapter(trailerAdapter);

         reviewList = (RecyclerView) rootView.findViewById(R.id.review_recyclerView);
        reviewList.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewList.setAdapter(revAdapter);



        return rootView;
    }

    @Override
    public void onResume() {

        /*if(orient) {
         /*   movie.getTrailers().clear();
            movie.getReviews().clear();

        }*/
        mPresenter.onCreate(this);
        super.onResume();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putParcelable("abc",movie);
    }

    @Override
    public void showDetails() {
        Picasso.with(getContext())
                .load("http://image.tmdb.org/t/p/w185/"+(mPresenter.getMovie()).getPath())
                .into(poster);

        overview.setText(mPresenter.getMovie().getOverview());
        date.setText(convertDate());
        rate.setText(""+Math.round( (mPresenter.getMovie().getVoteAverage()/2.0)*10.0)/10.0+" out of 5");
        title.setText(mPresenter.getMovie().getTitle());
        //new Trailer_ReviewAsyncClass().execute(movie);

        ratingBar.setRating(Float.parseFloat(""+Math.round( (mPresenter.getMovie().getVoteAverage()/2.0)*10.0)/10.0));
    }

    @Override
    public void markAsFavoriteORNOT() {


        if(MainFragment.db.searchForMovie(mPresenter.getMovie().getMovieId()))
        {
            shineButton.setChecked(true);
        }
        shineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainFragment.db.searchForMovie(mPresenter.getMovie().getMovieId())){
                    MainFragment.db.deleteMovie(mPresenter.getMovie());
                }
                else
                    MainFragment.db.addMovie(mPresenter.getMovie());
            }
        });


    }

    @Override
    public void showTrailers() {

       mPresenter.getTrailers(trailerAdapter);

    }

    @Override
    public void showReviews() {
        mPresenter.getReviews(revAdapter);
    }

    @Override
    public String convertDate() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date dat=new Date();
        try {
            dat = formatter.parse(mPresenter.getMovie().getReleaseDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat f = new SimpleDateFormat("MMMM d, yyyy");

        String s = f.format(dat);
        return  s;
    }


}
