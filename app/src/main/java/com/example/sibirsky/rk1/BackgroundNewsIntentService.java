package com.example.sibirsky.rk1;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.StrictMode;


public class BackgroundNewsIntentService extends IntentService {

    private static final String ACTION_START = "action.START";
    private static final String ACTION_STOP = "action.STOP";

    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                NewsProcessor.refreshNews(BackgroundNewsIntentService.this);
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    });

    public BackgroundNewsIntentService() {
        super("BackgroundNewsIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_START.equals(action)) {
                thread.start();
            }
            else if (ACTION_STOP.equals(action)) {
                thread.interrupt();
            }
        }
    }

}
