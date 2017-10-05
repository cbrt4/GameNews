package com.alex.gamenews.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.alex.gamenews.R;
import com.alex.gamenews.fragments.FavouritesFragment;
import com.alex.gamenews.fragments.NewsFragment;
import com.alex.gamenews.fragments.VideoFragment;

public class MainTabPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public MainTabPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NewsFragment();
            case 1:
                return new VideoFragment();
            case 2:
                return new FavouritesFragment();
            default:
                return null;
        }
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getResources().getString(R.string.fragment_stories_title);
            case 1:
                return context.getResources().getString(R.string.fragment_video_title);
            case 2:
                return context.getResources().getString(R.string.fragment_favourites_title);
            default:
                return null;
        }
    }
}
