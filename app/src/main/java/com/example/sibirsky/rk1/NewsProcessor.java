package com.example.sibirsky.rk1;


import android.content.Context;
import android.support.annotation.Nullable;

import java.io.IOException;

import ru.mail.weather.lib.News;
import ru.mail.weather.lib.NewsLoader;
import ru.mail.weather.lib.Storage;
import ru.mail.weather.lib.Topics;


public class NewsProcessor {

    public static void refreshNews(Context context) {
        News news;
        String topic = Storage.getInstance(context).loadCurrentTopic();
        if (topic == null) {
            topic = Topics.IT;
            Storage.getInstance(context).saveCurrentTopic(topic);
        }
        try {
            news = new NewsLoader().loadNews(topic);
        } catch (IOException e) { return; }
        Storage.getInstance(context).saveNews(news);
    }
}
