package com.alex.gamenews.repositories;

import android.util.SparseArray;

import com.alex.gamenews.entities.NewsEntity;
import com.alex.gamenews.interfaces.Repository;
import com.alex.gamenews.responses.NewsResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewsRepository implements Repository<NewsResponse> {

    private final static String SOURCES_URL = "http://owledge.ru/api/v1/sources";
    private final static String COVER = "cover";
    private final static String NAME = "name";
    private final static String SOURCE_ID = "sourceId";
    private final static String UPDATED_AT = "updatedAt";
    private final static String TOP = "top";

    @Override
    public NewsResponse getData(String url) {

        NewsResponse newsResponse = new NewsResponse();

        try {
            HttpURLConnection connection =
                    (HttpURLConnection) new URL(url).openConnection();

            String response = "", line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                response += line;
            }
            JSONArray newsJson = new JSONArray(response);
            connection.disconnect();
            reader.close();

            SparseArray<String> sources = new SourcesRepository().getData(SOURCES_URL);

            List<NewsEntity> topNewsList = new ArrayList<>();
            List<NewsEntity> newsList = new ArrayList<>();
            NewsEntity newsEntity;
            JSONObject pieceOfNews;

            for (int i = 0; i < newsJson.length(); i++) {
                pieceOfNews = newsJson.getJSONObject(i);
                newsEntity = new NewsEntity();
                newsEntity.setCover(new BitmapRepository().getData(pieceOfNews.getString(COVER)));
                newsEntity.setName(pieceOfNews.getString(NAME));
                newsEntity.setSource(sources.get(pieceOfNews.getInt(SOURCE_ID)) != null ?
                                sources.get(pieceOfNews.getInt(SOURCE_ID)) :
                                String.valueOf(pieceOfNews.getInt(SOURCE_ID)));
                newsEntity.setDate(pieceOfNews.getString(UPDATED_AT));
                if (pieceOfNews.getBoolean(TOP)) topNewsList.add(newsEntity);
                else newsList.add(newsEntity);
            }

            newsResponse.setTopNewsList(topNewsList);
            newsResponse.setNewsList(newsList);

        } catch (Exception e) {
            newsResponse.setError(e.toString() + Arrays.toString(e.getStackTrace()));
        }
        return newsResponse;
    }
}
