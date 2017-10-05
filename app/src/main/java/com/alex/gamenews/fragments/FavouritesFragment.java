package com.alex.gamenews.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alex.gamenews.R;

public class FavouritesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favourites, container, false);
        TextView textView = rootView.findViewById(R.id.favourites_text);
        textView.setText(getString(R.string.fragment_favourites_title));
        return rootView;
    }
}