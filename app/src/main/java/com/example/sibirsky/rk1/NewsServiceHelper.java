package com.example.sibirsky.rk1;

import android.content.Context;

import ru.mail.weather.lib.News;

public class NewsServiceHelper {

    private static NewsServiceHelper instance;

    public synchronized static NewsServiceHelper getInstance(Context context) {
        if (instance == null) {
            instance = new NewsServiceHelper();

        }
        return instance;
    }

    private NewsServiceHelper() {
    }

    public interface ResultListener {
        void onResult(final boolean success, final News news);
    }
}
