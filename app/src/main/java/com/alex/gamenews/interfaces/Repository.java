package com.alex.gamenews.interfaces;

public interface Repository<T> {

    T getData(String url);
}