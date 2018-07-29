package com.example.mahmoud.movieapp.MovieDetail.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mahmoud.movieapp.MovieDetail.Presenter.DetailPresenter;
import com.example.mahmoud.movieapp.MovieGallery.Model.Movie;
import com.example.mahmoud.movieapp.R;

/**
 * Created by Mahmoud on 10/22/2016.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerHolder> {

    private DetailPresenter mPresenter;
    private Context context;
    public TrailerAdapter(Context c,DetailPresenter presenter){
        context=c;
        mPresenter=presenter;
    }


    @Override
    public TrailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.row_trailer_list_item,parent,false);
        TrailerHolder THolder = new TrailerHolder(v);
        return THolder;
    }

    @Override
    public void onBindViewHolder(TrailerHolder holder, int position) {
        holder.tv.setText(mPresenter.getMovie().getTrailers().get(position).getName());

    }

    @Override
    public int getItemCount() {
        return mPresenter.getMovie().getTrailers().size();
    }


    public void notifyDataSetChanged(Movie mo) {
        super.notifyDataSetChanged();
        mPresenter.setMovie(mo);
    }
    class TrailerHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv;

        public TrailerHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv = (TextView) itemView.findViewById(R.id.trailer_name_text_view);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(mPresenter.getMovie().getTrailers().get(getLayoutPosition()).getLink()));
           context.startActivity(intent);
        }
    }
}
