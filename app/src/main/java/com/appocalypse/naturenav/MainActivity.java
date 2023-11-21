package com.appocalypse.naturenav;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import org.osmdroid.config.Configuration;

import com.appocalypse.naturenav.list.ListFragment;
import com.appocalypse.naturenav.list.ListViewModel;
import com.appocalypse.naturenav.map.MapFragment;
import com.appocalypse.naturenav.map.MapViewModel;
import com.appocalypse.naturenav.utility.Theme;

public class MainActivity extends AppCompatActivity {

    private MapFragment mapFragment;
    private ListFragment listFragment;
    private MapViewModel mapViewModel;
    private ListViewModel listViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Configuration.getInstance().setUserAgentValue(getString(R.string.app_name));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String theme = sharedPreferences.getString("app_theme","light");
        Theme.setTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showAppIntroduction();

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
        ImageButton actionIcon = findViewById(R.id.action_icon);

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
                    actionIcon.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_search_24));
                    actionIcon.setClickable(false);
                } else {
                    // show list fragment
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .hide(mapFragment)
                            .show(listFragment)
                            .commit();
                    actionIcon.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_arrow_back_24));
                    actionIcon.setClickable(true);
                    listViewModel.setContext(listFragment.requireContext());
                    listViewModel.search(s.toString());
                }
            }
        });

        actionIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.getText().clear();
                searchEditText.clearFocus();
                hideKeyboard();
            }
        });

        View blackView = findViewById(R.id.main_activity_black_view);
        blackView.bringToFront();

        findViewById(R.id.more_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuDialogFragment fragment = new MenuDialogFragment();
                fragment.show(getSupportFragmentManager(), "dialog");
                blackView.setAlpha(0.3f);
                fragment.setDismissEventListener(() -> {
                    blackView.setAlpha(0f);
                });
            }
        });
    }

    private void showAppIntroduction() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        boolean app_intro_played = sharedPref.getBoolean(getString(R.string.app_intro_played_key), false);
        if (!app_intro_played) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(getString(R.string.app_intro_played_key), true);
            editor.apply();
            startActivity(new Intent(getApplicationContext(), AppIntroduction.class));
        }
    }

    private void hideKeyboard() {
        Activity activity = mapFragment.getActivity();
        View view = mapFragment.getView();
        if (activity != null && view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}