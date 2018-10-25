package com.example.android.tesrv;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by adesm on 10/02/18.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final InterstitialAd mInterstitialAd;
    TextView title,date,rate;
    ImageView img;
    String backdrop,thn,vote,id_film,overview;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        MobileAds.initialize(itemView.getContext(), "ca-app-pub-2917426024691439~3638506383");

        mInterstitialAd = new InterstitialAd(itemView.getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
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

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }

        view.getContext().startActivity(intent);
    }
}
