package com.alex.gamenews.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.alex.gamenews.R;
import com.alex.gamenews.adapters.MainTabPagerAdapter;
import com.alex.gamenews.application.GameNewsApp;
import com.alex.gamenews.fragments.FavouritesFragment;
import com.alex.gamenews.fragments.NewsFragment;
import com.alex.gamenews.fragments.VideoFragment;

public class NewsActivity extends AppCompatActivity {

    private static long BACK_PRESSED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_news);
        toolbar.setTitle(R.string.news_activity_title);

        //PagerAdapter newsPagerAdapter = new MainTabPagerAdapter(this, getSupportFragmentManager());

        //ViewPager newsPager = findViewById(R.id.news_container);
        //newsPager.setAdapter(newsPagerAdapter);

        TabLayout newsTabLayout = findViewById(R.id.news);
        newsTabLayout.addTab(newsTabLayout.newTab().setText(getString(R.string.fragment_stories_title)));
        newsTabLayout.addTab(newsTabLayout.newTab().setText(getString(R.string.fragment_video_title)));
        newsTabLayout.addTab(newsTabLayout.newTab().setText(getString(R.string.fragment_favourites_title)));

        newsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                displaySelectedScreen(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        displaySelectedScreen(0);
        //newsTabLayout.setupWithViewPager(newsPager);
    }

    @Override
    public void onBackPressed() {
        if (BACK_PRESSED + 2000 > System.currentTimeMillis()) {
            GameNewsApp.cancelAllLoads();
            GameNewsApp.resetAllPresenters();
            finish();
        }
        else
            Toast.makeText(getBaseContext(), R.string.on_back_pressed_toast, Toast.LENGTH_SHORT).show();
        BACK_PRESSED = System.currentTimeMillis();

    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case 0:
                fragment = new NewsFragment();
                break;
            case 1:
                fragment = new VideoFragment();
                break;
            case 2:
                fragment = new FavouritesFragment();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.news_container, fragment);
            transaction.commit();
        }
    }

}