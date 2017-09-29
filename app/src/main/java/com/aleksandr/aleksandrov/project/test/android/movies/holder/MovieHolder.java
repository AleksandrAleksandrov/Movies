package com.aleksandr.aleksandrov.project.test.android.movies.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.aleksandr.aleksandrov.project.test.android.movies.R;
import com.aleksandr.aleksandrov.project.test.android.movies.models.Genre;
import com.aleksandr.aleksandrov.project.test.android.movies.models.Movie;
import com.aleksandr.aleksandrov.project.test.android.movies.view.MovieView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by aleksandr on 9/29/17.
 */

public class MovieHolder extends RecyclerView.ViewHolder implements MovieView {

    private Context mContext;

    private TextView mTitle;
    private ImageView mImageView;
    private RatingBar mRatingBar;
    private TextView mReleaseYear;
    private TextView mGenres;


    public MovieHolder(android.view.View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        mTitle = itemView.findViewById(R.id.tvTitle);
        mImageView = itemView.findViewById(R.id.ivAlbumCover);
        mRatingBar = itemView.findViewById(R.id.rbAlbumRating);
        mReleaseYear = itemView.findViewById(R.id.tvReleaseYear);
        mGenres = itemView.findViewById(R.id.tvGenres);
    }

    @Override
    public void update(Movie movie) {

    }

    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public void setImage(String url) {
        Picasso.with(mContext).load(url).into(mImageView);
    }

    @Override
    public void setRating(float rating) {
        mRatingBar.setRating(rating);
        Log.d("Rating", String.valueOf(rating));
    }

    @Override
    public void setReleaseYear(int releaseYear) {
        mReleaseYear.setText(String.valueOf(releaseYear));
    }

    @Override
    public void setGenres(List<Genre> genres) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Genre genre : genres) {
            stringBuilder.append(genre.getGenreName() + " ");
        }
        mGenres.setText(String.valueOf(stringBuilder));
    }
}
