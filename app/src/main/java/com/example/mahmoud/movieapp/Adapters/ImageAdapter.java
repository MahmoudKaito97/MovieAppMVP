package com.example.mahmoud.movieapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.mahmoud.movieapp.DBHandler;
import com.example.mahmoud.movieapp.DetailActivity;
import com.example.mahmoud.movieapp.DetailFragment;
import com.example.mahmoud.movieapp.MainActivity;
import com.example.mahmoud.movieapp.MainFragment;
import com.example.mahmoud.movieapp.Models.Movie;
import com.example.mahmoud.movieapp.MoviesPresenter;
import com.example.mahmoud.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {

    private Context context ;
    private MoviesPresenter mPresenter ;
    private boolean twoPane ;

    public ImageAdapter(Context context, MoviesPresenter presenter, boolean tab){
        this.context = context;
        mPresenter=presenter ;
        twoPane = tab;
    }


    @Override
    public ImageAdapter.ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =LayoutInflater.from(context).inflate(R.layout.grid_movie,parent,false);
        ImageHolder holder = new ImageHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ImageHolder holder, int position) {

        holder.imageView.setImageBitmap(mPresenter.getMovies().get(position).getMovieCover());

    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }
    @Override
    public int getItemCount() {
        return mPresenter.getMovies().size();
    }


    public void notifyDataSetChanged(ArrayList<Movie> list) {
       mPresenter.setMovies(list);
        super.notifyDataSetChanged();

    }
public void setTwoPane(boolean tab){
    twoPane = tab;
}

    public class ImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        public ImageHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = (ImageView) itemView.findViewById(R.id.grid_movie_image_view);
        }

        @Override
        public void onClick(View v) {
            if(!twoPane){
                Intent intent = new Intent(context,DetailActivity.class);

                intent.putExtra("MovieObject",mPresenter.getMovies().get(getLayoutPosition()));

                context.startActivity(intent);
            }
            else{

                DetailFragment.movie = mPresenter.getMovies().get(getLayoutPosition());

                AppCompatActivity app = (AppCompatActivity) context;


                app.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_detail,new DetailFragment()).commit();

            }
        }
    }

 }

