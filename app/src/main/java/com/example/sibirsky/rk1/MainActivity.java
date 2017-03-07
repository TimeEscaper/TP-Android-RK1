package com.example.sibirsky.rk1;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import ru.mail.weather.lib.News;
import ru.mail.weather.lib.Storage;

public class MainActivity extends AppCompatActivity implements NewsServiceHelper.NewsResultListener {

    private int requestId;

    private TextView titleView;
    private TextView dateView;
    private TextView textView;
    private Button btnToggleBg;

    static {
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectActivityLeaks()
                .penaltyLog()
                .penaltyDeath()
                .build()
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleView = (TextView) findViewById(R.id.news_title);
        dateView = (TextView) findViewById(R.id.news_date);
        textView = (TextView) findViewById(R.id.news_text);
        btnToggleBg = (Button) findViewById(R.id.btn_toggle_bg);

        if (Storage.getInstance(this).loadIsUpdateInBg())
            btnToggleBg.setText("Stop background refresh");
        else
            btnToggleBg.setText("Start background refresh");

        findViewById(R.id.btn_settings).setOnClickListener(onSettingsClick);
        findViewById(R.id.btn_refresh).setOnClickListener(onRefreshClick);
        findViewById(R.id.btn_toggle_bg).setOnClickListener(onToggleBgClick);
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshNews();
    }

    @Override
    protected void onStop() {
        NewsServiceHelper.getInstance(this).removeListener(requestId);
        super.onStop();
    }

    private final View.OnClickListener onSettingsClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
    };

    private final View.OnClickListener onRefreshClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            refreshNews();
        }
    };

    private final View.OnClickListener onToggleBgClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (Storage.getInstance(MainActivity.this).loadIsUpdateInBg()) {
                Storage.getInstance(MainActivity.this).saveIsUpdateInBg(false);
                Toast.makeText(MainActivity.this, "Refresh in background OFF", Toast.LENGTH_SHORT)
                        .show();
                btnToggleBg.setText("Start background refresh");
            } else {
                Storage.getInstance(MainActivity.this).saveIsUpdateInBg(true);
                Toast.makeText(MainActivity.this, "Refresh in background is ON", Toast.LENGTH_SHORT)
                        .show();
                btnToggleBg.setText("Stop background refresh");
            }
        }
    };


    private final void refreshNews() {
        if (requestId == 0) {
            requestId = NewsServiceHelper.getInstance(this).refreshNews(this, this);
        } else {
            Toast.makeText(this, "There is pending request", Toast.LENGTH_SHORT).show();
        }
    }

    private void showNews() {
        News news = Storage.getInstance(this).getLastSavedNews();
        if (news == null) {
            titleView.setText("There are still no loaded news");
            dateView.setText("");
            textView.setText("");
        } else {
            titleView.setText(news.getTitle());
            dateView.setText(new Date(news.getDate()).toString());
            textView.setText(news.getBody());
        }
    }

    @Override
    public void onResult(boolean success) {
        requestId = 0;
        if (!success)
            Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show();
        showNews();
    }
}
