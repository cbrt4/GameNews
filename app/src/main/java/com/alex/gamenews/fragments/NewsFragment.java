package com.alex.gamenews.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alex.gamenews.R;
import com.alex.gamenews.adapters.NewsListAdapter;
import com.alex.gamenews.adapters.TopNewsPagerAdapter;
import com.alex.gamenews.application.GameNewsApp;
import com.alex.gamenews.entities.NewsEntity;
import com.alex.gamenews.interfaces.Presenter;
import com.alex.gamenews.interfaces.Viewer;
import com.alex.gamenews.responses.NewsResponse;
import com.alex.gamenews.util.FlexibleIndicatorAdapter;

import java.util.List;

public class NewsFragment extends Fragment implements Viewer<NewsResponse> {

    private View topNews;
    private ViewPager topNewsPager;
    private RecyclerView indicatorRecycler;
    private ListView newsListView;
    private ProgressBar roundProgressBar;

    Presenter<NewsResponse> presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        topNews = inflater.inflate(R.layout.top_news_layout, null);

        topNewsPager = topNews.findViewById(R.id.top_news_slider);

        indicatorRecycler = topNews.findViewById(R.id.indicator_recycler);

        newsListView = rootView.findViewById(R.id.news_list);

        roundProgressBar = rootView.findViewById(R.id.progress_round);
        roundProgressBar.setVisibility(View.GONE);

        presenter = GameNewsApp.getNewsPresenter();
        presenter.attachView(this);

        loadNews();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    private void loadNews() {
        if (GameNewsApp.isOnline(getContext())) {
            presenter.loadData(getString(R.string.news_load_url));
        } else Toast.makeText(getContext(),
                getString(R.string.no_internet_connection),
                Toast.LENGTH_LONG).show();
    }

    private void fillTopNews(List<NewsEntity> topNewsList) {
        PagerAdapter topNewsPagerAdapter = new TopNewsPagerAdapter(this.getContext(), topNewsList);
        topNewsPager.setAdapter(topNewsPagerAdapter);
        indicatorRecycler.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        indicatorRecycler.setAdapter(new FlexibleIndicatorAdapter(indicatorRecycler, topNewsPager, 3));
        indicatorRecycler.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            // Stop only scrolling.
            return rv.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING;
        }
    });
        newsListView.addHeaderView(topNews);
    }

    private void fillNews(List<NewsEntity> newsList) {
        newsListView.setAdapter(new NewsListAdapter(this.getContext(), newsList));
    }

    @Override
    public void showProgress() {
        roundProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        roundProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setView(NewsResponse result) {
        if (result.getError() != null)
            Toast.makeText(this.getContext(),
                    result.getError(),
                    Toast.LENGTH_LONG).show();
        else {
            if (!result.getTopNewsList().isEmpty())
                fillTopNews(result.getTopNewsList());
            else fillTopNews(result.getNewsList());

            fillNews(result.getNewsList());
        }
    }
}