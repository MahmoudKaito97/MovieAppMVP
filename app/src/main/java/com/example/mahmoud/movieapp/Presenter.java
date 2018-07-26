package com.example.mahmoud.movieapp;

import android.content.Context;
import android.view.View;

/**
 * Created by MAHMOUD on 7/15/2018.
 */

public interface Presenter {

    public void onCreate(MoviesView MV);
    public void onResume();
    public void onStart();
    public void onSaveInstanceState();

}
