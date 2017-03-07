package com.example.sibirsky.rk1;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import ru.mail.weather.lib.News;
import ru.mail.weather.lib.Storage;


public class NewsIntentService extends IntentService {

    public static final String ACTION_NEWS = "action.NEWS";
    public static final String EXTRA_REQUEST_ID = "extra.REQUEST_ID";

    public static final String ACTION_NEWS_REFRESH_CANCEL = "action.NEWS_REFRESH_CANCEL";

    public NewsIntentService() {
        super("NewsIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //Log.i(NewsIntentService.class.getSimpleName(), "Intent received");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_NEWS.equals(action)) {
                //Log.i(NewsIntentService.class.getSimpleName(), "Intent received");
                final int requestId = intent.getIntExtra(EXTRA_REQUEST_ID, -1);
                handleActionNews(requestId);
            }
        }
    }

    private void handleActionNews(final int requestId) {
        NewsProcessor.refreshNews(this);
        Intent intent = new Intent(ACTION_NEWS_REFRESH_CANCEL);
        intent.putExtra(EXTRA_REQUEST_ID, requestId);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
