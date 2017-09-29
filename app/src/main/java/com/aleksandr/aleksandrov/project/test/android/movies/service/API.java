package com.aleksandr.aleksandrov.project.test.android.movies.service;

import com.aleksandr.aleksandrov.project.test.android.movies.models.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by aleksandr on 9/29/17.
 */

public interface API {

    @GET("/json/movies.json")
    Call<List<Movie>> getMovies();
}
