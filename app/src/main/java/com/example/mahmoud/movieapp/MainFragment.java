package com.example.mahmoud.movieapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahmoud.movieapp.Adapters.ImageAdapter;
import com.example.mahmoud.movieapp.Models.Movie;
import com.example.mahmoud.movieapp.Models.Review;
import com.example.mahmoud.movieapp.Models.Trailer;
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
import java.util.ArrayList;

import javax.crypto.interfaces.PBEKey;


public class MainFragment extends Fragment implements MoviesView {

    final String TAG = "com.example.mahmoud:";
    ProgressBar Pb ;
    MoviesPresenter mPresenter;

    RecyclerView gridView;
    TextView empty;
    ImageAdapter imageAdapter;
    String sortType ="popular";
    static  boolean twoPane;
    int top = 0;
    int pop=0;
    int fav=0;
    public static Movie temp = new Movie();

public static DBHandler db ;
    private PagerAdapter adapter;

    public MainFragment() {
//        updateList();
        setHasOptionsMenu(true);

    }

    public MainFragment(String s) {
        sortType = s;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);




        mPresenter = new MoviesPresenter();
        mPresenter.onCreate(this);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =inflater.inflate(R.layout.fragment_main, container, false);
        Pb = (ProgressBar) rootView.findViewById(R.id.progressBar);
        Pb.setVisibility(View.GONE);
        empty = (TextView) rootView.findViewById(R.id.empty);
        imageAdapter = new ImageAdapter(getContext(),mPresenter,MainActivity.twoPane);
        gridView = (RecyclerView) rootView.findViewById(R.id.grid_view);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Popular"));
        tabLayout.addTab(tabLayout.newTab().setText("Top Rated"));
        tabLayout.addTab(tabLayout.newTab().setText("Favorite"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        adapter = new PagerAdapter
                (getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                sortType = tab.getText().toString();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        GridLayoutManager GM;
        if(getActivity().getResources().getConfiguration().orientation ==Configuration.ORIENTATION_LANDSCAPE ) {
             GM = new GridLayoutManager(getContext(), 3);
        }
        else {
            GM = new GridLayoutManager(getContext(),2);
        }
        gridView.setLayoutManager(GM);

        gridView.setAdapter(imageAdapter);

        db=new DBHandler(getContext());


         return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();



        }


    @Override
    public void onResume() {

        super.onResume();



        imageAdapter.setTwoPane(MainActivity.twoPane);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }


    //String s="";




    @Override
    public void showImagesOnGrid() {

         if ((sortType.equals("favorite"))) {

                mPresenter.getMovies().clear();

            mPresenter.getMoviesFromDb();

            if(mPresenter.getMovies().isEmpty()){
              //  showProgressBar();
            }else{
               // hideProgressBar();

            }

            // sortType="";

            imageAdapter.notifyDataSetChanged(mPresenter.getMovies());
            //Pb.setVisibility(View.GONE);
        }
       else {
            mPresenter.getMovies().clear();

            mPresenter.getMoviesFromFMC(sortType);
            for(int i = 0;i<mPresenter.getMovies().size();i++)
            {

                try {
                    mPresenter.getMovies().get(i).setMovieCover( Picasso.with(getContext())
                            .load("http://image.tmdb.org/t/p/w342/"+(mPresenter.getMovies().get(i)).getPath()).get());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }



    @Override
    public void showProgressBar() {
        gridView.setVisibility(View.VISIBLE);
        empty.setVisibility(View.GONE);
       // Pb.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        gridView.setVisibility(View.GONE);
        //Pb.setVisibility(View.GONE);
       // empty.setVisibility(View.VISIBLE);
    }

}
