package com.example.sibirsky.rk1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import ru.mail.weather.lib.Storage;
import ru.mail.weather.lib.Topics;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        findViewById(R.id.btn_it).setOnClickListener(onItClick);
        findViewById(R.id.btn_auto).setOnClickListener(onAutoClick);
        findViewById(R.id.btn_health).setOnClickListener(onHealthClick);
    }

    private final View.OnClickListener onItClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setTopic(Topics.IT);
        }
    };

    private final View.OnClickListener onAutoClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setTopic(Topics.AUTO);
            back();
        }
    };

    private final View.OnClickListener onHealthClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setTopic(Topics.HEALTH);
            back();
        }
    };

    private void setTopic(String topic) {
        Storage.getInstance(this).saveCurrentTopic(topic);
        back();
    }

    private void back() {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
