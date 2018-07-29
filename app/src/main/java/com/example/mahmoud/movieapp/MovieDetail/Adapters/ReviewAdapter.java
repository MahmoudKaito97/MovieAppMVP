package com.example.mahmoud.movieapp.MovieDetail.Adapters;


import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.mahmoud.movieapp.MovieDetail.Presenter.DetailPresenter;
import com.example.mahmoud.movieapp.MovieGallery.Model.Movie;

import com.example.mahmoud.movieapp.R;
import com.ms.square.android.expandabletextview.ExpandableTextView;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {


    private Context context;
    private DetailPresenter mPresenter;
    public ReviewAdapter(Context c,DetailPresenter presenter){
        context=c;
        mPresenter = presenter;
    }


    @Override
    public ReviewAdapter.ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.row_review_list_view,parent,false);
        ReviewAdapter.ReviewHolder RHolder = new ReviewAdapter.ReviewHolder(v);
        return RHolder;
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ReviewHolder holder, int position) {
        holder.tv1.setText(mPresenter.getMovie().getReviews().get(position).getName());
        holder.tv2.setText(mPresenter.getMovie().getReviews().get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mPresenter.getMovie().getReviews().size();
    }


    public void notifyDataSetChanged(Movie mo) {
        super.notifyDataSetChanged();
        mPresenter.setMovie(mo);
    }
    class ReviewHolder extends RecyclerView.ViewHolder {
        TextView tv1;
        ExpandableTextView tv2;
        public ReviewHolder(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.reviewer_name);

            tv2 = (ExpandableTextView) itemView.findViewById(R.id.expand_text_view);
        }


    }
}
