package com.alex.gamenews.loaders;

import android.os.AsyncTask;

import com.alex.gamenews.application.GameNewsApp;
import com.alex.gamenews.interfaces.Loader;
import com.alex.gamenews.interfaces.Presenter;
import com.alex.gamenews.repositories.NewsRepository;
import com.alex.gamenews.responses.NewsResponse;

public class NewsLoader extends AsyncTask<Void, Void, NewsResponse> implements Loader<NewsResponse> {

    private String url;
    private Presenter<NewsResponse> presenter;

    public NewsLoader(String url) {
        this.url = url;
        presenter = GameNewsApp.getNewsPresenter();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        onStart();
    }

    @Override
    protected void onPostExecute(NewsResponse result) {
        super.onPostExecute(result);
        onFinish(result);
    }

    @Override
    protected NewsResponse doInBackground(Void... voids) {
        return new NewsRepository().getData(url);
    }

    @Override
    public void onStart() {
        if (presenter != null) presenter.onStartLoading();
        else cancel(true);
    }

    @Override
    public void onFinish(NewsResponse result) {
        if (presenter != null) presenter.onLoadFinished(result);
        else cancel(true);
    }

    @Override
    public void load() {
        execute();
    }

    @Override
    public void cancelLoad() {
        cancel(true);
    }
}