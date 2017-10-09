package com.alex.gamenews.presenters;

import com.alex.gamenews.interfaces.Loader;
import com.alex.gamenews.interfaces.Presenter;
import com.alex.gamenews.interfaces.Viewer;
import com.alex.gamenews.loaders.NewsLoader;
import com.alex.gamenews.responses.NewsResponse;

public class NewsPresenter implements Presenter<NewsResponse> {

    private Viewer<NewsResponse> viewer;
    private NewsResponse newsResponse;
    private Loader loader;
    private boolean isLoading;

    private NewsPresenter() {}

    public static NewsPresenter getInstance() {
        return new NewsPresenter();
    }

    @Override
    public void onStartLoading() {
        isLoading = true;
        if (viewer != null)
            viewer.showProgress();
    }

    @Override
    public void onLoadFinished(NewsResponse result) {
        isLoading = false;
        newsResponse = result;
        if (viewer != null) {
            viewer.hideProgress();
            viewer.setView(result);
        }
    }

    @Override
    public void loadData(String url) {
        if (newsResponse == null || newsResponse.getError() != null) {
            loader = new NewsLoader(url);
            loader.load();
        }
    }

    @Override
    public void cancelLoading() {
        if (isLoading)
            loader.cancelLoad();
    }

    @Override
    public void attachView(Viewer<NewsResponse> viewer) {
        this.viewer = viewer;
        if (isLoading) this.viewer.showProgress();
        else {
            this.viewer.hideProgress();
            if (newsResponse != null) this.viewer.setView(newsResponse);
        }
    }

    @Override
    public void detachView() {
        this.viewer = null;
    }
}