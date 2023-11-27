package com.appocalypse.naturenav;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

import io.noties.markwon.Markwon;

public class TermsAndConditionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

        ActionBar actionBar = Objects.requireNonNull(getSupportActionBar());
        actionBar.setTitle(getString(R.string.terms_and_conditions));

        TextView termsAndConditionsTextView = findViewById(R.id.terms_and_conditions_text_view);

        Markwon markwon = Markwon.create(getApplicationContext());
        markwon.setMarkdown(termsAndConditionsTextView, getString(R.string.terms_and_conditions_text));
    }
}