package com.example.sibirsky.rk1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import ru.mail.weather.lib.News;

public class NewsServiceHelper {

    private static NewsServiceHelper instance;

    private int idCounter = 1;
    private final Map<Integer, NewsResultListener> listeners = new HashMap<>();

    public synchronized static NewsServiceHelper getInstance(final Context context) {
        if (instance == null) {
            instance = new NewsServiceHelper();
            instance.initBroadcastReceiver(context);
        }
        return instance;
    }

    private NewsServiceHelper() {
    }

    private void initBroadcastReceiver(Context context) {
        final IntentFilter filter = new IntentFilter();
        filter.addAction(NewsIntentService.ACTION_NEWS_REFRESH_CANCEL);

        LocalBroadcastManager.getInstance(context).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                final int requestId = intent.getIntExtra(NewsIntentService.EXTRA_REQUEST_ID, -1);
                final NewsResultListener listener = listeners.remove(requestId);

                if (listener != null)
                    listener.onResult();
            }
        }, filter);
    }

    public int refreshNews(final Context context, final NewsResultListener listener) {
        listeners.put(idCounter, listener);
        Intent intent = new Intent(context, NewsIntentService.class);
        intent.setAction(NewsIntentService.ACTION_NEWS);
        intent.putExtra(NewsIntentService.EXTRA_REQUEST_ID, idCounter);
        Log.i(NewsServiceHelper.class.getSimpleName(), "Starting service...");
        context.startService(intent);
        return idCounter++;
    }

    void removeListener(final int id) { listeners.remove(id); }

    public interface NewsResultListener {
        void onResult();
    }
}
