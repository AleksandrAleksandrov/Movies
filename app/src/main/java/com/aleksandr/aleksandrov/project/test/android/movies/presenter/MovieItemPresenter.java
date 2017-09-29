package com.aleksandr.aleksandrov.project.test.android.movies.presenter;

import com.aleksandr.aleksandrov.project.test.android.movies.holder.MovieHolder;
import com.aleksandr.aleksandrov.project.test.android.movies.models.Movie;
import com.aleksandr.aleksandrov.project.test.android.movies.view.MovieView;

/**
 * Created by aleksandr on 9/29/17.
 */

public class MovieItemPresenter implements MovieItemMainPresenter {

    MovieView mAlbumView;

    Movie mAlbum;

    public MovieItemPresenter() {
//        mAlbum = album;
//        mAlbumView = albumView;
//        mAlbumView.update(album);
    }

    public void onBind(MovieHolder holder, Movie movie) {
        holder.setTitle(movie.getTitle());
        holder.setImage(movie.getImageUrl());
        holder.setRating(movie.getRating());
        holder.setReleaseYear(movie.getReleaseYear());
        holder.setGenres(movie.getGenres());
    }

//    public void onBindRepositoryRowViewAtPosition(int position, RepositoryRowView rowView) {
//        Repository repo = repositories.get(position);
//        rowView.setStarCount(repo.getStarsCount());
//        rowView.setTitle(repo.getTitle());
//    }
//
//    public int getRepositoriesRowsCount() {
//        return repositories.size();
//    }


    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setImage(String url) {

    }
}
