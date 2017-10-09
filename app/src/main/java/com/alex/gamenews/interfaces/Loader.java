package com.alex.gamenews.interfaces;

public interface Loader<T> {

    void onStart();

    void onFinish(T result);

    void load();

    void cancelLoad();
}