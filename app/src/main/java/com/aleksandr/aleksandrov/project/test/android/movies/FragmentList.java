package com.aleksandr.aleksandrov.project.test.android.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.aleksandr.aleksandrov.project.test.android.movies.adapter.Adapter;
import com.aleksandr.aleksandrov.project.test.android.movies.models.Movie;
import com.aleksandr.aleksandrov.project.test.android.movies.presenter.MoviePresenter;
import com.aleksandr.aleksandrov.project.test.android.movies.service.DBHelper;
import com.aleksandr.aleksandrov.project.test.android.movies.utils.SPUtils;
import com.aleksandr.aleksandrov.project.test.android.movies.utils.Utils;
import com.aleksandr.aleksandrov.project.test.android.movies.view.MoviesView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by aleksandr on 9/29/17.
 */

public class FragmentList extends Fragment implements MoviesView, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = "FragmentList";
    private MoviePresenter mMoviePresenter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Adapter mAdapter;

    private static final String FILTER_YEAR = "filterByYears";
    private Set<String> filterByYears = new HashSet<>();
    private static final String FILTER = "filter";
    private Set<String> filter = new HashSet<>();
    private int orientation;
    private static final String ORIENTATION = "orientation";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null) {
            mMoviePresenter = new MoviePresenter(getActivity(), this);
            if (orientation == this.getResources().getConfiguration().orientation) {
                mMoviePresenter.load();
            }
            orientation = this.getResources().getConfiguration().orientation;
        }

        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            orientation = savedInstanceState.getInt(ORIENTATION);
            filter = (HashSet) savedInstanceState.getSerializable(FILTER);
            filterByYears = (HashSet) savedInstanceState.getSerializable(FILTER_YEAR);
        } else {
            if (getActivity() != null) {
                mMoviePresenter.load();
            }
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        mMoviePresenter.register();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMoviePresenter.unregister();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(ORIENTATION, orientation);
        outState.putSerializable(FILTER, (Serializable) filter);
        outState.putSerializable(FILTER_YEAR, (Serializable) filterByYears);
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = view.findViewById(R.id.rvAlbums);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setList(DBHelper.getMovies());

        return view;
    }

    public void setList(List<Movie> movieList) {
        if (mAdapter == null) {
            mAdapter = new Adapter(getActivity(), movieList);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setMovieList(movieList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updateList(List<Movie> movieList) {
        setList(movieList);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        if (mMoviePresenter == null)
            return;

        if (filter.isEmpty()) {
            mMoviePresenter.load();
        } else {
            mMoviePresenter.filter(filter, filterByYears);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_filter:
                showPopupMenu(getActivity().findViewById(R.id.menu_filter));
                break;

            case R.id.menu_year:
                showYearPopup(getActivity().findViewById(R.id.menu_filter));
                break;
        }
        return true;
    }

    private void showPopupMenu(View v) {
        Set<String> genres = SPUtils.getGenres(getActivity());
        List<String> genresSorted = new ArrayList<>();
//        if (genresSorted.isEmpty())
//            return;
        genresSorted.addAll(genres);
        Collections.sort(genresSorted, Utils.ALPHABETICAL_ORDER);
        PopupMenu popupMenu = new PopupMenu(getActivity(), v);
        for (String name : genresSorted) {
            popupMenu.getMenu().add(name).setCheckable(true).setChecked(filter.contains(name));

        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                item.setChecked(!item.isChecked());
                if (item.isChecked()) {
                    filter.add(String.valueOf(item.getTitle()));
                } else {
                    filter.remove(String.valueOf(item.getTitle()));
                }
                mMoviePresenter.filter(filter, filterByYears);
                return false;
            }
        });

        popupMenu.show();
    }


    private void showYearPopup(View v) {
        Set<String> genres = SPUtils.getYears(getActivity());
        List<String> yeasSorted = new ArrayList<>();
        yeasSorted.addAll(genres);
        Collections.sort(yeasSorted, Utils.ALPHABETICAL_ORDER);
        Collections.sort(yeasSorted, Collections.reverseOrder());
        PopupMenu popupMenu = new PopupMenu(getActivity(), v);
        for (String year : yeasSorted) {
            popupMenu.getMenu().add(year).setCheckable(true).setChecked(filterByYears.contains(year));
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                item.setChecked(!item.isChecked());
                if (item.isChecked()) {
                    filterByYears.add(String.valueOf(item.getTitle()));
                } else {
                    filterByYears.remove(String.valueOf(item.getTitle()));
                }
                mMoviePresenter.filter(filter, filterByYears);
                return false;
            }
        });

        popupMenu.show();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.main, menu);
    }
}