package com.aleksandr.aleksandrov.project.test.android.movies.presenter;

import android.content.Context;
import android.content.Intent;

import com.aleksandr.aleksandrov.project.test.android.movies.ApiEvent;
import com.aleksandr.aleksandrov.project.test.android.movies.models.Movie;
import com.aleksandr.aleksandrov.project.test.android.movies.service.DBHelper;
import com.aleksandr.aleksandrov.project.test.android.movies.service.DataLoaderService;
import com.aleksandr.aleksandrov.project.test.android.movies.view.MoviesView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Set;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static com.aleksandr.aleksandrov.project.test.android.movies.service.ServicesConstants.GET_MOVIES;

/**
 * Created by aleksandr on 9/29/17.
 */

public class MoviePresenter implements MovieListPresenter {

    MoviesView mAlbumsView;

    private Context mContext;

    public MoviePresenter(Context context, MoviesView albumsView) {
        mContext = context;
        mAlbumsView = albumsView;
    }

    @Override
    public void load() {
        Intent intent = new Intent(mContext, DataLoaderService.class);
        intent.setAction(GET_MOVIES);
        mContext.startService(intent);
    }

    @Override
    public void update() {

    }

    @Override
    public void filter(Set filter, Set filterYear) {
        mAlbumsView.updateList(DBHelper.getFiltered(filter, filterYear));
    }

    @Override
    public void register() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void unregister() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    void onApiEventReceived(ApiEvent event) {
        switch (event.getEvent()) {
            case GET_MOVIES:
                if (!event.isSuccess()) {
                    mAlbumsView.updateList(null);
                    return;
                }

                Realm realm = Realm.getDefaultInstance();
                RealmQuery<Movie> query = realm.where(Movie.class);
                RealmResults<Movie> result1 = query.findAll();
                mAlbumsView.updateList(result1);

        }
    }
}
