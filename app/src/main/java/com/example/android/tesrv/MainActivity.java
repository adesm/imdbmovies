package com.example.android.tesrv;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static int loadCounter;
    public static AlertDialog.Builder alert;
    public static ProgressDialog dialog;
    public static final String URL = "https://api.themoviedb.org/3/movie/";
    public static SwipeRefreshLayout refresh;
    RecyclerView recyclerViewPop,recyclerViewTop,recyclerViewComing,recyclerViewNow;
    Builder builder,builder2,builder3,builder4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadCounter = 0;

        recyclerViewPop = (RecyclerView)findViewById(R.id.recycler_view_pop);
        recyclerViewTop = (RecyclerView)findViewById(R.id.recycler_view_top);
        recyclerViewComing = (RecyclerView)findViewById(R.id.recycler_view_coming);
        recyclerViewNow = (RecyclerView)findViewById(R.id.recycler_view_now);
        refresh = (SwipeRefreshLayout)findViewById(R.id.refresh_view);
        alert = new AlertDialog.Builder(MainActivity.this);
        dialog = new ProgressDialog(MainActivity.this);

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

//        dialog.setMessage("Loading...");
//        dialog.setCancelable(true);
//        dialog.show();
        refresh.setRefreshing(true);
        initMovies();
//        if(hasFetched)
//            dialog.hide();
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
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.popular:
//                requestJsonObject(0);
//                break;
//            case R.id.top:
//                requestJsonObject(1);
//                break;
//            case R.id.coming:
//                requestJsonObject(2);
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
