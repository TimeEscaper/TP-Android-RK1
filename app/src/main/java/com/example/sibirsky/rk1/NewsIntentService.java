package com.example.sibirsky.rk1;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import ru.mail.weather.lib.News;
import ru.mail.weather.lib.Storage;


public class NewsIntentService extends IntentService {

    public static final String ACTION_NEWS = "action.NEWS";
    public static final String EXTRA_NEWS_TOPIC = "extra.NEWS_TOPIC";
    public static final String EXTRA_REQUEST_ID = "extra.REQUEST_ID";

    public static final String ACTION_NEWS_RESULT_SUCCESS = "action.NEWS_RESULT_SUCCESS";
    public static final String ACTION_NEWS_RESULT_ERROR = "action.NEWS_RESULT_ERROR";
    public static final String EXTRA_NEWS_TITLE = "extra.NEWS_TITLE";
    public static final String EXTRA_NEWS_DATE = "extra_NEWS_DATE";
    public static final String EXTRA_NEWS_TEXT = "extra_NEWS_TEXT";

    public NewsIntentService() {
        super("NewsIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_NEWS.equals(action)) {

            }
        }
    }

    private void handleActionNews(final String topic, final int requestId) {
        boolean success = true;
        News result = NewsProcessor.getNews(this);
        if (result == null)
            success = false;
        Intent intent = new Intent(success ? ACTION_NEWS_RESULT_SUCCESS : ACTION_NEWS_RESULT_ERROR);
        intent.putExtra(EXTRA_REQUEST_ID, requestId);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
