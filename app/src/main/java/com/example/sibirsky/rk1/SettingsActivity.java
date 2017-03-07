package com.example.sibirsky.rk1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;

import ru.mail.weather.lib.Storage;

public class SettingsActivity extends AppCompatActivity {

    private Button btnIt;
    private Button btnAuto;
    private Button btnHealth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



    }

    private void setTopic(String topic) {
        Storage.getInstance(this).saveCurrentTopic(topic);
    }
}
