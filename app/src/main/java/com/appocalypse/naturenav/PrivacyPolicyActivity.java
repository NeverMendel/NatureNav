package com.appocalypse.naturenav;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Objects;

public class PrivacyPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        ActionBar actionBar = Objects.requireNonNull(getSupportActionBar());
        actionBar.setTitle(getString(R.string.privacy_policy));
    }
}