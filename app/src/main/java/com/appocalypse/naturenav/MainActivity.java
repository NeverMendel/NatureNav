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

import com.appocalypse.naturenav.auth.Users;
import com.appocalypse.naturenav.bottomsheet.BottomSheetDialog;
import com.appocalypse.naturenav.bottomsheet.BottomSheetDialogViewModel;
import com.appocalypse.naturenav.map.MapFragment;
import com.appocalypse.naturenav.map.MapViewModel;
import com.appocalypse.naturenav.poilist.PoiListViewModel;
import com.appocalypse.naturenav.suggestionlist.SuggestionListFragment;
import com.appocalypse.naturenav.suggestionlist.SuggestionListViewModel;
import com.appocalypse.naturenav.utility.GeocoderSingleton;
import com.appocalypse.naturenav.utility.PoiFinder;
import com.appocalypse.naturenav.utility.Theme;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    private MapFragment mapFragment;
    private SuggestionListFragment suggestionListFragment;
    private MapViewModel mapViewModel;
    private SuggestionListViewModel suggestionListViewModel;
    private BottomSheetDialog bottomSheetDialog;
    private BottomSheetDialogViewModel bottomSheetDialogViewModel;
    private PoiListViewModel poiListViewModel;

    private EditText searchEditText;
    private ImageButton actionIcon;

    private PoiFinder poiFinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Configuration.getInstance().setUserAgentValue(getString(R.string.app_name));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String theme = sharedPreferences.getString("app_theme", "auto");
        Theme.setTheme(theme);
        GeocoderSingleton.getInstance(getApplicationContext());
        Users.getInstance();
        poiFinder = PoiFinder.getInstance(getApplicationContext());


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showAppIntroduction();

        mapFragment = new MapFragment();
        suggestionListFragment = new SuggestionListFragment();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.main_activity_fragment, mapFragment)
                .add(R.id.main_activity_fragment, suggestionListFragment)
                .show(mapFragment)
                .hide(suggestionListFragment)
                .commit();

        bottomSheetDialog = new BottomSheetDialog();

        mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        suggestionListViewModel = new ViewModelProvider(this).get(SuggestionListViewModel.class);
        poiListViewModel = new ViewModelProvider(this).get(PoiListViewModel.class);
        bottomSheetDialogViewModel = new ViewModelProvider(this).get(BottomSheetDialogViewModel.class);

        searchEditText = findViewById(R.id.search_edit_text);
        actionIcon = findViewById(R.id.action_icon);

        suggestionListViewModel.setOnSuggestionClick(suggestion -> {
            hideKeyboard();
            poiFinder.search(getApplicationContext(), suggestion);
            bottomSheetDialog.show(getSupportFragmentManager(), BottomSheetDialog.TAG);
            bottomSheetDialog.setCancelable(false);
            searchEditText.setText(suggestion);
            showMap();
        });

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
                    showMap();
                    if(!bottomSheetDialog.isVisible()) {
                        showSearchIcon();
                    }
                } else {
                    showSuggestionList();
                    showArrowIcon();
                    suggestionListViewModel.setSearchString(s.toString());
                }
            }
        });

        searchEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                if (bottomSheetDialog.isVisible()) bottomSheetDialog.dismiss();
                showSuggestionList();
                showArrowIcon();
            }
        });

        actionIcon.setOnClickListener(v -> {
            actionIcon.clearFocus();
            searchEditText.clearFocus();
            if(bottomSheetDialogViewModel.getDisplayingListLiveData().getValue() || !bottomSheetDialog.isVisible()){
                if(bottomSheetDialog.isVisible()) bottomSheetDialog.dismiss();
                searchEditText.getText().clear();
//                poiFinder.clearPois();
                showSearchIcon();
                showMap();
            } else {
                bottomSheetDialogViewModel.setDisplayingList(true);
            }
            hideKeyboard();
        });

        findViewById(R.id.more_icon).setOnClickListener(v -> {
            MenuDialogFragment fragment = new MenuDialogFragment();
            fragment.show(getSupportFragmentManager(), MenuDialogFragment.TAG);
        });
    }

    private void showMap() {
        searchEditText.clearFocus();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .hide(suggestionListFragment)
                .show(mapFragment)
                .commit();
    }

    private void showSuggestionList() {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .hide(mapFragment)
                .show(suggestionListFragment)
                .commit();
    }

    private void showSearchIcon(){
        actionIcon.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_search_24));
        actionIcon.setClickable(false);
    }

    private void showArrowIcon(){
        actionIcon.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_arrow_back_24));
        actionIcon.setClickable(true);
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