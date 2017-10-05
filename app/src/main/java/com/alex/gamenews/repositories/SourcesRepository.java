package com.alex.gamenews.repositories;

import android.util.SparseArray;

import com.alex.gamenews.interfaces.Repository;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;

public class SourcesRepository implements Repository<SparseArray<String>> {

    private final static String ID = "id";
    private final static String SOURCE_NAME = "source_name";

    @Override
    public SparseArray<String> getData(String url) {

        SparseArray<String> sources = new SparseArray<>();

        try {
            HttpURLConnection connection =
                    (HttpURLConnection) new URL(url).openConnection();

            String response = "", line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                response += line;
            }
            JSONArray sourcesJson = new JSONArray(response);
            connection.disconnect();
            reader.close();

            JSONObject source;

            for (int i = 0; i < sourcesJson.length(); i++) {
                source = sourcesJson.getJSONObject(i);
                sources.put(source.getInt(ID), source.getString(SOURCE_NAME));
            }
            return sources;
        } catch (Exception e) {
            sources.put(-1, e.toString() + Arrays.toString(e.getStackTrace()));
            return sources;
        }
    }
}
