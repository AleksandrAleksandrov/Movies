package com.aleksandr.aleksandrov.project.test.android.movies.presenter;

import com.aleksandr.aleksandrov.project.test.android.movies.holder.MovieHolder;
import com.aleksandr.aleksandrov.project.test.android.movies.models.Movie;

/**
 * Created by aleksandr on 9/29/17.
 */

public class MovieItemPresenter implements MovieItemMainPresenter {

    public MovieItemPresenter() {

    }

    public void onBind(MovieHolder holder, Movie movie) {
        holder.setTitle(movie.getTitle());
        holder.setImage(movie.getImageUrl());
        holder.setRating(movie.getRating());
        holder.setReleaseYear(movie.getReleaseYear());
        holder.setGenres(movie.getGenres());
    }
}
