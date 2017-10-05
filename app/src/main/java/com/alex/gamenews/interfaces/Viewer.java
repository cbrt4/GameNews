package com.alex.gamenews.interfaces;

public interface Viewer<T> {

    void showProgress();

    void hideProgress();

    void setView(T view);
}