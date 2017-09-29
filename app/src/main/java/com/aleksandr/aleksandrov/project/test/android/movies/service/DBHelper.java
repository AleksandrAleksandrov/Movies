package com.aleksandr.aleksandrov.project.test.android.movies.service;

import android.content.Context;
import android.util.Log;

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

    public static List<Movie> getFiltered(Set filter, Set years) {
        if (filter.isEmpty() && years.isEmpty())
            return getMovies();

        String[] filt = (String[]) filter.toArray(new String[filter.size()]);
        String[] year = (String[]) years.toArray(new String[years.size()]);
        Integer[] yearsInt = new Integer[year.length];
        for (int i = 0; i < year.length; i++) {
            yearsInt[i] = Integer.parseInt(year[i]);
        }

        if (filter.isEmpty()) {
            return getByYearOnly(yearsInt);
        } else if (years.isEmpty()) {
            return getByGenerOnly(filt);
        }

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Movie> first = realm.where(Movie.class)
                .in("genres.genreName", filt)
                .in("releaseYear", yearsInt)
                .findAll();
        first.sort("rating", Sort.DESCENDING);
        Log.d(TAG, "results count " + first.size());
        return first;
    }

    private static List<Movie> getByYearOnly(Integer[] year) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Movie> filtered = realm.where(Movie.class)
                .in("releaseYear", year)
                .findAll();
        return filtered;
    }

    private static List<Movie> getByGenerOnly(String[] gener) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Movie> filtered = realm.where(Movie.class)
                .in("genres.genreName", gener)
                .findAll();
        return filtered;
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
