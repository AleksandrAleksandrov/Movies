package com.aleksandr.aleksandrov.project.test.android.movies.presenter;

/**
 * Created by aleksandr on 9/29/17.
 */

public interface BasePresenter<T> {

    void load();

    void update();

    void filter(boolean isRating, String[] filter, Integer[] filterYear);

}

