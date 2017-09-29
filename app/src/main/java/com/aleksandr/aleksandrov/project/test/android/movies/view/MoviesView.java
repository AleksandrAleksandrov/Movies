package com.aleksandr.aleksandrov.project.test.android.movies.view;

import com.aleksandr.aleksandrov.project.test.android.movies.models.Movie;

import java.util.List;

/**
 * Created by aleksandr on 9/29/17.
 */

public interface MoviesView extends BaseView {

    void updateList(List<Movie> movieList);
}