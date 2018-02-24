package com.example.android.tesrv;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

/**
 * Created by adesm on 10/02/18.
 */

public class DetailMovie extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private TextView judul, deskripsi, rate, tahun;
    private VideoView videoView;
    private ImageView img;
    private String id,videoPath = null;
    Uri uri;
    public static AlertDialog.Builder alert;
    public static ProgressDialog dialog;
    private final String API_KEY = "7aeff35b7698274d32975dd69c5103d8";
    private RecyclerView recyclerViewSim,recyclerViewRec;
    private Builder builder,builder2;
    private RequestParams params;
    private SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        judul = (TextView) findViewById(R.id.txt_title_detail);
        deskripsi = (TextView) findViewById(R.id.txt_desc);
        rate = (TextView) findViewById(R.id.txt_rate);
        tahun = (TextView) findViewById(R.id.txt_date);
        img = (ImageView) findViewById(R.id.movie_detail);
//        videoView = (VideoView) findViewById(R.id.video_view);

        recyclerViewSim = (RecyclerView) findViewById(R.id.recycler_view_similar);
        recyclerViewRec = (RecyclerView) findViewById(R.id.recycler_view_recomend);
        refresh = (SwipeRefreshLayout)findViewById(R.id.refresh_detail_view);
        alert = new AlertDialog.Builder(this);
        dialog = new ProgressDialog(this);

        refresh.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener)DetailMovie.this);
        refresh.post(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(true);
                initMovies();
            }
        });
        refresh.setRefreshing(true);

        String title = getIntent().getStringExtra("judul");
        id = getIntent().getStringExtra("id");
        String desc = getIntent().getStringExtra("desc");
        String thn = getIntent().getStringExtra("tahun");
        String backdrop = getIntent().getStringExtra("backdrop");
        String v = getIntent().getStringExtra("vote");

        Picasso.with(this).load(backdrop).placeholder(R.drawable.placeholder).into(img);
        judul.setText(title);
        deskripsi.setText(desc);
        rate.setText(v + "/10");
        tahun.setText(thn);

        builder = new Builder(this, MainActivity.URL + id + "/similar");
        builder2 = new Builder(this, MainActivity.URL + id + "/recommendations");
        params = new RequestParams();
        params.put("api_key",API_KEY);

//        dialog.setMessage("Loading...");
//        dialog.setCancelable(true);
//        dialog.show();
        initMovies();
    }

    private void initMovies() {
        String fullurl = "https://api.themoviedb.org/3/movie/"+id+"/videos";
        videoPath = parsingGson(params,fullurl);
        builder.buildRecycler(recyclerViewSim);
        builder2.buildRecycler(recyclerViewRec);
        refresh.setRefreshing(false);
    }

    public void playVideo(View view) {
        if(videoPath!=null) {
            uri = Uri.parse(videoPath);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
//            videoView.setVideoURI(uri);
//            videoView.start();
        }else
            Toast.makeText(this,"Video not found.",Toast.LENGTH_SHORT).show();

    }

    private String parsingGson(RequestParams params, String fullurl) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        asyncHttpClient.get(fullurl, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject results = new JSONObject(response);
                    JSONArray data = results.getJSONArray("results");
                    for (int i = 0; i<data.length(); i++){
                        JSONObject obj = data.getJSONObject(i);
                        String type = obj.getString("type");
                        String site = obj.getString("site");
                        String key = obj.getString("key");
                        if(type.equals("Trailer")){
                            videoPath = "http://"+site.toLowerCase()+".com/watch?v="+key;
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("error","Request time out");
                }
                super.onSuccess(response);
            }

            @Override
            public void onFailure(int statuscode, Throwable error, String content) {
                if (statuscode == 404) {
                    Log.e("error","Not found");
                } else if (statuscode == 500) {
                    Log.e("error","Internal server error");
                } else {
                    Log.e("error","Check your internet connection");
                }
            }
        });
        return videoPath;
    }

    @Override
    public void onRefresh() {
        refresh.setRefreshing(true);
        initMovies();
    }
}
