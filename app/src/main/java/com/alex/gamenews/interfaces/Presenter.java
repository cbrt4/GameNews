package com.alex.gamenews.interfaces;

public interface Presenter<T> {

    void onStartLoading();

    void onLoadFinished(T result);

    void loadData(String url);

    void cancelLoading();

    void attachView(Viewer<T> viewer);

    void detachView();
}