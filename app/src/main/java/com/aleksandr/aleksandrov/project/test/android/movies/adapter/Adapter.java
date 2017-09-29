package com.aleksandr.aleksandrov.project.test.android.movies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.aleksandr.aleksandrov.project.test.android.movies.R;
import com.aleksandr.aleksandrov.project.test.android.movies.holder.MovieHolder;
import com.aleksandr.aleksandrov.project.test.android.movies.models.Movie;
import com.aleksandr.aleksandrov.project.test.android.movies.presenter.MovieItemPresenter;

import java.util.List;

/**
 * Created by aleksandr on 9/29/17.
 */

public class Adapter extends RecyclerView.Adapter<MovieHolder> {

    private List<Movie> mMovieList;

    private Context mContext;

    MovieItemPresenter albumItemPresenter;

    public Adapter(Context context, List<Movie> movieList) {
        mContext = context;
        mMovieList = movieList;
        albumItemPresenter = new MovieItemPresenter();
    }

    public void setMovieList(List<Movie> movieList) {
        mMovieList = movieList;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        android.view.View view = inflater.inflate(R.layout.item_movie, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        albumItemPresenter.onBind(holder, mMovieList.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }
}
