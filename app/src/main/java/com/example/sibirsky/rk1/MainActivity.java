package com.example.sibirsky.rk1;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

        findViewById(R.id.btn_settings).setOnClickListener(onSettingsClick);
        findViewById(R.id.btn_refresh).setOnClickListener(onRefreshClick);

        Log.i(MainActivity.class.getSimpleName(), "Activity created");
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

    private final void refreshNews() {
        if (requestId == 0) {
            requestId = NewsServiceHelper.getInstance(this).refreshNews(this, this);
        } else {
            Toast.makeText(this, "There is pending request", Toast.LENGTH_SHORT).show();
        }
    }

    private void showNews() {
        News news = Storage.getInstance(this).getLastSavedNews();
        if (news == null)
            Toast.makeText(this, "No downloaded news", Toast.LENGTH_SHORT).show();
        else {
            titleView.setText(news.getTitle());
            dateView.setText(new Date(news.getDate()).toString());
            textView.setText(news.getBody());
        }
    }

    @Override
    public void onResult() {
        requestId = 0;
        showNews();
    }
}
