package com.depeloper.android.imdb_movies;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by adesm on 10/02/18.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView title,date,rate;
    ImageView img;
    String backdrop,thn,vote,id_film,overview;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        img = (ImageView)itemView.findViewById(R.id.image_view);
        title = (TextView)itemView.findViewById(R.id.txt_title);
        date = (TextView)itemView.findViewById(R.id.date);
        rate = (TextView)itemView.findViewById(R.id.rate);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), DetailMovie.class);
        intent.putExtra("id",id_film);
        intent.putExtra("judul",title.getText().toString());
        intent.putExtra("desc",overview);
        intent.putExtra("tahun", thn);
        intent.putExtra("backdrop",backdrop);
        intent.putExtra("vote",vote);

        view.getContext().startActivity(intent);
    }
}
