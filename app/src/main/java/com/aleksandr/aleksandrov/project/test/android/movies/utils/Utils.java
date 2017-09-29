package com.aleksandr.aleksandrov.project.test.android.movies.utils;

import com.aleksandr.aleksandrov.project.test.android.movies.models.Genre;
import com.aleksandr.aleksandrov.project.test.android.movies.models.Movie;

import java.util.Comparator;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by aleksandr on 9/29/17.
 */

public class Utils {

    public static List<Movie> createRealmList(List<Movie> albumList) {
        for (Movie album : albumList) {
            RealmList<Genre> list = new RealmList<>();
            for (String genreName : album.getTempGenres()) {
                Genre genre = new Genre();
                genre.setGenreName(genreName);
                list.add(genre);
            }
            album.setGenres(list);
        }
        return albumList;
    }

    public static Comparator<String> ALPHABETICAL_ORDER = new Comparator<String>() {
        public int compare(String str1, String str2) {
            int res = String.CASE_INSENSITIVE_ORDER.compare(str1, str2);
            if (res == 0) {
                res = str1.compareTo(str2);
            }
            return res;
        }
    };
}

