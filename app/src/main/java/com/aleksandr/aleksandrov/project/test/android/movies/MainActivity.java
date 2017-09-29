package com.aleksandr.aleksandrov.project.test.android.movies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showFragment(FragmentList.TAG);
    }

    private void showFragment(String fragmentTAG) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTAG);

        if (fragment == null) {
            fragment = new FragmentList();
            fragmentManager.beginTransaction().add(R.id.fragment_container, fragment, FragmentList.TAG).commit();
        }
    }
}
