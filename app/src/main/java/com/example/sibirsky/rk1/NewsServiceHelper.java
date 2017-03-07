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
import ru.mail.weather.lib.Scheduler;
import ru.mail.weather.lib.Storage;

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
        filter.addAction(NewsIntentService.ACTION_NEWS_REFRESH_OK);
        filter.addAction(NewsIntentService.ACTION_NEWS_REFRESH_FAIL);

        LocalBroadcastManager.getInstance(context).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                final int requestId = intent.getIntExtra(NewsIntentService.EXTRA_REQUEST_ID, -1);
                final NewsResultListener listener = listeners.remove(requestId);

                if (listener != null)
                    listener.onResult(intent.getAction().equals(
                            NewsIntentService.ACTION_NEWS_REFRESH_OK));
            }
        }, filter);
    }

    public int refreshNews(final Context context, final NewsResultListener listener) {
        listeners.put(idCounter, listener);
        Intent intent = new Intent(context, NewsIntentService.class);
        intent.setAction(NewsIntentService.ACTION_NEWS);
        intent.putExtra(NewsIntentService.EXTRA_REQUEST_ID, idCounter);
        context.startService(intent);

        return idCounter++;
    }

    public void startBackgroundRefresh(final  Context context) {
        Intent intent = new Intent(context, NewsIntentService.class);
        intent.setAction(NewsIntentService.ACTION_NEWS);
        Storage.getInstance(context).saveIsUpdateInBg(true);
        Scheduler.getInstance().schedule(context, intent, 200000);
    }

    public void stopBackgroundRefresh(final Context context) {
        Intent intent = new Intent(context, NewsIntentService.class);
        intent.setAction(NewsIntentService.ACTION_NEWS);
        Storage.getInstance(context).saveIsUpdateInBg(false);
        Scheduler.getInstance().unschedule(context, intent);
    }

    void removeListener(final int id) { listeners.remove(id); }

    public interface NewsResultListener {
        void onResult(boolean success);
    }
}
