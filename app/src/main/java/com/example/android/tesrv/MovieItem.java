package com.example.android.tesrv;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by adesm on 10/02/18.
 */

public class MovieItem {
    @SerializedName("results")
    List<Results> results;

    public class Results{

        @SerializedName("poster_path")
        public String posterPath;

        @SerializedName("original_title")
        public String title;

        @SerializedName("overview")
        public String overview;

        @SerializedName("release_date")
        public String releaseDate;

        @SerializedName("backdrop_path")
        public String backdropPath;

        @SerializedName("vote_average")
        public String voteAvg;

        @SerializedName("id")
        public String id;

    }
}
