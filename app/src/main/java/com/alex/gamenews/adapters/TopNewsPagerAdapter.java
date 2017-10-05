package com.alex.gamenews.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.gamenews.R;
import com.alex.gamenews.entities.NewsEntity;
import com.alex.gamenews.util.Clock;

import java.util.List;

public class TopNewsPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<NewsEntity> topNews;

    public TopNewsPagerAdapter(Context context, List<NewsEntity> topNews) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.topNews = topNews;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        NewsEntity newsEntity = topNews.get(position);

        View topNewsView = inflater.inflate(R.layout.top_news_slider_item, container, false);

        ImageView cover = topNewsView.findViewById(R.id.top_cover);
        cover.setBackgroundColor(Color.BLACK);
        cover.setImageAlpha(64);
        cover.setImageBitmap(newsEntity.getCover());

        TextView header = topNewsView.findViewById(R.id.top_header);
        header.setText(newsEntity.getName());

        TextView source = topNewsView.findViewById(R.id.top_source);
        source.setText(newsEntity.getSource());

        TextView date = topNewsView.findViewById(R.id.top_date);
        date.setText(Clock.getTime(newsEntity.getDate(), context.getString(R.string.date_format_pattern)));

        container.addView(topNewsView);
        return topNewsView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return topNews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view != null && object != null && view.equals(object);
    }
}