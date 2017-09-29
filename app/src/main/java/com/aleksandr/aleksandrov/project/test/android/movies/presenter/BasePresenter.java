package com.aleksandr.aleksandrov.project.test.android.movies.presenter;

import java.util.Set;

/**
 * Created by aleksandr on 9/29/17.
 */

public interface BasePresenter<T> {

    void load();

    void update();

    void filter(Set<String> filter, Set<String> filterYear);

}

