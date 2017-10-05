package com.alex.gamenews.responses;

import com.alex.gamenews.entities.NewsEntity;

import java.util.List;

public class NewsResponse {

    private List<NewsEntity> topNewsList;
    private List<NewsEntity> newsList;
    private String error;

    public List<NewsEntity> getTopNewsList() {
        return topNewsList;
    }

    public void setTopNewsList(List<NewsEntity> topNewsList) {
        this.topNewsList = topNewsList;
    }

    public List<NewsEntity> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<NewsEntity> newsList) {
        this.newsList = newsList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
