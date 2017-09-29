package com.aleksandr.aleksandrov.project.test.android.movies.presenter;

import android.content.Context;
import android.content.Intent;

import com.aleksandr.aleksandrov.project.test.android.movies.ApiEvent;
import com.aleksandr.aleksandrov.project.test.android.movies.service.DBHelper;
import com.aleksandr.aleksandrov.project.test.android.movies.service.DataLoaderService;
import com.aleksandr.aleksandrov.project.test.android.movies.view.MoviesView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static com.aleksandr.aleksandrov.project.test.android.movies.service.ServicesConstants.GET_MOVIES;
import static com.aleksandr.aleksandrov.project.test.android.movies.service.ServicesConstants.IS_INTERNET_ACCESS;

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
    public void filter(boolean isRating, String[] filter, Integer[] filterYear) {
        mAlbumsView.updateList(DBHelper.getFiltered(isRating, filter, filterYear));
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
                mAlbumsView.loaded();
                break;
            case IS_INTERNET_ACCESS:
                mAlbumsView.loadFailed();
                break;
        }
    }
}
