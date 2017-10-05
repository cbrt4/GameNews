package com.alex.gamenews.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.alex.gamenews.R;
import com.alex.gamenews.adapters.MainTabPagerAdapter;
import com.alex.gamenews.application.GameNewsApp;

public class NewsActivity extends AppCompatActivity {

    private static long BACK_PRESSED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_news);
        toolbar.setTitle(R.string.news_activity_title);

        PagerAdapter newsPagerAdapter = new MainTabPagerAdapter(this, getSupportFragmentManager());

        ViewPager newsPager = (ViewPager) findViewById(R.id.news_container);
        newsPager.setAdapter(newsPagerAdapter);

        TabLayout newsTabLayout = (TabLayout) findViewById(R.id.news);
        newsTabLayout.setupWithViewPager(newsPager);
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
}