package com.appocalypse.naturenav;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

import io.noties.markwon.Markwon;

public class PrivacyPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        ActionBar actionBar = Objects.requireNonNull(getSupportActionBar());
        actionBar.setTitle(getString(R.string.privacy_policy));

        TextView privacyPolicyTextView = findViewById(R.id.privacy_policy_text_view);

        Markwon markwon = Markwon.create(getApplicationContext());
        markwon.setMarkdown(privacyPolicyTextView, getString(R.string.privacy_policy_text));
    }
}