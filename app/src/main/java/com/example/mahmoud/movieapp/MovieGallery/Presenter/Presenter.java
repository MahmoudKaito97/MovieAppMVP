package com.example.mahmoud.movieapp.MovieGallery.Presenter;

import com.example.mahmoud.movieapp.MovieDetail.View.DetailView;
import com.example.mahmoud.movieapp.MovieGallery.View.MoviesView;

/**
 * Created by MAHMOUD on 7/15/2018.
 */

public interface Presenter {

    public void onCreate(DetailView DV);
    public void onCreate(MoviesView MV);
    public void onResume();
    public void onStart();
    public void onSaveInstanceState();

}
