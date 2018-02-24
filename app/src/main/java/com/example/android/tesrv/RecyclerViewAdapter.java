package com.example.android.tesrv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by adesm on 10/02/18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    public Context context;
    public List<MovieItem.Results> movieItems;

    public RecyclerViewAdapter(Context context, List<MovieItem.Results> movieItems) {
        this.context = context;
        this.movieItems = movieItems;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,null);
        int width = parent.getMeasuredWidth()/2;
        int height = 500;
        layoutView.setLayoutParams(new RecyclerView.LayoutParams(width,height));
        RecyclerViewHolder holder = new RecyclerViewHolder(layoutView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Picasso.with(context).load(
                "https://image.tmdb.org/t/p/w185"+ movieItems.get(position).posterPath)
                .placeholder(R.drawable.placeholder)
                .into(holder.img);
        holder.title.setText(movieItems.get(position).title);
        holder.date.setText(movieItems.get(position).releaseDate);
        holder.rate.setText(movieItems.get(position).voteAvg+"/10");
        holder.overview = movieItems.get(position).overview;
        holder.thn = movieItems.get(position).releaseDate;
        holder.backdrop = "https://image.tmdb.org/t/p/w780" + movieItems.get(position).backdropPath;
        holder.vote = movieItems.get(position).voteAvg;
        holder.id_film  = movieItems.get(position).id;
    }

    @Override
    public int getItemCount() {
        return movieItems.size();
    }
}
