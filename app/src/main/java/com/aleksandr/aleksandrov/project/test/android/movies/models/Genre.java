package com.aleksandr.aleksandrov.project.test.android.movies.models;

import io.realm.RealmObject;

/**
 * Created by aleksandr on 9/29/17.
 */

public class Genre extends RealmObject {

    String genreName;

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
