package com.example.mahmoud.movieapp.MovieGallery.View;

import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mahmoud.movieapp.MovieGallery.Adapter.ImageAdapter;
import com.example.mahmoud.movieapp.MovieGallery.Model.DBHandler;
import com.example.mahmoud.movieapp.MovieGallery.Model.Movie;
import com.example.mahmoud.movieapp.MovieGallery.Presenter.MoviesPresenter;
import com.example.mahmoud.movieapp.R;

import java.util.ArrayList;


public class MainFragment extends Fragment implements MoviesView {

    final String TAG = "com.example.mahmoud:";
    ProgressBar Pb ;
    MoviesPresenter mPresenter;

    RecyclerView gridView;
    TextView empty;
    ImageAdapter imageAdapter;
    String sortType ="";
    static  boolean twoPane;
    int top = 0;
    int pop=0;
    int fav=0;
    public static Movie temp = new Movie();

public static DBHandler db ;

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

        mPresenter = new MoviesPresenter(new ArrayList<Movie>(),getContext());




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

        //mPresenter.onCreate(this);
         return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();



        }


    @Override
    public void onResume() {

        super.onResume();


    mPresenter.onCreate(this);
        imageAdapter.setTwoPane(MainActivity.twoPane);


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }


    //String s="";




    @Override
    public void swipe() {

         if ((sortType.equals("favorite"))) {
             if(mPresenter.getMovies().size()>0)
             mPresenter.getMovies().clear();

             mPresenter.getMoviesFromDb();
            if(mPresenter.getMovies().isEmpty()){
              //  showProgressBar();
            }else{
               // hideProgressBar();

            }

            // sortType="";
             //imageAdapter.notifyDataSetChanged(mPresenter.getMovies());

            //Pb.setVisibility(View.GONE);
        }
       else {

            if(mPresenter.getMovies().size()>0)
             mPresenter.getMovies().clear();

             mPresenter.getMoviesFromTMDB(sortType,imageAdapter);


         }
        //
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
