package com.aleksandr.aleksandrov.project.test.android.movies.service;

import android.content.Context;

import com.aleksandr.aleksandrov.project.test.android.movies.models.Movie;
import com.aleksandr.aleksandrov.project.test.android.movies.utils.SPUtils;
import com.aleksandr.aleksandrov.project.test.android.movies.utils.Utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by aleksandr on 9/29/17.
 */

public class DBHelper {

    public static final String TAG = "DBHelper";

    public static void write(List<Movie> movieList) {
        Realm realm = Realm.getDefaultInstance();

        movieList = Utils.createRealmList(movieList);

        realm.beginTransaction();
        realm.deleteAll();
        realm.copyToRealm(movieList);
        realm.commitTransaction();
    }

    public static List<Movie> getMovies() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Movie> query = realm.where(Movie.class);
        return query.findAll();
    }

    public static List<Movie> getFiltered(boolean isRating, String[] filter, Integer[] years) {



        Realm realm = Realm.getDefaultInstance();
        RealmQuery query = realm.where(Movie.class);
        if (filter.length != 0)
            query = query.in("genres.genreName", filter);

        if (years.length != 0)
            query = query.in("releaseYear", years);
        RealmResults<Movie> first;

        if (!isRating)
             first = query.findAll();
        else
            first = query.findAllSorted("rating", Sort.DESCENDING);

        return first;
    }


    public static void setGenres(Context context, List<Movie> movieList) {
        Set<String> genres = new HashSet<>();

        for (Movie movie : movieList) {
            genres.addAll(movie.getTempGenres());
        }

        SPUtils.setGenres(context, genres);
    }

    public static void setYears(Context context, List<Movie> movieList) {
        Set<String> years = new HashSet<>();

        for (Movie movie : movieList) {
            years.add(String.valueOf(movie.getReleaseYear()));
        }

        SPUtils.setYears(context, years);
    }
}
