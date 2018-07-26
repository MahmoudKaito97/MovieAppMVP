package com.example.mahmoud.movieapp;

import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    static boolean twoPane;
    Bundle b = new Bundle();
    PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        if(findViewById(R.id.fragment_detail)!=null){
            twoPane = true;

        }
        else{
            twoPane = false;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public boolean isTwoPane(){
        return twoPane;
    }


}
