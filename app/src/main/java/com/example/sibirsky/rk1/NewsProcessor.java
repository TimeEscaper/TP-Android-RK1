package com.example.sibirsky.rk1;


import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import ru.mail.weather.lib.News;
import ru.mail.weather.lib.NewsLoader;
import ru.mail.weather.lib.Storage;
import ru.mail.weather.lib.Topics;


public class NewsProcessor {

    public static void refreshNews(Context context) {
        News news;
        Storage storage = Storage.getInstance(context);
        String topic = storage.loadCurrentTopic();
        if (TextUtils.isEmpty(topic)) {
            topic = Topics.IT;
            storage.saveCurrentTopic(topic);
        }
        try {
            Log.i(NewsProcessor.class.getSimpleName(), "Topic: " + topic);
            news = new NewsLoader().loadNews(topic);
            Log.i(NewsProcessor.class.getSimpleName(), news.getTitle());
        } catch (IOException e) {
            Log.i(NewsProcessor.class.getSimpleName(), e.getMessage());
            return;
        }
        storage.saveNews(news);
    }
}
