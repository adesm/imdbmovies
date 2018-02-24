package com.example.android.tesrv;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by adesm on 16/02/18.
 */

public class Builder {

    private final String API_KEY = "7aeff35b7698274d32975dd69c5103d8";
    public static MovieItem movieItem;
    private Context context;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;
    private String fullurl;
    private Activity activity;
    private boolean isSUccess = false;

    public Builder(Context context,String fullurl) {
        this.context = context;
        this.fullurl = fullurl;
        this.activity = (Activity)context;
    }

    public void buildRecycler(RecyclerView recyclerView){

        linearLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        requestJsonObject(recyclerView);
    }

    private void requestJsonObject(RecyclerView recyclerView) {
        RequestParams params = new RequestParams();
        params.put("api_key",API_KEY);
        myParsingGson(params,fullurl,recyclerView);
    }

    private void myParsingGson(RequestParams params, String fullurl, final RecyclerView recyclerView) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        asyncHttpClient.get(fullurl,params,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {
                isSUccess= true;
                try {
                    GsonBuilder builder = new GsonBuilder();
                    Gson myGson = builder.create();
                    movieItem = myGson.fromJson(content,MovieItem.class);
                    recyclerViewAdapter = new RecyclerViewAdapter(context,movieItem.results);
                    recyclerView.setAdapter(recyclerViewAdapter);
                }catch (Exception e){
                    e.printStackTrace();
//                    MainActivity.alert.setTitle("Error");
//                    MainActivity.alert.setIcon(android.R.drawable.ic_dialog_alert);
//                    MainActivity.alert.setMessage("Request time out");
//                    MainActivity.alert.show();
                }
            }

            @Override
            public void onFailure(int statuscode, Throwable error, String content) {
//                MainActivity.refresh.setRefreshing(false);
//                if(statuscode==404){
////                    MainActivity.alert.setTitle("Error");
////                    MainActivity.alert.setIcon(android.R.drawable.ic_dialog_alert);
////                    MainActivity.alert.setMessage("Not found");
////                    MainActivity.alert.show();
//                }else if(statuscode==500){
////                    MainActivity.alert.setTitle("Error");
////                    MainActivity.alert.setIcon(android.R.drawable.ic_dialog_alert);
////                    MainActivity.alert.setMessage("Internal server error");
////                    MainActivity.alert.show();
//                }else{
////                    MainActivity.alert.setTitle("Error");
////                    MainActivity.alert.setIcon(android.R.drawable.ic_dialog_alert);
////                    MainActivity.alert.setMessage("Please check your internet connection");
////                    MainActivity.alert.show();
//                }
            }
        });
    }
}
