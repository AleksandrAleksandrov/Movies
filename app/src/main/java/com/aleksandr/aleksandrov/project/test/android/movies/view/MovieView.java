package com.aleksandr.aleksandrov.project.test.android.movies.view;

import com.aleksandr.aleksandrov.project.test.android.movies.models.Genre;
import com.aleksandr.aleksandrov.project.test.android.movies.models.Movie;

import java.util.List;

/**
 * Created by aleksandr on 9/29/17.
 */

public interface MovieView extends BaseView {

    void update(Movie album);

    void setTitle(String title);

    void setImage(String url);

    void setRating(float rating);

    void setReleaseYear(int releaseYear);

    void setGenres(List<Genre> genres);
}
