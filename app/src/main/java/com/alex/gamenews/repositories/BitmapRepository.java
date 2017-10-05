package com.alex.gamenews.repositories;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.alex.gamenews.interfaces.Repository;

import java.net.HttpURLConnection;
import java.net.URL;

public class BitmapRepository implements Repository<Bitmap> {
    @Override
    public Bitmap getData(String url) {
        try {
            HttpURLConnection connection =
                    (HttpURLConnection) new URL(url).openConnection();
            return BitmapFactory.decodeStream(connection.getInputStream());
        } catch (Exception e) {
            return null;
        }
    }
}