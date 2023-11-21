package com.appocalypse.naturenav;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        ActionBar actionBar = Objects.requireNonNull(getSupportActionBar());

//      actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//      actionBar.setDisplayShowCustomEnabled(true);
//      actionBar.setCustomView(R.layout.custom_action_bar);

        actionBar.setTitle(getString(R.string.about_us));

    }
}