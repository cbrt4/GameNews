package com.alex.gamenews.application;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.alex.gamenews.interfaces.Presenter;
import com.alex.gamenews.presenters.NewsPresenter;
import com.alex.gamenews.responses.NewsResponse;

public class GameNewsApp extends Application {

    private static Presenter<NewsResponse> newsPresenter;

    public static Presenter<NewsResponse> getNewsPresenter() {
        if (newsPresenter == null) newsPresenter = NewsPresenter.getInstance();
        return newsPresenter;
    }

    public static void cancelNewsLoad() {
        newsPresenter.cancelLoading();
    }

    public static void cancelAllLoads() {
        cancelNewsLoad();
        // cancel other loads
    }

    public static void resetNewsPresenter() {
        newsPresenter = null;
    }

    public static void resetAllPresenters() {
        resetNewsPresenter();
        // reset other presenters
    }

    public static boolean isOnline(Context context) {
        final ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}