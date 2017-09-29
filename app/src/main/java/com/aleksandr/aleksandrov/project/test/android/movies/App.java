package com.aleksandr.aleksandrov.project.test.android.movies;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by aleksandr on 9/29/17.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
