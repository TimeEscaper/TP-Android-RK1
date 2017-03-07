package com.example.sibirsky.rk1;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import ru.mail.weather.lib.News;
import ru.mail.weather.lib.NewsLoader;
import ru.mail.weather.lib.Storage;
import ru.mail.weather.lib.Topics;


public class NewsIntentService extends IntentService {

    public static final String ACTION_NEWS = "action.NEWS";
    public static final String EXTRA_REQUEST_ID = "extra.REQUEST_ID";

    public static final String ACTION_NEWS_REFRESH_OK = "action.NEWS_REFRESH_OK";
    public static final String ACTION_NEWS_REFRESH_FAIL = "action.NEWS_REFRESH_FAIL";

    public NewsIntentService() {
        super("NewsIntentService");
    }

    private boolean refreshNews() {
        News news;
        Storage storage = Storage.getInstance(this);
        String topic = storage.loadCurrentTopic();
        if (TextUtils.isEmpty(topic)) {
            topic = Topics.IT;
            storage.saveCurrentTopic(topic);
        }
        try {
            news = new NewsLoader().loadNews(topic);
        } catch (IOException e) {
            return false;
        }
        storage.saveNews(news);
        return true;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_NEWS.equals(action)) {
                final int requestId = intent.getIntExtra(EXTRA_REQUEST_ID, -1);
                handleActionNews(requestId);
            }
        }
    }

    private void handleActionNews(final int requestId) {
        final boolean success = refreshNews();
        Intent intent = new Intent(success ? ACTION_NEWS_REFRESH_OK : ACTION_NEWS_REFRESH_FAIL);
        intent.putExtra(EXTRA_REQUEST_ID, requestId);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
