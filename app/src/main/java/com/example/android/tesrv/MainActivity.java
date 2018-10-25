package com.example.android.tesrv;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static AlertDialog.Builder alert;
    public static final String URL = "https://api.themoviedb.org/3/movie/";
    public static SwipeRefreshLayout refresh;
    private InterstitialAd mInterstitialAd;
    RecyclerView recyclerViewPop,recyclerViewTop,recyclerViewComing,recyclerViewNow;
    Builder builder,builder2,builder3,builder4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-2917426024691439~3638506383");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        recyclerViewPop = (RecyclerView)findViewById(R.id.recycler_view_pop);
        recyclerViewTop = (RecyclerView)findViewById(R.id.recycler_view_top);
        recyclerViewComing = (RecyclerView)findViewById(R.id.recycler_view_coming);
        recyclerViewNow = (RecyclerView)findViewById(R.id.recycler_view_now);
        refresh = (SwipeRefreshLayout)findViewById(R.id.refresh_view);
        alert = new AlertDialog.Builder(MainActivity.this);

        builder = new Builder(MainActivity.this,URL+"popular");
        builder2 = new Builder(MainActivity.this,URL+"top_rated");
        builder3 = new Builder(MainActivity.this,URL+"upcoming");
        builder4 = new Builder(MainActivity.this,URL+"now_playing");

        refresh.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener)MainActivity.this);
        refresh.post(new Runnable() {
            @Override
            public void run() {
                initMovies();
            }
        });

        refresh.setRefreshing(true);
        initMovies();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onRefresh() {
        refresh.setRefreshing(true);
        initMovies();
    }

    public void initMovies(){
        builder.buildRecycler(recyclerViewPop);
        builder2.buildRecycler(recyclerViewTop);
        builder3.buildRecycler(recyclerViewComing);
        builder4.buildRecycler(recyclerViewNow);
        refresh.setRefreshing(false);

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle(R.string.str_alert_about_title);
                alert.setMessage(R.string.str_alert_about);
                alert.setCancelable(true);
                alert.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
