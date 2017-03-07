package com.example.sibirsky.rk1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

    private Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        categorySpinner = (Spinner) findViewById(R.id.spn_categories);
    }
}
