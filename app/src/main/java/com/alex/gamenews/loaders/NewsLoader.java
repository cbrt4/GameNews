package com.alex.gamenews.loaders;

import android.os.AsyncTask;

import com.alex.gamenews.application.GameNewsApp;
import com.alex.gamenews.interfaces.Presenter;
import com.alex.gamenews.repositories.NewsRepository;
import com.alex.gamenews.responses.NewsResponse;

public class NewsLoader extends AsyncTask<Void, Void, NewsResponse> {

    private String url;
    private Presenter<NewsResponse> presenter;

    public NewsLoader(String url) {
        this.url = url;
        presenter = GameNewsApp.getNewsPresenter();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (presenter != null) presenter.onStartLoading();
        else cancel(true);
    }

    @Override
    protected void onPostExecute(NewsResponse result) {
        super.onPostExecute(result);
        if (presenter != null) presenter.onLoadFinished(result);
        else cancel(true);
    }

    @Override
    protected NewsResponse doInBackground(Void... voids) {
        return new NewsRepository().getData(url);
    }
}
