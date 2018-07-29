package com.example.mahmoud.movieapp.MovieDetail.View;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import com.example.mahmoud.movieapp.R;


public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if(savedInstanceState==null)
     getSupportFragmentManager().beginTransaction().add(R.id.content_detail,new DetailFragment()).commit();

        
    }

}
