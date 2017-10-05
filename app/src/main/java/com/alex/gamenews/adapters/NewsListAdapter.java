package com.alex.gamenews.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.gamenews.R;
import com.alex.gamenews.entities.NewsEntity;
import com.alex.gamenews.util.Clock;

import java.util.List;

public class NewsListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<NewsEntity> news;

    public NewsListAdapter(Context context, List<NewsEntity> news) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.news = news;
    }

    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public Object getItem(int position) {
        return news.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View newsView = view;
        if (newsView == null) newsView = inflater.inflate(R.layout.news_list_item, viewGroup, false);

        NewsEntity newsEntity = getNewsEntity(position);

        ImageView cover = newsView.findViewById(R.id.news_cover);
        if (newsEntity.getCover() != null) cover.setImageBitmap(newsEntity.getCover());
        else cover.setVisibility(View.GONE);

        TextView header = newsView.findViewById(R.id.news_header);
        header.setText(newsEntity.getName());

        TextView source = newsView.findViewById(R.id.news_source);
        source.setText(newsEntity.getSource());

        TextView date = newsView.findViewById(R.id.news_date);
        date.setText(Clock.getTime(newsEntity.getDate(), context.getString(R.string.date_format_pattern)));

        return newsView;
    }

    private NewsEntity getNewsEntity(int position) {
        return (NewsEntity) getItem(position);
    }
}