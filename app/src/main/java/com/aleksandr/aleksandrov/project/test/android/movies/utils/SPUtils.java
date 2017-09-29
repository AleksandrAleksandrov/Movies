package com.aleksandr.aleksandrov.project.test.android.movies.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by aleksandr on 9/29/17.
 */

public class SPUtils {

    public static final String GENRES_PREF = "genres_pref";
    public static final String GENRES = "genres";
    public static final String YEARS = "years";

    public static void setGenres(Context context, Set<String> genres) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GENRES_PREF, Context.MODE_PRIVATE);
        sharedPreferences.edit().putStringSet(GENRES, genres).commit();
    }

    public static Set<String> getGenres(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GENRES_PREF, Context.MODE_PRIVATE);
        return sharedPreferences.getStringSet(GENRES, null);
    }

    public static void setYears(Context context, Set<String> years) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GENRES_PREF, Context.MODE_PRIVATE);
        sharedPreferences.edit().putStringSet(YEARS, years).commit();
    }

    public static Set<String> getYears(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GENRES_PREF, Context.MODE_PRIVATE);
        return sharedPreferences.getStringSet(YEARS, null);
    }
}