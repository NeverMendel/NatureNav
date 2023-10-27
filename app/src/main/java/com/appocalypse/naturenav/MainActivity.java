package com.appocalypse.naturenav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.osmdroid.config.Configuration;

import com.appocalypse.naturenav.MapFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Fragment mapFragment, listFragment;
    ViewModel mapViewModel, listViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Configuration.getInstance().setUserAgentValue(getString(R.string.app_name));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment = new MapFragment();
        listFragment = new ListFragment();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.main_activity_fragment, mapFragment)
                .add(R.id.main_activity_fragment, listFragment)
                .show(mapFragment)
                .hide(listFragment)
                .commit();

        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        listViewModel = new ViewModelProvider(this).get(ListViewModel.class);

        EditText searchEditText = findViewById(R.id.search_edit_text);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().isEmpty()) {
                    // show map fragment
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .hide(listFragment)
                            .show(mapFragment)
                            .commit();
                } else {
                    // show list fragment
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .hide(mapFragment)
                            .show(listFragment)
                            .commit();
                }
            }
        });

        findViewById(R.id.action_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.getText().clear();
                searchEditText.clearFocus();
            }
        });
    }
}