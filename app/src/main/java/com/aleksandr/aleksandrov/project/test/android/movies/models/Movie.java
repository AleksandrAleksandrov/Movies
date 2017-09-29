package com.aleksandr.aleksandrov.project.test.android.movies.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by aleksandr on 9/29/17.
 */

public class Movie extends RealmObject {

    @SerializedName("title")
    String title;

    @SerializedName("image")
    String imageUrl;

    @SerializedName("rating")
    float rating;

    @SerializedName("releaseYear")
    int releaseYear;

    @SerializedName("genre")
    @Ignore
    List<String> tempGenres;

    RealmList<Genre> genres;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public RealmList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(RealmList<Genre> genre) {
        this.genres = genre;
    }

    public List<String> getTempGenres() {
        return tempGenres;
    }

    public void setTempGenres(List<String> tempGenres) {
        this.tempGenres = tempGenres;
    }
}